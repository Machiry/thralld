/**
 * 
 */
package com.thralld.server.core;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.thralld.common.aobjects.Command;
import com.thralld.common.aobjects.CommandRequestInfo;
import com.thralld.common.logging.Logger;
import com.thralld.common.objects.ClientInfo;
import com.thralld.common.utilities.ReflectionHelper;
import com.thralld.server.interfaces.IServerStatusInterface;

/**
 * @author m4kh1ry
 *
 */
public class ServerMain implements IServerStatusInterface
{
	public HashMap<ClientInfo,ServerThread> currentProcessingThreads = new HashMap<ClientInfo, ServerThread>();
	private ServerMainThread currentServerMain = null;
	
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
	
	public void updateNewClient(ClientInfo newClient,ServerThread newCliThread)
	{
		synchronized (currentProcessingThreads) 
		{
			currentProcessingThreads.put(newClient, newCliThread);
		}
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
				retVal = false;
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
		if(this.currentServerMain != null && !this.currentServerMain.isStopped)
		{
			return this.currentServerMain.stopMainThread();
		}
		return false;
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

}
