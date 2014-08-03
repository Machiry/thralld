/**
 * 
 */
package com.thralld.server.interfaces;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

}
