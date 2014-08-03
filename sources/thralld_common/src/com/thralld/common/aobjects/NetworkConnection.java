/**
 * 
 */
package com.thralld.common.aobjects;

import java.io.InputStream;
import java.io.OutputStream;

import com.thralld.common.annotations.CanReturnNull;
import com.thralld.common.objects.ClientInfo;
import com.thralld.common.objects.ServerInfo;

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
	
	/***
	 * This method returns the ClientInfo object representing this connection.
	 * @return Target ClientInfo object or null (if error occurred)
	 */
	@CanReturnNull
	public abstract ClientInfo getClientInfo();
	
	/***
	 * This method returns the ServerInfo object representing this connection.
	 * @return Target ServerInfo object or null (if error occurred)
	 */
	@CanReturnNull
	public abstract ServerInfo getServerInfo();
}
