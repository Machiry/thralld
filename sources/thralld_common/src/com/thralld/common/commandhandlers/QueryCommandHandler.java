/**
 * 
 */
package com.thralld.common.commandhandlers;

import com.thralld.common.aobjects.CommandHandler;
import com.thralld.common.aobjects.NetworkConnection;
import com.thralld.common.commands.QueryCommandRequestInfo;
import com.thralld.common.commands.QueryCommandResponseInfo;
import com.thralld.common.commands.QueryCommandScheduleInfo;
import com.thralld.common.logging.Logger;
import com.thralld.common.utilities.NetworkObjectSerializer;

/**
 * @author m4kh1ry
 *
 */
public class QueryCommandHandler extends CommandHandler 
{
	@Override
	public QueryCommandRequestInfo receiveRequestInfo(NetworkConnection targetNetworkConnection)
	{
		return NetworkObjectSerializer.receiveObject(targetNetworkConnection, QueryCommandRequestInfo.class);
	}
	
	@Override
	public QueryCommandScheduleInfo receiveScheduleInfo(NetworkConnection targetNetworkConnection)
	{
		return NetworkObjectSerializer.receiveObject(targetNetworkConnection, QueryCommandScheduleInfo.class);
	}
	
	@Override
	public QueryCommandResponseInfo receiveResponseInfo(NetworkConnection targetNetworkConnection) throws Exception
	{
		QueryCommandResponseInfo toRet = NetworkObjectSerializer.receiveObject(targetNetworkConnection, QueryCommandResponseInfo.class);
		if(toRet.commandIds.size() != toRet.noOfAvailableCommands || toRet.commandNames.size() != toRet.noOfAvailableCommands || toRet.commandVersions.size() != toRet.noOfAvailableCommands)
		{
			Logger.logError("Invalid QueryCommandResponseInfo received");
			throw new Exception("Invalid QueryCommandResponseInfo received");
		}
		return toRet;
	}
}
