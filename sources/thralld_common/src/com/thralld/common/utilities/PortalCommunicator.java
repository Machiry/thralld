/**
 * 
 */
package com.thralld.common.utilities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.thralld.common.objects.PortalServerInfo;

/**
 * This class provides methods that help in interacting with portal
 * @author m4kh1ry
 *
 */
public class PortalCommunicator 
{
	/***
	 * This method gets the available server list from the portal.
	 * 
	 * @return List of PortalServerInfo objects representing each of the available server.
	 */
	public static List<PortalServerInfo> getServerList()
	{
		ArrayList<PortalServerInfo> toRet = new ArrayList<PortalServerInfo>();
		//TODO: Add implementation
		Collections.sort(toRet, new Comparator<PortalServerInfo>() 
				{
					@Override
					public int compare(PortalServerInfo o1, PortalServerInfo o2) 
					{
						return o2.preferenceInfo - o1.preferenceInfo;
					}
				});
		return toRet;
	}
	
	/***
	 * This method updates portal with the provided server info.
	 * @param toPost PortalServerInfo that needs to be updated on to portal.
	 * @return true/false depending on update being sucessful or not.
	 */
	public static boolean updateServerInfo(PortalServerInfo toPost)
	{
		boolean toRet = true;
		//TODO: Add implementation
		return toRet;
	}

}
