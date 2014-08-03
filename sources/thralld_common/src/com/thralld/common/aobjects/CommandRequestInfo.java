/**
 * 
 */
package com.thralld.common.aobjects;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Every Command ReqeustInfo should inherit this class.
 * 
 * This class should be serializable because these are the objects we transfer between client and server
 * @author m4kh1ry
 *
 */
public abstract class CommandRequestInfo implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2100453935055536579L;
	public String transactionID = "";

	//We should have copy constructor for this class as this is serializable
	public CommandRequestInfo()
	{
		
	}
	
	/***
	 * This method returns names of required parameters by this command
	 * 
	 * @return Returns list of parameter names required by the command
	 */
	public abstract List<String> getRequiredParameters();
	
	/***
	 * This method sets the parameters for the command.
	 * 
	 * @param toSetParam Map containing values of all the required parameters.
	 * @return true if all the required parameters are set else false
	 */
	public abstract boolean setParameters(Map<String,Object> toSetParam);
	
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
