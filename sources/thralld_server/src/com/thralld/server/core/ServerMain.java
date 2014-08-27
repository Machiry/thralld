/**
 * 
 */
package com.thralld.server.core;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.thralld.common.aobjects.Command;
import com.thralld.common.aobjects.CommandRequestInfo;
import com.thralld.common.aobjects.CommandResponseInfo;
import com.thralld.common.logging.Logger;
import com.thralld.common.objects.ClientInfo;
import com.thralld.common.objects.PortalServerInfo;
import com.thralld.common.utilities.PortalCommunicator;
import com.thralld.common.utilities.ReflectionHelper;
import com.thralld.server.interfaces.CommandState;
import com.thralld.server.interfaces.IServerStatusInterface;
import com.thralld.server.interfaces.IServerThreadFeedback;

/**
 * This is an implementation of ServerStatusInterface
 * 
 * @author m4kh1ry
 *
 */
public class ServerMain implements IServerStatusInterface,IServerThreadFeedback
{
	public HashMap<ClientInfo,ServerThread> currentProcessingThreads = new HashMap<ClientInfo, ServerThread>();
	private ServerMainThread currentServerMain = null;
	private int serverPreference = 1;
	
	
	
	@Override
	public List<ClientInfo> getClientInfoByName(String clientName)
	{
		ArrayList<ClientInfo> toRet = new ArrayList<ClientInfo>();
		for(ClientInfo cli:currentProcessingThreads.keySet())
		{
			if(cli.getClientName().equals(clientName))
			{
				toRet.add(cli);
			}
		}
		return toRet;
	}
	
	@Override
	public ArrayList<ClientInfo> getConnectedClients()
	{
		ArrayList<ClientInfo> toRet = new ArrayList<ClientInfo>(currentProcessingThreads.keySet());
		return toRet;
	}
	
	@Override
	public boolean startServer(int portNumber)
	{
		boolean retVal = false;
		if(currentServerMain == null || currentServerMain.isStopped)
		{
			try
			{
				currentServerMain = new ServerMainThread(portNumber, this);
				currentServerMain.start();
				Logger.logInfo("Started server thread on port:"+Integer.toString(portNumber));
				
				PortalServerInfo toPush = new PortalServerInfo();
				toPush.serverNetworkName = InetAddress.getLocalHost().getHostAddress();
				toPush.serverNetworkPort = Integer.toString(portNumber);
				toPush.preferenceInfo = this.serverPreference;
				if(PortalCommunicator.updateServerInfo(toPush))
				{
					Logger.logInfo("Successfully pushed server info to the portal");
				}
				else
				{
					Logger.logError("Problem occured while trying to push server info to portal");
				}
				retVal = true;
			}
			catch(Exception e)
			{
				Logger.logException("Error occured while trying to start main thread", e);
			}
		}
		return retVal;
	}
	
	@Override
	public boolean stopServer()
	{
		boolean retVal = false;
		if(this.currentServerMain != null && !this.currentServerMain.isStopped)
		{
			retVal = this.currentServerMain.stopMainThread();
			synchronized (currentProcessingThreads) 
			{
				for(ServerThread currClientThread:currentProcessingThreads.values())
				{
					currClientThread.terminateServerThread();
				}
			}
		}
		return retVal;
	}
	
	@Override
	public HashMap<String,String> getAvailableCommands()
	{
		HashMap<String,String> toRet = new HashMap<String,String>();
		ArrayList<Class<?>> availableCommands = (ArrayList<Class<?>>) ReflectionHelper.getCommandClasses();
		for(Class<?> comm:availableCommands)
		{
			try
			{
				Command currComm = (Command)comm.newInstance();
				toRet.put(currComm.getCommandName(), ReflectionHelper.getCommandVersion(currComm));
			}
			catch(Exception e)
			{
				Logger.logException("Problem occured while trying to get command information", e);
			}
			
		}
		return toRet;
	}
	
	@Override
	public List<String> getCommandParameters(String commandName)
	{
		Command targetCommand = ReflectionHelper.getCommandByName(commandName);
		CommandRequestInfo reqInfo;
		List<String> toRet = null;
		try 
		{
			reqInfo = (CommandRequestInfo)targetCommand.getCommandRequestInfoType().newInstance();
			toRet = reqInfo.getRequiredParameters();
		} catch (Exception e) 
		{
			Logger.logException("Problem occured while trying to get command parameters", e);
		}
		return toRet;
	}

	@Override
	public void setServerPreference(int preferenceNumber) 
	{
		this.serverPreference = preferenceNumber;
		
	}

	@Override
	public int getServerPreference() 
	{
		return this.serverPreference;
	}

	//Implementing methods of IServerThreadFeedback
	@Override
	public void updateNewClent(ClientInfo newClientInfo,
			ServerThread newServerThread) 
	{
		synchronized (currentProcessingThreads) 
		{
			currentProcessingThreads.put(newClientInfo, newServerThread);
		}
		
	}

	@Override
	public void notifyTerminatingClient(ClientInfo toRemoveClientInfo,
			ServerThread toRemoveServerThread) 
	{
		synchronized (currentProcessingThreads) 
		{
			if(currentProcessingThreads.containsKey(toRemoveClientInfo))
			{
				currentProcessingThreads.remove(toRemoveClientInfo);
			}
		}
		
	}

	@Override
	public boolean isServerRunning() 
	{
		return this.currentServerMain != null && !this.currentServerMain.isStopped;
	}

	@Override
	public String getServerStatus() 
	{
		if(this.currentServerMain != null && !this.currentServerMain.isStopped)
		{
			return this.currentServerMain.toString();
		}
		else
		{
			return "Server not started";
		}
	}

	@Override
	public boolean scheduleClientCommand(ClientInfo targetClient,
			CommandRequestInfo toRunCommand) 
	{
		synchronized (currentProcessingThreads) 
		{
			if(currentProcessingThreads.containsKey(targetClient))
			{
				return currentProcessingThreads.get(targetClient).addCommand(toRunCommand);
			}
			
		}
		return false;
	}

	@Override
	public CommandState getCommandState(ClientInfo targetClient, String uniqueID) 
	{
		CommandState toRet = CommandState.UNKNOWN;
		synchronized (currentProcessingThreads) 
		{
			if(currentProcessingThreads.containsKey(targetClient))
			{
				toRet = currentProcessingThreads.get(targetClient).getCommandState(uniqueID);
			}
			
		}
		return toRet;
	}

	@Override
	public Map<CommandRequestInfo, CommandState> getCurrentCommandsState(
			ClientInfo targetClient) 
	{
		Map<CommandRequestInfo,CommandState> toRet = new HashMap<CommandRequestInfo, CommandState>();
		synchronized (currentProcessingThreads) 
		{
			if(currentProcessingThreads.containsKey(targetClient))
			{
				toRet = currentProcessingThreads.get(targetClient).getCommandsState();
			}
			
		}
		return toRet;
	}

	@Override
	public CommandResponseInfo getCommandResponse(ClientInfo targetClient,
			String uniqueID) 
	{
		CommandResponseInfo toRet = null;
		synchronized (currentProcessingThreads) 
		{
			if(currentProcessingThreads.containsKey(targetClient))
			{
				toRet = currentProcessingThreads.get(targetClient).getResponse(uniqueID);
			}
			
		}
		return toRet;
	}

}
