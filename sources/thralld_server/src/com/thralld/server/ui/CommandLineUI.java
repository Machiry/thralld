/**
 * 
 */
package com.thralld.server.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import com.thralld.common.annotations.CanReturnNull;
import com.thralld.common.logging.Logger;
import com.thralld.server.core.ServerMain;
import com.thralld.server.interfaces.IServerStatusInterface;
import com.thralld.server.ui.interfaces.IClientsCommandHandler;
import com.thralld.server.ui.interfaces.IHelpCommandHandler;
import com.thralld.server.ui.interfaces.IServerStatusCommandHandler;

/**
 * This class handles command line UI for server.
 * @author m4kh1ry
 *
 */
public class CommandLineUI implements IServerStatusCommandHandler,IClientsCommandHandler,IHelpCommandHandler
{
	
	private static String commandPrompt = "thralld_server";
	private IServerStatusInterface serverInterface = new ServerMain();
	private static HashMap<String,Object> availableCommands = new HashMap<String,Object>();
	
	@CanReturnNull
	private static String getCommandFromConsole()
	{
		String toRet = "";
		System.out.print(commandPrompt);
		BufferedReader bufferedInputReader = new BufferedReader(new InputStreamReader(System.in));
		try 
		{
	         toRet = bufferedInputReader.readLine();
	    } 
		catch (IOException e) 
		{
	         Logger.logException("Problem occured while reading line from console", e);
	    }
		return toRet;
	}
	
	private static void setupAvailableCommands()
	{
		CommandLineUI commonCommandHandler = new CommandLineUI();
		availableCommands.put("help", commonCommandHandler);
		availableCommands.put("status",commonCommandHandler);
		availableCommands.put("clients", commonCommandHandler);
		
	}

	
	
	/**
	 * @param args
	 * @throws UnknownHostException 
	 */
	public static void main(String[] args) throws UnknownHostException 
	{
		//Initialize Logger
		Logger.initialize("thralld_server started at:"+(new Date()).toString());
		
		//Setup available commands
		setupAvailableCommands();
		
		//Set the command prompt
		commandPrompt += "@" + InetAddress.getLocalHost().getHostName() + ">";
		
		while(true)
		{
			String currCommandLine = getCommandFromConsole();
			if(currCommandLine != null)
			{
				currCommandLine = currCommandLine.trim();
				if(!currCommandLine.isEmpty())
				{
					String[] commandLineParts = currCommandLine.split(" ");
					String mainCommand = commandLineParts[0].toLowerCase();
					if(availableCommands.containsKey(mainCommand))
					{
						
					}
					
				}
			}
			
		}

	}

	//Help Command Handler
	
	@Override
	public boolean handleHelpCommand(String providedCommandLine,
			PrintStream outputStream,
			IServerStatusInterface targetServerInterface) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void displayHelpCommandHelpMessage(PrintStream outputStream) {
		// TODO Auto-generated method stub
		
	}

	
	//Clients Command Handler
	@Override
	public boolean handleClientsCommand(String providedCommandLine,
			PrintStream outputStream,
			IServerStatusInterface targetServerInterface) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void displayClientsCommandHelpMessage(PrintStream outputStream) {
		// TODO Auto-generated method stub
		
	}

	
	//ServerStatus Command Handler
	@Override
	public boolean handleServerStatusCommand(String providedCommandLine,
			PrintStream outputStream,
			IServerStatusInterface targetServerInterface) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void displayServerStatusCommandHelpMessage(PrintStream outputStream) {
		// TODO Auto-generated method stub
		
	}

}
