/**
 * 
 */
package com.thralld.common.aobjects;

/**
 * This class represents generic command and any specific command should inherit this class.
 * @author m4kh1ry
 *
 */
//This annotation is required because there are few methods in the class which
//return raw types
@SuppressWarnings("rawtypes")
public abstract class Command 
{
	/***
	 * This method returns the command id of the command
	 * 
	 * @return integer representing command id
	 */
	public abstract int getCommandID();
	
	/***
	 * This method returns name of the command
	 * @return String Name of the command
	 */
	public abstract String getCommandName();
	
	/***
	 * This method returns Class of RequestInfo used by the command
	 * @return Class representing request_info structure used by the command
	 */
	public abstract Class getCommandRequestInfoType();
	
	/***
	 * This method returns Class of the ScheduleInfo used by the command
	 * @return Class representing schedule_info structure used by the command
	 */
	public abstract Class getCommandScheduleInfoType();
	
	/***
	 * This method returns Class of the ResponseInfo used by the command
	 * @return Class representing response_info structure used by the command.
	 */
	public abstract Class getCommandResponseInfoType();
	
}
