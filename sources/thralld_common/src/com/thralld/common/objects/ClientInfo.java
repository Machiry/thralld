/**
 * 
 */
package com.thralld.common.objects;

import com.thralld.common.aobjects.ConnectedEntityInfo;

/**
 * This object represents a client and contains all the information required to identify it.
 * @author m4kh1ry
 *
 */
public class ClientInfo extends ConnectedEntityInfo
{
	/***
	 * This method sets the name of this ClientInfo to the given name
	 * 
	 * @param clientName New name for this ClientInfo object
	 */
	public void setClientName(String clientName)
	{
		this.entityName = clientName;
	}
	
	/***
	 * This method returns name of this client info
	 * 
	 * @return Returns name of the current client
	 */
	public String getClientName()
	{
		return this.entityName;
	}
}
