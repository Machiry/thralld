/**
 * 
 */
package com.thralld.server.interfaces;

/**
 * This enum represents state of the command.
 * 
 * @author m4kh1ry
 *
 */
public enum CommandState 
{
	INQUEUE("Command is in the queue to be processed."),RUNNING("Command is currently being executed at client."),RESPONSE_READY("Command completed execution and response is ready."),UNKNOWN("Command status is unknown");
	
	private String description;
	private CommandState(String des)
	{
		this.description = des;
	}
	
	@Override
	public String toString()
	{
		return this.description;
	}
	

}
