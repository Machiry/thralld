/**
 * 
 */
package com.thralld.common.utilities;

import com.thralld.common.annotations.CanReturnNull;
import com.thralld.common.aobjects.CommandRequestInfo;
import com.thralld.common.aobjects.ServerCommandHandler;
import com.thralld.common.commandhandlers.QueryCommandServerHandler;
import com.thralld.common.commands.QueryCommandRequestInfo;

/**
 * This class returns command handlers for all the supported commands
 * 
 * @author m4kh1ry
 *
 */
public class CommandHandlerFactory 
{
	/***
	 * This method returns server side command handler for provided RequestInfo
	 * @param toProcessRequest The target requestInfo to process
	 * @return ServerCommandHandler capable of handling the command or null if no handler exists.
	 */
	@CanReturnNull
	public static ServerCommandHandler getServerCommandHandler(CommandRequestInfo toProcessRequest)
	{
		ServerCommandHandler toRet = null;
		if(toProcessRequest != null)
		{
			if(toProcessRequest instanceof QueryCommandRequestInfo)
			{
				toRet = new QueryCommandServerHandler();
			}
		}
		return toRet;
	}

}
