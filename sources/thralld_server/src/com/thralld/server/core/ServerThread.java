package com.thralld.server.core;

import java.util.ArrayList;

import com.thralld.common.annotations.CanReturnNull;
import com.thralld.common.aobjects.CommandRequestInfo;
import com.thralld.common.aobjects.CommandResponseInfo;
import com.thralld.common.aobjects.NetworkConnection;
import com.thralld.common.aobjects.ServerCommandHandler;
import com.thralld.common.interfaces.INetworkInterface;
import com.thralld.common.logging.Logger;
import com.thralld.common.utilities.CommandHandlerFactory;

/***
 * This class represents ServerThread:
 * A thread which will be created for each connected client.
 * 
 * This thread works like message processing system.
 * A request that needs to be processed will be inserted into toProcessCommands, from which the thread reads, processes it
 * and inserts corresponding responses to commandResults.
 * 
 * @author m4kh1ry
 *
 */
public class ServerThread extends Thread 
{
	//List containing the commands that need to be processed.
	private ArrayList<CommandRequestInfo> toProcessCommands = new ArrayList<CommandRequestInfo>();
	//List containing responses for each of the command
	private ArrayList<CommandResponseInfo> commandResults = new ArrayList<CommandResponseInfo>();
	//The Network connection on which the communication needs to be take place.
	private NetworkConnection targetNetworkConnecion = null;
	//The target network interface to use while working with network.
	private INetworkInterface targetNetworkInterface = null;
	//This indicates if an error occurred while processing a request.
	public boolean isErrorOccured = true;
	//Message queue poll time interval.
	private static final int pollTimeMilliseconds = 1000;
	
	public ServerThread(NetworkConnection netConn,INetworkInterface netInter)
	{
		this.targetNetworkConnecion = netConn;
		this.targetNetworkInterface = netInter;
	}
	
	/***
	 * This method is used to insert requestInfo objects which needs to be processed by thread.
	 * @param comm RequestInfo object that need to be inserted.
	 * @return True, if the object is not already present, False, if the object is already present in the queue.
	 */
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
	
	/***
	 * This method is used to stop this client processing thread.
	 * 
	 * @return true/false depending on whether the closing of network is successful or not.
	 */
	public synchronized boolean stopSeverThread()
	{
		boolean retVal = false;
		if(this.targetNetworkConnecion != null && this.targetNetworkInterface != null)
		{
			retVal = this.targetNetworkInterface.closeConnection(this.targetNetworkConnecion);
		}
		return retVal;
	}
	
	/***
	 * This method returns response object(if present) for a given unique id 
	 * @param uniqueID unique id of the response.
	 * @return ResponseInfo if present or null
	 */
	@CanReturnNull
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
	
	/***
	 *The main message request processing method.
	 */
	@Override
	public void run()
	{
		while(true)
		{
			CommandRequestInfo toProcess = null;
			//1. Wait till you see any command.
			while(toProcessCommands.size() == 0)
			{
				//Do nothing. Just sleep.
				try 
				{
					Thread.sleep(pollTimeMilliseconds);
				} catch (InterruptedException e) 
				{
					Logger.logException("Error occured while sleeping", e);
				}
			}
			//2. One you see a command, try to get the command.
			synchronized(toProcessCommands)
			{
				if(toProcessCommands.size() > 0)
				{
					toProcess = toProcessCommands.remove(0);
				}
			}
			//3. If you have a command, process it.
			if(toProcess != null)
			{
				ServerCommandHandler commandHandler = CommandHandlerFactory.getServerCommandHandler(toProcess);
				CommandResponseInfo resultInfo = commandHandler.processCommand(targetNetworkConnecion, toProcess);
				if(resultInfo == null)
				{
					Logger.logError("Problem occured while processing command:"+toProcess.toString() +" on network connection:"+targetNetworkConnecion.toString());
					isErrorOccured = true;
					//End of processing. An Error occurred here.
					//We will stop and the thread will be killed.
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
