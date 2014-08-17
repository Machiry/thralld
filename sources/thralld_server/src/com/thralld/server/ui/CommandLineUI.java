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
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.thralld.common.annotations.CanReturnNull;
import com.thralld.common.logging.Logger;
import com.thralld.common.objects.ClientInfo;
import com.thralld.common.utilities.GenericUtilities;
import com.thralld.server.core.ServerMain;
import com.thralld.server.interfaces.IServerStatusInterface;
import com.thralld.server.ui.interfaces.IClientsCommandHandler;
import com.thralld.server.ui.interfaces.IExitCommandHandler;
import com.thralld.server.ui.interfaces.IHelpCommandHandler;
import com.thralld.server.ui.interfaces.IServerStartCommandHandler;
import com.thralld.server.ui.interfaces.IServerStatusCommandHandler;

/**
 * This class handles command line UI for server.
 * @author m4kh1ry
 *
 */
public class CommandLineUI implements IServerStatusCommandHandler,IClientsCommandHandler,IHelpCommandHandler,IExitCommandHandler,IServerStartCommandHandler
{
	
	private static String commandPrompt = "thralld_server";
	private static IServerStatusInterface serverInterface = new ServerMain();
	private static HashMap<String,Object> availableCommands = new HashMap<String,Object>();
	
	@CanReturnNull
	private static String getCommandFromConsole()
	{
		String toRet = "";
		System.out.print(commandPrompt);
		System.out.flush();
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
		availableCommands.put("exit", commonCommandHandler);
		availableCommands.put("start", commonCommandHandler);
		
	}
	
	private static void displayHelpMessage()
	{
		handleCommand("help","help");
	}
	
	private static void handleCommand(String command,String providedCommandLine)
	{
		if(!runHandler(command, providedCommandLine))
		{
			runCommandHelp(command);
		}
	}
	
	private static boolean runHandler(String command,String providedCommandLine)
	{
		boolean retVal = false;
		if(command.equals("help"))
		{
			IHelpCommandHandler cmdHandler = (IHelpCommandHandler)availableCommands.get(command);
			if(cmdHandler != null)
			{
				retVal = cmdHandler.handleHelpCommand(providedCommandLine, System.out, serverInterface);
			}
		}
		if(command.equals("status"))
		{
			IServerStatusCommandHandler cmdHandler = (IServerStatusCommandHandler)availableCommands.get(command);
			if(cmdHandler != null)
			{
				retVal = cmdHandler.handleServerStatusCommand(providedCommandLine, System.out, serverInterface);
			}
		}
		if(command.equals("clients"))
		{
			IClientsCommandHandler cmdHandler = (IClientsCommandHandler)availableCommands.get(command);
			if(cmdHandler != null)
			{
				retVal = cmdHandler.handleClientsCommand(providedCommandLine, System.out, serverInterface);
			}
			
		}
		if(command.equals("exit"))
		{
			IExitCommandHandler cmdHandler = (IExitCommandHandler)availableCommands.get(command);
			if(cmdHandler != null)
			{
				retVal = cmdHandler.handleExitCommand(providedCommandLine, System.out, serverInterface);
			}
			
		}
		return retVal;
	}

	private static void runCommandHelp(String command)
	{
		if(command.equals("help"))
		{
			IHelpCommandHandler cmdHandler = (IHelpCommandHandler)availableCommands.get(command);
			if(cmdHandler != null)
			{
				cmdHandler.displayHelpCommandHelpMessage(System.out);
			}
		}
		if(command.equals("status"))
		{
			IServerStatusCommandHandler cmdHandler = (IServerStatusCommandHandler)availableCommands.get(command);
			if(cmdHandler != null)
			{
				cmdHandler.displayServerStatusCommandHelpMessage(System.out);
			}
		}
		if(command.equals("clients"))
		{
			IClientsCommandHandler cmdHandler = (IClientsCommandHandler)availableCommands.get(command);
			if(cmdHandler != null)
			{
				cmdHandler.displayClientsCommandHelpMessage(System.out);
			}
			
		}
		if(command.equals("exit"))
		{
			IExitCommandHandler cmdHandler = (IExitCommandHandler)availableCommands.get(command);
			if(cmdHandler != null)
			{
				cmdHandler.displayExitCommandHelpMessage(System.out);
			}
			
		}
	}
	
	
	/**
	 * @param args
	 * @throws UnknownHostException 
	 */
	public static void main(String[] args) throws UnknownHostException 
	{
		//Initialize Logger
		Logger.initialize("thralld_server started at:"+(new Date()).toString());
		
		System.out.println("thralld_server started at:"+(new Date()).toString());
		
		//Setup available commands
		setupAvailableCommands();
		
		//Set the command prompt
		commandPrompt += "@" + InetAddress.getLocalHost().getHostName() + ">";
		
		while(true)
		{
			//System.out.println("Waiting for commands");
			//1. Get the command entered
			String currCommandLine = getCommandFromConsole();
			
			if(currCommandLine != null)
			{
				currCommandLine = currCommandLine.trim();
				if(!currCommandLine.isEmpty())
				{
					//Get the name of the command
					String[] commandLineParts = currCommandLine.split(" ");
					String mainCommand = commandLineParts[0].toLowerCase();
					
					//If the command is present in available commands
					if(availableCommands.containsKey(mainCommand))
					{
						//Handle the command.
						handleCommand(mainCommand, currCommandLine);
					}
					else
					{
						//Display help message.
						displayHelpMessage();
					}
					
				}
			}
			
		}

	}

