/**
 * 
 */
package com.thralld.common.aobjects;

import com.thralld.common.utilities.NetworkObjectSerializer;

/**
 * @author m4kh1ry
 *
 */
public abstract class CommandHandler 
{
	public boolean sendRequestInfo(CommandRequestInfo toSend,NetworkConnection targetNetworkConnection)
	{
		return NetworkObjectSerializer.sendObject(targetNetworkConnection, toSend);
	}
	
	public CommandRequestInfo receiveRequestInfo(NetworkConnection targetNetworkConnection)
	{
		return NetworkObjectSerializer.receiveObject(targetNetworkConnection, CommandRequestInfo.class);
	}
	
	public boolean sendScheduleInfo(CommandScheduleInfo toSend,NetworkConnection targetNetworkConnection)
	{
		return NetworkObjectSerializer.sendObject(targetNetworkConnection, toSend);
	}
	
	public CommandScheduleInfo receiveScheduleInfo(NetworkConnection targetNetworkConnection)
	{
		return NetworkObjectSerializer.receiveObject(targetNetworkConnection, CommandScheduleInfo.class);
	}
	
	public boolean sendResponseInfo(CommandResponseInfo toSend,NetworkConnection targetNetworkConnection)
	{
		return NetworkObjectSerializer.sendObject(targetNetworkConnection, toSend);
	}
	
	public CommandResponseInfo receiveResponseInfo(NetworkConnection targetNetworkConnection) throws Exception
	{
		return NetworkObjectSerializer.receiveObject(targetNetworkConnection, CommandResponseInfo.class);
	}

}
