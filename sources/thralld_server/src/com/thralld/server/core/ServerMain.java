/**
 * 
 */
package com.thralld.server.core;
import java.util.ArrayList;
import java.util.HashMap;

import com.thralld.common.objects.ClientInfo;

/**
 * @author m4kh1ry
 *
 */
public class ServerMain 
{
	public HashMap<ClientInfo,ServerThread> currentProcessingThreads = new HashMap<ClientInfo, ServerThread>();
	
	private ClientInfo getClientInfoByName(String clientName)
	{
		ClientInfo toRet = null;
		for(ClientInfo cli:currentProcessingThreads.keySet())
		{
			if(cli.clientName.equals(clientName))
			{
				toRet = cli;
				break;
			}
		}
		return toRet;
	}
	
	public ArrayList<ClientInfo> getConnectedClients()
	{
		ArrayList<ClientInfo> toRet = new ArrayList<ClientInfo>(currentProcessingThreads.keySet());
		return toRet;
	}

}