	//Help Command Handler
	
	@Override
	public boolean handleHelpCommand(String providedCommandLine,
			PrintStream outputStream,
			IServerStatusInterface targetServerInterface) 
	{
		outputStream.println("Available Commands:");
		for(String commandName:availableCommands.keySet())
		{
			outputStream.println(commandName);
			outputStream.println();
			runCommandHelp(commandName);
			outputStream.println();
		}
		return true;
	}

	@Override
	public void displayHelpCommandHelpMessage(PrintStream outputStream) 
	{
		outputStream.println("Usage: help");
		outputStream.println("This command displays available commands and other help information regarding server");
	}

	
	//Clients Command Handler
	@Override
	public boolean handleClientsCommand(String providedCommandLine,
			PrintStream outputStream,
			IServerStatusInterface targetServerInterface) 
	{
		List<ClientInfo> runningClients = targetServerInterface.getConnectedClients();
		if(runningClients != null && runningClients.size() > 0)
		{
			outputStream.println(Integer.toString(runningClients.size()) + " clients connected.");
			outputStream.println();
			for(ClientInfo currCli:runningClients)
			{
				outputStream.println(currCli.toString());
			}
			outputStream.println();
		}
		else
		{
			outputStream.println("No clients connected.");
		}
				
		return false;
	}

	@Override
	public void displayClientsCommandHelpMessage(PrintStream outputStream) 
	{
		outputStream.println("Usage: clients");
		outputStream.println("This command displays currently connected clients.");
		
	}

	
	//ServerStatus Command Handler
	@Override
	public boolean handleServerStatusCommand(String providedCommandLine,
			PrintStream outputStream,
			IServerStatusInterface targetServerInterface) 
	{
		if(targetServerInterface.isServerRunning())
		{
			outputStream.println("Server running.");
			outputStream.println("Status:");
			outputStream.println(targetServerInterface.getServerStatus());
		}
		else
		{
			outputStream.println("Server not running.");
		}
		return true;
	}

	@Override
	public void displayServerStatusCommandHelpMessage(PrintStream outputStream) 
	{
		outputStream.println("Usage: status");
		outputStream.println("This command provides the status of server.");
	}

	//Exit command handler
	@Override
	public boolean handleExitCommand(String providedCommandLine,
			PrintStream outputStream,
			IServerStatusInterface targetServerInterface) 
	{
		//Stop the server.
		Logger.logInfo("Trying to stop server.");
		if(serverInterface.stopServer())
		{
			Logger.logInfo("Server Sucessfully stopped");
		}
		else
		{
			Logger.logInfo("Problem occured while trying to stop server");
		}
		Logger.logInfo("Exiting the server");
		System.exit(0);
		return true;
	}

	@Override
	public void displayExitCommandHelpMessage(PrintStream outputStream) 
	{
		outputStream.println("Usage: exit");
		outputStream.println("This command terminates the current server and closes connections to clients");
		
	}

	//ServerStart command handler
	@Override
	public boolean handleServerStartCommand(String providedCommandLine,
			PrintStream outputStream,
			IServerStatusInterface targetServerInterface) 
	{
		String[] commandLineParts = GenericUtilities.splitBySpace(providedCommandLine);
		boolean restart = false;
		int serverPortNumber = 0;
		int portNumberIndex = 1;
		//Validate the arguments.
		if(commandLineParts.length > 3 || commandLineParts.length < 2)
		{
			return false;
		}
		
		if(commandLineParts.length > 2)
		{
			if(commandLineParts[1].equals("force"))
			{
				restart = true;
				portNumberIndex = 2;
			}
			else
			{
				return false;
			}
		}
		if(commandLineParts.length > 1)
		{
			if(GenericUtilities.isInteger(commandLineParts[portNumberIndex]))
			{
				serverPortNumber = Integer.parseInt(commandLineParts[portNumberIndex]);
			}
			else
			{
				return false;
			}
		}
		
		if(targetServerInterface.isServerRunning())
		{
			outputStream.println("Server running.");
			//Restart the server
			if(restart)
			{
				outputStream.println("Stopping Server.");
				if(targetServerInterface.stopServer())
				{
					outputStream.println("Server sucessfully stopped");
				}
				else
				{
					outputStream.println("Problem occured while stopping the server.");
				}
			}
			else
			{
				outputStream.println("Please specify 'force' to restart server");
				return true;
			}
		}
		//Start the server
		if(targetServerInterface.startServer(serverPortNumber))
		{
			outputStream.println("Server sucessfully started.");
		}
		else
		{
			outputStream.println("Problem occured while starting the server.");
		}
		return true;
	}

	@Override
	public void displayServerStartCommandHelpMessage(PrintStream outputStream) 
	{
		outputStream.println("Usage: start <force> [portNo]");
		outputStream.println("This command starts server main thread.");
		outputStream.println("\tSpecify 'force' if you want to restart the server.");
		outputStream.println("\tportNo is the port on which server thread has to listen.");
	}

}
