/**
 * 
 */
package com.thralld.common.aobjects;

import com.thralld.common.logging.Logger;
import com.thralld.common.objects.ClientCommandQuery;
import com.thralld.common.objects.ClientCommandResponse;
import com.thralld.common.utilities.NetworkObjectSerializer;
import com.thralld.common.utilities.ReflectionHelper;



/**
 * This class represents the general structure of  server side command handler.
 * @author m4kh1ry
 *
 */
public abstract class ServerCommandHandler 
{
	/***
	 * This method handles the underlying command on server side
	 * It receives  a requestinfo and returns responseinfo.
	 * 
	 * @param targetNetworkConnection Network connection which needs to be used for communication.
	 * @param toProcess Request info for the command
	 * @return Received response info object or null (if an error occurs)
	 */
	public abstract CommandResponseInfo processCommand(NetworkConnection targetNetworkConnection,CommandRequestInfo toProcess);
	
	/***
	 * This method checks whether a given command is available at the client.
	 * 
	 * @param targetNetworkConnection Network connection of the client
	 * @param toProcess The target command that needs to be checked.
	 * @return true/false depending on whether command is available at client.
	 */
	protected boolean isCommandAvailableAtClient(NetworkConnection targetNetworkConnection,CommandRequestInfo toProcess)
	{
		boolean retVal = false;
		ClientCommandQuery commandQuery = new ClientCommandQuery();
		commandQuery.commandId = toProcess.getTargetCommand().getCommandID();
		commandQuery.commandVersion = ReflectionHelper.getCommandVersion(toProcess.getTargetCommand());
		if(!NetworkObjectSerializer.sendObject(targetNetworkConnection, commandQuery))
		{
			Logger.logError("Unable to send ClientCommandQuery to network connection:"+targetNetworkConnection.toString());
		}
		else
		{
			Logger.logInfo("Sent ClientCommandQuery to network connection:"+targetNetworkConnection);
			ClientCommandResponse resp = NetworkObjectSerializer.receiveObject(targetNetworkConnection, ClientCommandResponse.class);
			retVal = resp != null && resp.isAvailable();
		}
		if(retVal)
		{
			Logger.logInfo("Command:" +commandQuery.toString()+" Available on:"+targetNetworkConnection.toString());
		}
		else
		{
			Logger.logInfo("Command:" +commandQuery.toString()+" Not Available on:"+targetNetworkConnection.toString());
		}
		return retVal;
	}

}
