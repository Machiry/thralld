/**
 * 
 */
package com.thralld.common.aobjects;

import java.io.InputStream;
import java.io.OutputStream;

import com.thralld.common.annotations.CanReturnNull;

/**
 * Object containing members that are relevant to specific network implementation.
 * 
 * @author m4kh1ry
 *
 */
public abstract class NetworkConnection 
{
	
	/***
	 * This method returns input stream of the connection
	 * 
	 * @return InputStream of the connection or null
	 */
	@CanReturnNull
	public abstract InputStream getInputStream();
	
	/***
	 * This method returns output stream of the connection
	 * 
	 * @return OutputStream of the connection or null
	 */
	@CanReturnNull
	public abstract OutputStream getOutputStream();
}
