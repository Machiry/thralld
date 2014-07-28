/**
 * 
 */
package com.thralld.common.objects;

import java.util.HashMap;

/**
 * This object represents a client and contains all the information required to identify it.
 * @author m4kh1ry
 *
 */
public class ClientInfo 
{
	public String clientName = "";
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
		retVal = this.clientName.hashCode() ^ this.propertiesMap.hashCode();
		return retVal;
	}
	
	@Override
	public boolean equals(Object o)
	{
		boolean retVal = false;
		if(o != null)
		{
			ClientInfo toCompare = (ClientInfo)o;
			retVal = this.clientName.equals(toCompare.clientName) && this.propertiesMap.equals(toCompare.propertiesMap);
		}
		return retVal;
	}
}
