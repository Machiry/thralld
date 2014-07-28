/**
 * 
 */
package com.thralld.common.objects;

import java.util.HashMap;

/**
 * This object contains all the information regarding a server
 * @author m4kh1ry
 *
 */
public class ServerInfo 
{
	public String serverName = "";
	public HashMap<String,String> propertiesMap = new HashMap<String, String>();
	
	@Override
	public String toString()
	{
		String toRet = null;
		return toRet;
	}
	
	@Override
	public int hashCode()
	{
		int retVal = 0;
		retVal = this.serverName.hashCode() ^ this.propertiesMap.hashCode();
		return retVal;
	}
	
	@Override
	public boolean equals(Object o)
	{
		boolean retVal = false;
		if(o != null)
		{
			ServerInfo toCompare = (ServerInfo)o;
			retVal = this.serverName.equals(toCompare.serverName) && this.propertiesMap.equals(toCompare.propertiesMap);
		}
		return retVal;
	}

}
