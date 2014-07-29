/**
 * 
 */
package com.thralld.common.aobjects;

import java.io.Serializable;

/**
 * Every Command Response Info should inherit this class.
 * 
 * This class should be serializable because this will be transferred between client and server
 * @author m4kh1ry
 *
 */
public abstract class CommandResponseInfo implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 313085552277730818L;
	
	//We should have copy constructor for this class as this is serializable
	public CommandResponseInfo()
	{
			
	}

}
