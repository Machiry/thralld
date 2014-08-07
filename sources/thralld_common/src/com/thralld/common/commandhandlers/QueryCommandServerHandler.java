/**
 * 
 */
package com.thralld.common.commandhandlers;

import com.thralld.common.annotations.CanReturnNull;
import com.thralld.common.aobjects.CommandRequestInfo;
import com.thralld.common.aobjects.NetworkConnection;
import com.thralld.common.aobjects.ServerCommandHandler;
import com.thralld.common.commands.QueryCommandRequestInfo;
import com.thralld.common.commands.QueryCommandResponseInfo;
import com.thralld.common.commands.QueryCommandScheduleInfo;
import com.thralld.common.logging.Logger;
import com.thralld.common.utilities.NetworkObjectSerializer;

/**
 * This class implements server side command handler for QueryCommand
 * @author m4kh1ry
 *
 */
public class QueryCommandServerHandler extends ServerCommandHandler 
{

	@Override
	@CanReturnNull
	public QueryCommandResponseInfo processCommand(NetworkConnection targetNetworkConnection,CommandRequestInfo toProcess) 
	{
		QueryCommandResponseInfo toRet = null;
		if(toProcess != null && targetNetworkConnection !=null)
		{
			do
			{
				if(!(toProcess instanceof QueryCommandRequestInfo))
				{
					Exception e = new Exception("Improper request info object.");
					Logger.logException("RequestInfo object is not instance of QueryCommandRequestInfo", e);
				}
				else
				{
					Logger.logInfo("Starting QueryCommand processing on connection:"+targetNetworkConnection.toString());
					if(!isCommandAvailableAtClient(targetNetworkConnection, toProcess))
					{
						//Command not available on client.
						break;
					}
					
					if(!NetworkObjectSerializer.sendObject(targetNetworkConnection, toProcess))
					{
						Logger.logError("Unable to send QueryCommandRequestInfo to network connection:"+targetNetworkConnection.toString());
						break;
					}
					Logger.logInfo("QueryCommandRequestInfo sent on connection:"+targetNetworkConnection.toString());
					QueryCommandScheduleInfo scheduleInfo = NetworkObjectSerializer.receiveObject(targetNetworkConnection, QueryCommandScheduleInfo.class);
					if(scheduleInfo == null)
					{
						Logger.logError("Unable to receive QueryCommandScheduleInfo from network connection:"+targetNetworkConnection.toString());
						break;
					}
					Logger.logInfo("QueryCommandScheduleInfo received from connection:"+targetNetworkConnection.toString());
					if(scheduleInfo.transactionID != toProcess.transactionID)
					{
						Logger.logError("Incorrect schedule info object received. Sent:"+toProcess.transactionID +" Received:"+scheduleInfo.transactionID);
					}
					toRet = NetworkObjectSerializer.receiveObject(targetNetworkConnection, QueryCommandResponseInfo.class);
					if(toRet == null)
					{
						Logger.logInfo("Unable to receive QueryCommandResponseInfo from network connection:"+targetNetworkConnection.toString());
						break;
					}
					Logger.logInfo("QueryCommandResponseInfo received from connection:"+targetNetworkConnection.toString());
					if(!toRet.isValid())
					{
						Logger.logError("Improper QueryResponseInfo object received for:"+toProcess.transactionID);
					}
				}
			} while(false);
		}
		return toRet;
	}
	
}
