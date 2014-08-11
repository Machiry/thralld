/**
 * 
 */
package com.thralld.client.core;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.thralld.common.aobjects.ClientCommandHandler;
import com.thralld.common.aobjects.Command;
import com.thralld.common.aobjects.CommandRequestInfo;
import com.thralld.common.aobjects.ConnectionSpecification;
import com.thralld.common.aobjects.NetworkConnection;
import com.thralld.common.interfaces.INetworkInterface;
import com.thralld.common.logging.Logger;
import com.thralld.common.objects.ClientCommandQuery;
import com.thralld.common.objects.ClientCommandResponse;
import com.thralld.common.objects.PortalServerInfo;
import com.thralld.common.tcpnetwork.TCPConnectionSpecification;
import com.thralld.common.tcpnetwork.TCPNetworkImplementation;
import com.thralld.common.utilities.CommandHandlerFactory;
import com.thralld.common.utilities.FileUtilities;
import com.thralld.common.utilities.NetworkObjectSerializer;
import com.thralld.common.utilities.NetworkUtilities;
import com.thralld.common.utilities.PortalCommunicator;
import com.thralld.common.utilities.ReflectionHelper;


/**
 * This is the main processing engine of client.
 * 
 * Refer Dataflow diagrams to understand more about client processing.
 * @author m4kh1ry
 *
 */
public class ClientMain 
{
	//TODO: change this to something configurable.
	private static final int SERVER_POLL_TIME = 2000;
	private static final String urlServerList = "/Users/m4kh1ry/workdir/thralld/sources/thralld_common/files/alexa_25000";
	private static final int NET_CONNECTIVITY_CHECK_TIMEOUT = 2000;
	
	/**
	 * @param args
	 * @throws UnknownHostException 
	 * @throws InterruptedException 
	 */
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws UnknownHostException, InterruptedException 
	{
		//Do initialization
		Logger.initialize("thralld_client:"+InetAddress.getLocalHost().getHostName() +" Starting at:" + (new Date()).toString());
		while(true)
		{
			//1.Check for Internet connectivity
			while(!NetworkUtilities.isInternetConnectionUp(FileUtilities.readLines(urlServerList)))
			{
				Thread.sleep(NET_CONNECTIVITY_CHECK_TIMEOUT);
			}
			
			//2. Get list of available servers from portal.
			List<PortalServerInfo> availableSerInfos = new ArrayList<PortalServerInfo>();
			while(availableSerInfos.size() == 0)
			{
				try
				{
					Thread.sleep(SERVER_POLL_TIME);
				}
				catch(Exception e)
				{
					Logger.logException("Problem occured while polling", e);
				}
				List<PortalServerInfo> currServInfo = PortalCommunicator.getServerList();
				availableSerInfos.addAll(currServInfo);
			}
			
			//3. Select a server
			PortalServerInfo targetServerInfo = availableSerInfos.get(0);
	
			//4. Connect to the server
			INetworkInterface networkInterface = new TCPNetworkImplementation();
			String[] connParams = new String[]{null,null,targetServerInfo.serverNetworkName,targetServerInfo.serverNetworkPort};
			ConnectionSpecification serverConnSpecification = TCPConnectionSpecification.getConnectionSpecification(connParams);
			NetworkConnection serverConnection = networkInterface.openConnection(serverConnSpecification);
			
			//5. Process commands.
			while(true)
			{
				try
				{
					ClientCommandQuery toExecCommand = NetworkObjectSerializer.receiveObject(serverConnection, ClientCommandQuery.class);
					Command targetCommand = ReflectionHelper.getCommandByID(toExecCommand.commandId);
					if(targetCommand != null && ReflectionHelper.getCommandVersion(targetCommand).equals(toExecCommand.commandVersion))
					{
						if(NetworkObjectSerializer.sendObject(serverConnection,ClientCommandResponse.getAvailableResponse()))
						{
							Logger.logInfo("Sending available to Server for command:"+targetCommand.toString());
						}
						else
						{
							Logger.logError("Problem occured while sending available to server for command:"+Integer.toString(toExecCommand.commandId));
							//We exit here, because we are no sure if server is up.
							//We try and connect to other servers.
							break;
						}
						CommandRequestInfo commandRequestInfo = (CommandRequestInfo)NetworkObjectSerializer.receiveObject(serverConnection, targetCommand.getCommandRequestInfoType());
						ClientCommandHandler commandHandler = CommandHandlerFactory.getClientCommandHandler(commandRequestInfo);
						if(commandHandler.processCommand(serverConnection, commandRequestInfo))
						{
							Logger.logInfo("Sucessfully processed command:" + commandRequestInfo.toString());
						}
						else
						{
							Logger.logError("Problem occured while prcessing command:" + commandRequestInfo.toString());
							//We exit here, because we are no sure if server is up.
							//We try and connect to other servers.
							break;
						}
					}
					else
					{
						if(NetworkObjectSerializer.sendObject(serverConnection, ClientCommandResponse.getNotAvailableResponse()))
						{
							Logger.logInfo("Succesfully sent unavilable result to server");
						}
						else
						{
							Logger.logError("Problem occured while sending unavailable result to server");
							//We exit here, because we are no sure if server is up.
							//We try and connect to other servers.
							break;
						}
					}
				}
				catch(Exception e)
				{
					//Error occurred: may be server died.
					//Never mind, we start over again.
					Logger.logException("Problem occured while tryint co communicate with:"+serverConnection.toString(), e);
					break;
				}
			}
			Logger.logError("Connection tear down with server:"+serverConnSpecification);
		}
	}

}
