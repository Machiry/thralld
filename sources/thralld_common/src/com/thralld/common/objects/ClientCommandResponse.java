/**
 * 
 */
package com.thralld.common.objects;

import java.io.Serializable;

/**
 * @author m4kh1ry
 *
 */
public class ClientCommandResponse implements Serializable 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5338037344364719908L;
	
	public String responseString = "";
	
	public static ClientCommandResponse getAvailableResponse()
	{
		ClientCommandResponse resp = new ClientCommandResponse();
		resp.responseString = ClientCommandQuery.AVAILABLE;
		return resp;
	}
	
	public static ClientCommandResponse getNotAvailableResponse()
	{
		ClientCommandResponse resp = new ClientCommandResponse();
		resp.responseString = ClientCommandQuery.NOTAVAILABLE;
		return resp;
	}
	
	public boolean isAvailable()
	{
		return this.responseString != null && this.responseString.equals(ClientCommandQuery.AVAILABLE);
	}

}
