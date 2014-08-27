/**
 * 
 */
package com.thralld.server.interfaces;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.thralld.common.annotations.CanReturnNull;
import com.thralld.common.aobjects.CommandRequestInfo;
import com.thralld.common.aobjects.CommandResponseInfo;
import com.thralld.common.objects.ClientInfo;

/**
 * 
 * This interface specifies various methods useful to interact with server core.
 * @author m4kh1ry
 *
 */
public interface IServerStatusInterface 
{
	/***
	 * This method returns list of connected ClientInfo objects by their name
	 * 
	 * @param clientName client name to search for
	 * @return List of client info objects matching the name.
	 */
	public abstract List<ClientInfo> getClientInfoByName(String clientName);
	
	/***
	 * This method returns list of current connected clients.
	 * 
	 * @return List of currently connected client objects.
	 */
	public abstract ArrayList<ClientInfo> getConnectedClients();
	
	/***
	 * Start server on the specified port.
	 * 
	 * @param portNumber Port number to listen on.
	 * @return true if starting is successful / false if unsuccessful.
	 */
	public abstract boolean startServer(int portNumber);
	
	/***
	 * Stop currently running server.
	 * @return true if stopping is successful / false if unsuccessful.
	 */
	public abstract boolean stopServer();
	
	/***
	 * Get map of available commands and their version
	 * 
	 * @return Map of command names and their version.
	 */
	public abstract HashMap<String,String> getAvailableCommands();
	
	/***
	 * Get list of parameters required for the given command.
	 * @param commandName Command for which parameters are required.
	 * @return List of parameter names supported by given command.
	 */
	public abstract List<String> getCommandParameters(String commandName);
	
	/***
	 * This method sets selection preference of the current server.
	 * 
	 * @param preferenceNumber New preference number to set.
	 */
	public abstract void setServerPreference(int preferenceNumber);
	
	/***
	 * This method gets the current selection preference of the server.
	 * 
	 * @return preference number of the server
	 */
	public abstract int getServerPreference();
	
	/***
	 * This method checks whether the server main thread is running or not.
	 * 
	 * @return True/False depending on whether the server is running or not.
	 */
	public abstract boolean isServerRunning();
	
	/***
	 * This method return the status of currently running server.
	 * 
	 * @return String representing status of the server.
	 */
	public abstract String getServerStatus();
	
	/***
	 * This method inserts the provided command request info object into the given client queue.
	 * 
	 * @param targetClient The target client into which the command need to be queued.
	 * @param toRunCommand The target command to run.
	 * 
	 * @return True/False depending on whether the scheduling is successful or not.
	 */
	public abstract boolean scheduleClientCommand(ClientInfo targetClient,CommandRequestInfo toRunCommand);
	
	/***
	 * This method gets the state of the provided command on the given client.
	 * 
	 * @param targetClient The client on which status of the command need to be queried.
	 * @param uniqueID Unique id of the command that needs to be queried.
	 * @return CommandState representing state of the command.
	 */
	public abstract CommandState getCommandState(ClientInfo targetClient,String uniqueID);
	
	/***
	 * This method gets the state of all the commands known to the provided client.
	 * 
	 * @param targetClient The client whose status needs to be queried.
	 * @return Map of CommandRequestInfo and corresponding CommandState objects.
	 */
	public abstract Map<CommandRequestInfo,CommandState> getCurrentCommandsState(ClientInfo targetClient);
	
	/***
	 * This method gets the CommandResponseInfo object of the command from the provided client.
	 * 
	 * @param targetClient The client from which response needs to be fetched.
	 * @param uniqueID The unique id of the command whose response needs to be fetched.
	 * @return CommandResponseInfo of the target command.
	 */
	@CanReturnNull
	public abstract CommandResponseInfo getCommandResponse(ClientInfo targetClient,String uniqueID);

}
