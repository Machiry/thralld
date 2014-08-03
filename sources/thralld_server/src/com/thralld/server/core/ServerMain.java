/**
 * 
 */
package com.thralld.server.core;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.thralld.common.logging.Logger;
import com.thralld.common.objects.ClientInfo;

/**
 * @author m4kh1ry
 *
 */
public class ServerMain 
{
	public HashMap<ClientInfo,ServerThread> currentProcessingThreads = new HashMap<ClientInfo, ServerThread>();
	private ServerMainThread currentServerMain = null;
	
	private List<ClientInfo> getClientInfoByName(String clientName)
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
	
	public ArrayList<ClientInfo> getConnectedClients()
	{
		ArrayList<ClientInfo> toRet = new ArrayList<ClientInfo>(currentProcessingThreads.keySet());
		return toRet;
	}
	
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
	
	public boolean stopServer()
	{
		if(this.currentServerMain != null && !this.currentServerMain.isStopped)
		{
			return this.currentServerMain.stopMainThread();
		}
		return false;
	}

}
