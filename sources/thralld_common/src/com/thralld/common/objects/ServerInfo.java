/**
 * 
 */
package com.thralld.common.objects;

import com.thralld.common.aobjects.ConnectedEntityInfo;

/**
 * This object contains all the information regarding a server
 * @author m4kh1ry
 *
 */
public class ServerInfo extends ConnectedEntityInfo
{
	/***
	 * This method sets the name of the server info object.
	 * @param serverName new name for server 
	 */
	public void setServerName(String serverName)
	{
		this.entityName = serverName;
	}
	
	/***
	 * This method gets the name of the server
	 * @return Server name of this ServerInfo object.
	 */
	public String getServerName()
	{
		return this.entityName;
	}

}
