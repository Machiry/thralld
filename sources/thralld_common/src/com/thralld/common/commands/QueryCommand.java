/**
 * 
 */
package com.thralld.common.commands;

import com.thralld.common.annotations.ThralldCommandVersion;
import com.thralld.common.aobjects.Command;

/**
 * @author m4kh1ry
 *
 */
@ThralldCommandVersion("1.0")
public class QueryCommand extends Command 
{
	private static final int COMMAND_ID=4;
	private static final String COMMAND_NAME="QueryCommand";

	/* (non-Javadoc)
	 * @see com.thralld.common.aobjects.Command#getCommandID()
	 */
	@Override
	public int getCommandID() 
	{
		return COMMAND_ID;
	}

	/* (non-Javadoc)
	 * @see com.thralld.common.aobjects.Command#getCommandName()
	 */
	@Override
	public String getCommandName() 
	{
		return COMMAND_NAME;
	}

	/* (non-Javadoc)
	 * @see com.thralld.common.aobjects.Command#getCommandRequestInfoType()
	 */
	@Override
	public Class<QueryCommandRequestInfo> getCommandRequestInfoType() 
	{
		return QueryCommandRequestInfo.class;
	}

	/* (non-Javadoc)
	 * @see com.thralld.common.aobjects.Command#getCommandScheduleInfoType()
	 */
	@Override
	public Class<QueryCommandScheduleInfo> getCommandScheduleInfoType() 
	{
		return QueryCommandScheduleInfo.class;
	}

	/* (non-Javadoc)
	 * @see com.thralld.common.aobjects.Command#getCommandResponseInfoType()
	 */
	@Override
	public Class<QueryCommandResponseInfo> getCommandResponseInfoType() 
	{
		return QueryCommandResponseInfo.class;
	}

}
