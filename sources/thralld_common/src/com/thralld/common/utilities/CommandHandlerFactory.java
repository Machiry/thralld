/**
 * 
 */
package com.thralld.common.utilities;

import com.thralld.common.aobjects.CommandRequestInfo;
import com.thralld.common.aobjects.ServerCommandHandler;
import com.thralld.common.commandhandlers.QueryCommandServerHandler;
import com.thralld.common.commands.QueryCommandRequestInfo;

/**
 * @author m4kh1ry
 *
 */
public class CommandHandlerFactory 
{
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
