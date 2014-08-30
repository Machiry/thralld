/**
 * 
 */
package com.thralld.common.objects;

/**
 * This object represents the information obtained about each server on the portal.
 * @author m4kh1ry
 *
 */
public class PortalServerInfo 
{
	public String serverNetworkName = "";
	public String serverNetworkPort = "";
	public Integer preferenceInfo = 50;
	
	@Override
	public boolean equals(Object o)
	{
		if(o != null && o instanceof PortalServerInfo)
		{
			PortalServerInfo that = (PortalServerInfo)o;
			return this.serverNetworkName.equals(that.serverNetworkName)
					&& this.serverNetworkPort.equals(that.serverNetworkPort)
					&& this.preferenceInfo == that.preferenceInfo;
		}
		return false;
	}
	
	@Override
	public int hashCode()
	{
		return this.serverNetworkName.hashCode() ^ this.serverNetworkPort.hashCode() ^ this.preferenceInfo.hashCode();
	}
	
	@Override
	public String toString()
	{
		return serverNetworkName + ":PortNo=" + serverNetworkPort + ":PreferenceNo=" + Integer.toString(preferenceInfo);
	}
}
