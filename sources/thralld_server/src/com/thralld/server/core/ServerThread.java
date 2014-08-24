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
import com.thralld.server.interfaces.IServerThreadFeedback;

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
	//This variable indicates that current thread need to be terminated.
	private boolean toTerminate = false;
	//This is the interface used to provide feedback about creation and termination of threads.
	private IServerThreadFeedback serverMainFeedback = null;
	//This holds the current command that is being processed.
	private CommandRequestInfo currentCommand = null;
	
	public ServerThread(NetworkConnection netConn,INetworkInterface netInter,IServerThreadFeedback serverMainFeedback)
	{
		this.targetNetworkConnecion = netConn;
		this.targetNetworkInterface = netInter;
		this.serverMainFeedback = serverMainFeedback;
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
	 * This method gets the status of the command from its unique id.
	 * 
	 * @param uniqueID Unique id of the command to fetch the status
	 * 
	 * @return CommandState of the command.
	 */
	public CommandState getCommandState(String uniqueID)
	{
		if(currentCommand != null && currentCommand.transactionID.equals(uniqueID))
		{
			return CommandState.RUNNING;
		}
		synchronized (toProcessCommands) 
		{	
			for(CommandRequestInfo commReq:toProcessCommands)
			{
				if(commReq.transactionID.equals(uniqueID))
				{
					return CommandState.INQUEUE;
				}
			}
		}
		synchronized (commandResults) 
		{
			for(CommandResponseInfo comRes:commandResults)
			{
				if(comRes.transactionID.equals(uniqueID))
				{
					return CommandState.RESPONSE_READY;
				}
			}
		}
		return CommandState.UNKNOWN;
	}
	
	private synchronized boolean stopSeverThreadConnection()
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
	 * This function terminates the current thread servicing client.
	 */
	public void terminateServerThread()
	{
		toTerminate = true;
		stopSeverThreadConnection();
	}
	
	/***
	 *The main message request processing method.
	 */
	@Override
	public void run()
	{
		this.serverMainFeedback.updateNewClent(this.targetNetworkConnecion.getClientInfo(), this);
		while(true && !toTerminate)
		{
			CommandRequestInfo toProcess = null;
			//1. Wait till you see any command.
			while(toProcessCommands.size() == 0 && !toTerminate)
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
			if(toTerminate)
			{
				break;
			}
			//2. One you see a command, try to get the command.
			synchronized(toProcessCommands)
			{
				if(toProcessCommands.size() > 0)
				{
					toProcess = toProcessCommands.remove(0);
				}
			}
			if(toTerminate)
			{
				break;
			}
			//3. If you have a command, process it.
			if(toProcess != null)
			{
				currentCommand = toProcess;
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
			currentCommand = null;
			if(toTerminate)
			{
				break;
			}
		}
		//Set the current command to null.
		currentCommand = null;
		//Terminate the connection
		Logger.logInfo("Closing the connection to client thread:"+this.targetNetworkConnecion.toString());
		//Close the connection
		stopSeverThreadConnection();
		//Notify the main thread that client has terminated.
		this.serverMainFeedback.notifyTerminatingClient(this.targetNetworkConnecion.getClientInfo(), this);
	}
}
