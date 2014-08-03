/**
 * 
 */
package com.thralld.client.core;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import com.thralld.common.aobjects.ClientCommandHandler;
import com.thralld.common.aobjects.Command;
import com.thralld.common.aobjects.CommandRequestInfo;
import com.thralld.common.aobjects.ConnectionSpecification;
import com.thralld.common.aobjects.NetworkConnection;
import com.thralld.common.interfaces.INetworkInterface;
import com.thralld.common.logging.Logger;
import com.thralld.common.objects.ClientCommandQuery;
import com.thralld.common.objects.PortalServerInfo;
import com.thralld.common.tcpnetwork.TCPConnectionSpecification;
import com.thralld.common.tcpnetwork.TCPNetworkImplementation;
import com.thralld.common.utilities.CommandHandlerFactory;
import com.thralld.common.utilities.NetworkObjectSerializer;
import com.thralld.common.utilities.PortalCommunicator;
import com.thralld.common.utilities.ReflectionHelper;


/**
 * @author m4kh1ry
 *
 */
public class ClientMain 
{
	
	private static final int SERVER_POLL_TIME = 2000;
	

	/**
	 * @param args
	 * @throws UnknownHostException 
	 */
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws UnknownHostException 
	{
		//Do initialization
		Logger.initialize("Client:"+InetAddress.getLocalHost().getHostName());
		while(true)
		{
			//1.Check for Internet connectivity
			
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
					if(ReflectionHelper.getCommandVersion(targetCommand).equals(toExecCommand.commandVersion))
					{
						if(NetworkObjectSerializer.sendObject(serverConnection, ClientCommandQuery.AVAILABLE))
						{
							//TODO:log
						}
						else
						{
							//TODO: log
						}
						CommandRequestInfo commandRequestInfo = (CommandRequestInfo)NetworkObjectSerializer.receiveObject(serverConnection, targetCommand.getCommandRequestInfoType());
						ClientCommandHandler commandHandler = CommandHandlerFactory.getClientCommandHandler(commandRequestInfo);
						if(commandHandler.processCommand(serverConnection, commandRequestInfo))
						{
							//TODO: log
						}
						else
						{
							//TODO: log
						}
					}
					else
					{
						if(NetworkObjectSerializer.sendObject(serverConnection, ClientCommandQuery.NOTAVAILABLE))
						{
							//TODO:log
						}
						else
						{
							//TODO: log
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
