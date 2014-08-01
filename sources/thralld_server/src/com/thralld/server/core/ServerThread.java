package com.thralld.server.core;

import java.util.ArrayList;

import com.thralld.common.aobjects.CommandRequestInfo;
import com.thralld.common.aobjects.CommandResponseInfo;
import com.thralld.common.aobjects.NetworkConnection;
import com.thralld.common.aobjects.ServerCommandHandler;
import com.thralld.common.logging.Logger;
import com.thralld.common.utilities.CommandHandlerFactory;

public class ServerThread extends Thread 
{
	private ArrayList<CommandRequestInfo> toProcessCommands = new ArrayList<CommandRequestInfo>();
	private ArrayList<CommandResponseInfo> commandResults = new ArrayList<CommandResponseInfo>();
	private NetworkConnection targetNetworkConnecion = null;
	public boolean isErrorOccured = true;
	
	public ServerThread(NetworkConnection netConn)
	{
		this.targetNetworkConnecion = netConn;
	}
	
	public boolean addCommand(CommandRequestInfo comm)
	{
		synchronized (toProcessCommands) 
		{	
			if(comm!= null && !toProcessCommands.contains(comm))
			{
				toProcessCommands.add(comm);
				return true;
			}
		}
		return false;
	}
	
	public CommandResponseInfo getResponse(String uniqueID)
	{
		CommandResponseInfo toRet = null;
		synchronized (commandResults) 
		{
			for(CommandResponseInfo resp:commandResults)
			{
				if(resp.transactionID.equals(uniqueID))
				{
					toRet = resp;
					break;
				}
			}
			if(toRet != null)
			{
				commandResults.remove(toRet);
			}
		}
		return toRet;
	}
	
	public void run()
	{
		while(true)
		{
			CommandRequestInfo toProcess = null;
			while(toProcessCommands.size() == 0)
			{
				
			}
			synchronized(toProcessCommands)
			{
				if(toProcessCommands.size() > 0)
				{
					toProcess = toProcessCommands.remove(0);
				}
			}
			if(toProcess != null)
			{
				ServerCommandHandler commandHandler = CommandHandlerFactory.getServerCommandHandler(toProcess);
				CommandResponseInfo resultInfo = commandHandler.processCommand(targetNetworkConnecion, toProcess);
				if(resultInfo == null)
				{
					Logger.logError("Problem occured while processing command:"+toProcess.toString() +" on network connection:"+targetNetworkConnecion.toString());
					isErrorOccured = true;
					break;
				}
				synchronized (commandResults) 
				{
					commandResults.add(resultInfo);
				}
			}
		}
	}
}
