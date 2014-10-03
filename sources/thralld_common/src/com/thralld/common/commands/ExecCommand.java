/**
 * 
 */
package com.thralld.common.commands;

import com.thralld.common.aobjects.Command;

/**
 * This is implementation of ExecCommand,
 * Refer: https://docs.google.com/document/d/16YvvUXawvcwjnN2xpwCf30zaxRJgaqQaTfFRnSRDkiw for more details.
 * @author m4kh1ry
 *
 */
public class ExecCommand extends Command 
{

	private static final int COMMAND_ID=1;
	private static final String COMMAND_NAME="ExecCommand";
	/* (non-Javadoc)
	 * @see com.thralld.common.aobjects.Command#getCommandID()
	 */
	@Override
	public int getCommandID() 
	{
		return ExecCommand.COMMAND_ID;
	}

	/* (non-Javadoc)
	 * @see com.thralld.common.aobjects.Command#getCommandName()
	 */
	@Override
	public String getCommandName() 
	{
		return ExecCommand.COMMAND_NAME;
	}

	/* (non-Javadoc)
	 * @see com.thralld.common.aobjects.Command#getCommandRequestInfoType()
	 */
	@Override
	public Class<ExecCommandRequestInfo> getCommandRequestInfoType() 
	{
		return ExecCommandRequestInfo.class;
	}

	/* (non-Javadoc)
	 * @see com.thralld.common.aobjects.Command#getCommandScheduleInfoType()
	 */
	@Override
	public Class<ExecCommandScheduleInfo> getCommandScheduleInfoType() 
	{
		return ExecCommandScheduleInfo.class;
	}

	/* (non-Javadoc)
	 * @see com.thralld.common.aobjects.Command#getCommandResponseInfoType()
	 */
	@Override
	public Class<ExecCommandResponseInfo> getCommandResponseInfoType() 
	{
		return ExecCommandResponseInfo.class;
	}

}
