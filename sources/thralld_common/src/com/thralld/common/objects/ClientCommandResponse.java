/**
 * 
 */
package com.thralld.common.objects;

import java.io.Serializable;

/**
 * This class represents the initial response clients sends when a command is queried.
 * @author m4kh1ry
 *
 */
//We need serializable because we should be able to send the objects over network.
public class ClientCommandResponse implements Serializable 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5338037344364719908L;
	
	public String responseString = "";
	
	/***
	 * This method returns ClientCommandResponse object which indicates that command is available.
	 * 
	 * @return ClientCommandResponse indicating that underlying command is available.
	 */
	public static ClientCommandResponse getAvailableResponse()
	{
		ClientCommandResponse resp = new ClientCommandResponse();
		resp.responseString = ClientCommandQuery.AVAILABLE;
		return resp;
	}
	
	/***
	 * This method returns ClientCommandResponse object which indicates that command is not available.
	 * 
	 * @return ClientCommandResponse indicating that underlying command is not available.
	 */
	public static ClientCommandResponse getNotAvailableResponse()
	{
		ClientCommandResponse resp = new ClientCommandResponse();
		resp.responseString = ClientCommandQuery.NOTAVAILABLE;
		return resp;
	}
	
	/***
	 * This method is used to check whether the underlying response object indicates available.
	 * 
	 * @return true/false depending on whether this response object indicates available or not.
	 */
	public boolean isAvailable()
	{
		return this.responseString != null && this.responseString.equals(ClientCommandQuery.AVAILABLE);
	}

}
