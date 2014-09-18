/**
 * 
 */
package com.thralld.common.commands;

import com.thralld.common.aobjects.Command;

/**
 * This is implementation of DownloadCommand,
 * Refer: https://docs.google.com/document/d/16YvvUXawvcwjnN2xpwCf30zaxRJgaqQaTfFRnSRDkiw for more details.
 * @author m4kh1ry
 *
 */
public class DownloadCommand extends Command 
{

	private static final int COMMAND_ID=3;
	private static final String COMMAND_NAME="DownloadCommand";
	
	/* (non-Javadoc)
	 * @see com.thralld.common.aobjects.Command#getCommandID()
	 */
	@Override
	public int getCommandID() 
	{
		return DownloadCommand.COMMAND_ID;
	}

	/* (non-Javadoc)
	 * @see com.thralld.common.aobjects.Command#getCommandName()
	 */
	@Override
	public String getCommandName() 
	{
		return DownloadCommand.COMMAND_NAME;
	}

	/* (non-Javadoc)
	 * @see com.thralld.common.aobjects.Command#getCommandRequestInfoType()
	 */
	@Override
	public Class<DownloadCommandRequestInfo> getCommandRequestInfoType() 
	{
		return DownloadCommandRequestInfo.class;
	}

	/* (non-Javadoc)
	 * @see com.thralld.common.aobjects.Command#getCommandScheduleInfoType()
	 */
	@Override
	public Class<DownloadCommandScheduleInfo> getCommandScheduleInfoType() 
	{
		return DownloadCommandScheduleInfo.class;
	}

	/* (non-Javadoc)
	 * @see com.thralld.common.aobjects.Command#getCommandResponseInfoType()
	 */
	@Override
	public Class<DownloadCommandResponseInfo> getCommandResponseInfoType() 
	{
		return DownloadCommandResponseInfo.class;
	}

}
