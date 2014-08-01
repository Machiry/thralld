package com.thralld.server.core;

import java.util.ArrayList;

import com.thralld.common.aobjects.CommandRequestInfo;

public class ServerThread extends Thread 
{
	private ArrayList<CommandRequestInfo> toProcessCommands = new ArrayList<CommandRequestInfo>();
	
	public boolean addCommand(CommandRequestInfo comm)
	{
		synchronized (toProcessCommands) 
		{	
			if(comm!= null && !toProcessCommands.contains(comm))
			{
				toProcessCommands.add(comm);
				return true;
			}
		}
		return false;
	}
}
