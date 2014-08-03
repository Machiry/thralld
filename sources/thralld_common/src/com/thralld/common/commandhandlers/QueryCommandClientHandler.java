/**
 * 
 */
package com.thralld.common.commandhandlers;

import java.util.List;

import com.thralld.common.aobjects.ClientCommandHandler;
import com.thralld.common.aobjects.Command;
import com.thralld.common.aobjects.CommandRequestInfo;
import com.thralld.common.aobjects.NetworkConnection;
import com.thralld.common.commands.QueryCommandRequestInfo;
import com.thralld.common.commands.QueryCommandResponseInfo;
import com.thralld.common.commands.QueryCommandScheduleInfo;
import com.thralld.common.logging.Logger;
import com.thralld.common.utilities.NetworkObjectSerializer;
import com.thralld.common.utilities.ReflectionHelper;

/**
 * @author m4kh1ry
 *
 */
public class QueryCommandClientHandler extends ClientCommandHandler 
{

	/* (non-Javadoc)
	 * @see com.thralld.common.aobjects.ClientCommandHandler#processCommand(com.thralld.common.aobjects.NetworkConnection, com.thralld.common.aobjects.CommandRequestInfo)
	 */
	@Override
	public boolean processCommand(NetworkConnection targetNetworkConnection,
			CommandRequestInfo toProcess) 
	{
		boolean toRet = false;
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
					Logger.logInfo("Starting Client QueryCommand processing on connection:"+targetNetworkConnection.toString());
					QueryCommandScheduleInfo scheduleInfo = new QueryCommandScheduleInfo();
					scheduleInfo.transactionID = toProcess.transactionID;
					if(!NetworkObjectSerializer.sendObject(targetNetworkConnection, scheduleInfo))
					{
						Logger.logError("Unable to send QueryCommandScheduleInfo to network connection:"+targetNetworkConnection.toString());
						break;
					}
					Logger.logInfo("QueryCommandScheduleInfo sent on connection:"+targetNetworkConnection.toString());
					
					//Construct response info object
					QueryCommandResponseInfo respInfo = new QueryCommandResponseInfo();
					respInfo.transactionID = toProcess.transactionID;
					List<Class<?>> commandClasses = ReflectionHelper.getCommandClasses();
					respInfo.noOfAvailableCommands = commandClasses.size();
					for(Class<?> commClass:commandClasses)
					{
						try
						{
							Command currComm = (Command)commClass.newInstance();
							respInfo.commandIds.add(currComm.getCommandID());
							respInfo.commandNames.add(currComm.getCommandName());
							respInfo.commandVersions.add(ReflectionHelper.getCommandVersion(currComm));
						}
						catch(Exception e)
						{
							Logger.logException("Problem occured while trying to add command class:"+commClass.getName()+ " to response info", e);
							respInfo.noOfAvailableCommands--;
						}
						
					}
					
					Logger.logInfo("Trying to send QueryCommandResponseInfo to connection:"+targetNetworkConnection.toString());
					
					if(!NetworkObjectSerializer.sendObject(targetNetworkConnection, respInfo))
					{
						Logger.logError("Unable to send QueryCommandResponseInfo to network connection:"+targetNetworkConnection.toString());
						break;
					}
					Logger.logInfo("QueryCommandResponseInfo sent on connection:"+targetNetworkConnection.toString());
					
					toRet = true;
				}
			} while(false);
		}
		return toRet;
	}

}
