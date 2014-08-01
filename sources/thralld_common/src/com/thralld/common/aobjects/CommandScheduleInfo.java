/**
 * 
 */
package com.thralld.common.aobjects;

import java.io.Serializable;

/**
 * Every command's schedule info should inherit this class.
 * 
 * This class should be serializable because these are the objects we transfer between client and server
 * @author m4kh1ry
 *
 */
public abstract class CommandScheduleInfo implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 10588553931099462L;
	public String transactionID = null;

	//We should have copy constructor for this class as this is serializable
	public CommandScheduleInfo()
	{
		
	}
	
	@Override
	public int hashCode()
	{
		return this.transactionID.hashCode();
	}
	
	@Override
	public boolean equals(Object o)
	{
		if(o != null && (o instanceof CommandRequestInfo))
		{
			return this.transactionID.equals(((CommandRequestInfo)o).transactionID);
		}
		return false;
	}
}
