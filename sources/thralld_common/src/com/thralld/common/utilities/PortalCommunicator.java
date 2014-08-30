/**
 * 
 */
package com.thralld.common.utilities;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.thralld.common.logging.Logger;
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
	 * @param serverListUrl Webpage containing information about servers.
	 * @return List of PortalServerInfo objects representing each of the available server.
	 */
	public static List<PortalServerInfo> getServerList(String serverListUrl)
	{
		ArrayList<PortalServerInfo> toRet = new ArrayList<PortalServerInfo>();
		List<String> webpageContents = NetworkUtilities.readWebPage(serverListUrl);
		for(String currLine:webpageContents)
		{
			if(currLine != null)
			{
				String[] parts = currLine.split(":");
				if(parts.length == 3)
				{
					try
					{
						String serverIPAddr = null;
						String portNumber = null;
						int preferenceNumber = 0;
						if(InetAddress.getAllByName(parts[0]) != null && Integer.parseInt(parts[1]) > 0 && Integer.parseInt(parts[2]) > 0)
						{
							serverIPAddr = parts[0];
							portNumber = parts[1];
							preferenceNumber = Integer.parseInt(parts[2]);
						}
						PortalServerInfo currServInfo = new PortalServerInfo();
						currServInfo.serverNetworkName = serverIPAddr;
						currServInfo.serverNetworkPort = portNumber;
						currServInfo.preferenceInfo = preferenceNumber;
						toRet.add(currServInfo);
					}
					catch(Exception e)
					{
						//We ignore.
					}
				}
			}
		}
		
		//Here we are sorting based on preference, server having highest preference will be at the first
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
		//Do we need this functionality?
		/*
		 * IMO, this breaches security. See, we start a server and someone authorized should update the portal
		 * rather than automatically by server.
		 * 
		 * Need to think through.
		 */
		Logger.logInfo("Please update the portal which is read by client manually:"+ toPost.toString());
		return toRet;
	}

}
