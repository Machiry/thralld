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
import java.util.Map;
import java.util.logging.Level;

import com.thralld.common.annotations.CanReturnNull;
import com.thralld.common.aobjects.Command;
import com.thralld.common.aobjects.CommandRequestInfo;
import com.thralld.common.aobjects.CommandResponseInfo;
import com.thralld.common.logging.Logger;
import com.thralld.common.objects.ClientInfo;
import com.thralld.common.utilities.GenericUtilities;
import com.thralld.common.utilities.ReflectionHelper;
import com.thralld.server.core.ServerMain;
import com.thralld.server.interfaces.CommandState;
import com.thralld.server.interfaces.IServerStatusInterface;
import com.thralld.server.ui.interfaces.IClientCommandHandler;
import com.thralld.server.ui.interfaces.IClientsCommandHandler;
import com.thralld.server.ui.interfaces.ICommandParametersCommandHandler;
import com.thralld.server.ui.interfaces.ICommandsCommandHandler;
import com.thralld.server.ui.interfaces.IExitCommandHandler;
import com.thralld.server.ui.interfaces.IHelpCommandHandler;
import com.thralld.server.ui.interfaces.ILogLevelCommandHandler;
import com.thralld.server.ui.interfaces.IServerStartCommandHandler;
import com.thralld.server.ui.interfaces.IServerStatusCommandHandler;

/**
 * This class handles command line UI for server.
 * @author m4kh1ry
 *
 */
public class CommandLineUI implements IServerStatusCommandHandler,IClientsCommandHandler,
IHelpCommandHandler,IExitCommandHandler,IServerStartCommandHandler,ICommandsCommandHandler,
ICommandParametersCommandHandler,IClientCommandHandler,ILogLevelCommandHandler
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
		availableCommands.put("commands", commonCommandHandler);
		availableCommands.put("comm_params", commonCommandHandler);
		availableCommands.put("client", commonCommandHandler);
		availableCommands.put("loglevel", commonCommandHandler);
		
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
		if(command.equals("start"))
		{
			IServerStartCommandHandler cmdHandler = (IServerStartCommandHandler)availableCommands.get(command);
			if(cmdHandler != null)
			{
				retVal = cmdHandler.handleServerStartCommand(providedCommandLine, System.out, serverInterface);
			}
			
		}
		if(command.equals("commands"))
		{
			ICommandsCommandHandler cmdHandler = (ICommandsCommandHandler)availableCommands.get(command);
			if(cmdHandler != null)
			{
				retVal = cmdHandler.handleCommandsCommand(providedCommandLine, System.out, serverInterface);
			}
			
		}
		if(command.equals("comm_params"))
		{
			ICommandParametersCommandHandler cmdHandler = (ICommandParametersCommandHandler)availableCommands.get(command);
			if(cmdHandler != null)
			{
				retVal = cmdHandler.handleCommandParametersCommand(providedCommandLine, System.out, serverInterface);
			}
			
		}
		if(command.equals("client"))
		{
			IClientCommandHandler cmdHandler = (IClientCommandHandler)availableCommands.get(command);
			if(cmdHandler != null)
			{
				retVal = cmdHandler.handleClientCommand(providedCommandLine, System.out, serverInterface);
			}
			
		}
		if(command.equals("loglevel"))
		{
			ILogLevelCommandHandler cmdHandler = (ILogLevelCommandHandler)availableCommands.get(command);
			if(cmdHandler != null)
			{
				retVal = cmdHandler.handleLogLevelCommand(providedCommandLine, System.out, serverInterface);
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
		if(command.equals("start"))
		{
			IServerStartCommandHandler cmdHandler = (IServerStartCommandHandler)availableCommands.get(command);
			if(cmdHandler != null)
			{
				cmdHandler.displayServerStartCommandHelpMessage(System.out);
			}
			
		}
		if(command.equals("commands"))
		{
			ICommandsCommandHandler cmdHandler = (ICommandsCommandHandler)availableCommands.get(command);
			if(cmdHandler != null)
			{
				cmdHandler.displayCommandsCommandHelpMessage(System.out);
			}
			
		}
		if(command.equals("comm_params"))
		{
			ICommandParametersCommandHandler cmdHandler = (ICommandParametersCommandHandler)availableCommands.get(command);
			if(cmdHandler != null)
			{
				cmdHandler.displayCommandParametersCommandHelpMessage(System.out);
			}
			
		}
		if(command.equals("client"))
		{
			IClientCommandHandler cmdHandler = (IClientCommandHandler)availableCommands.get(command);
			if(cmdHandler != null)
			{
				cmdHandler.displayClientCommandHelpMessage(System.out);
			}
			
		}
		
		if(command.equals("loglevel"))
		{
			ILogLevelCommandHandler cmdHandler = (ILogLevelCommandHandler)availableCommands.get(command);
			if(cmdHandler != null)
			{
				cmdHandler.displayLogLevelCommandHelpMessage(System.out);
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
		Logger.initialize("thralld_server started at:"+(new Date()).toString(),Level.SEVERE);
		
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
				
		return true;
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

	//Commands command handler
	@Override
	public boolean handleCommandsCommand(String providedCommandLine,
			PrintStream outputStream,
			IServerStatusInterface targetServerInterface) 
	{
		HashMap<String,String> availCommands = targetServerInterface.getAvailableCommands();
		outputStream.println("Number of available commands:"+Integer.toString(availCommands.size()));
		for(Map.Entry<String, String> entry:availCommands.entrySet())
		{
			outputStream.println("\t"+entry.getKey() +":" + entry.getValue());
		}
		return true;
	}

	@Override
	public void displayCommandsCommandHelpMessage(PrintStream outputStream) 
	{
		outputStream.println("Usage: commands");
		outputStream.println("This command displays the commands supported by the current server.");
		
	}

	//comm_params command handler
	@Override
	public boolean handleCommandParametersCommand(String providedCommandLine,
			PrintStream outputStream,
			IServerStatusInterface targetServerInterface) 
	{
		String[] commandParts = GenericUtilities.splitBySpace(providedCommandLine);
		if(commandParts.length == 2)
		{
			List<String> params = targetServerInterface.getCommandParameters(commandParts[1]);
			if(params == null)
			{
				outputStream.println("Command not supported:" + commandParts[1]);
			}
			else
			{
				if(params.size() == 0)
				{
					outputStream.println("Command has no parameters");
				}
				else
				{
					for(String paramName:params)
					{
						outputStream.println("Parameter:"+paramName);
					}
				}
			}
			return true;
		}
		return false;
	}

	@Override
	public void displayCommandParametersCommandHelpMessage(
			PrintStream outputStream) 
	{
		outputStream.println("Usage: comm_params [commandName]");
		outputStream.println("This command displays parameters supported by the provided command.");
		outputStream.println("\tcommandName : This target command whose parameters need to be displayed.");
		
	}

	//client command handler
	@Override
	public boolean handleClientCommand(String providedCommandLine,
			PrintStream outputStream,
			IServerStatusInterface targetServerInterface) 
	{
		String[] commandParts = GenericUtilities.splitBySpace(providedCommandLine,4);
		boolean retVal = false;
		do
		{
			if(commandParts.length < 3)
			{
				break;
			}
			List<ClientInfo> childConnections = targetServerInterface.getClientInfoByName(commandParts[1]);
			if(childConnections == null || childConnections.size() == 0)
			{
				outputStream.println("No client available with specified name:"+commandParts[1]);
				break;
			}
			String subCommand = commandParts[2];
			if("status".equals(subCommand))
			{
				if(commandParts.length == 4)
				{
					//Get status of provided command
					for(ClientInfo cli:childConnections)
					{
						outputStream.println("For client:" + cli.toString());
						outputStream.println("Command:"+commandParts[3] +" status:"+targetServerInterface.getCommandState(cli, commandParts[3]));
						outputStream.println();
					}
				}
				else
				{
					for(ClientInfo cli:childConnections)
					{
						outputStream.println("For client:" + cli.toString());
						for(Map.Entry<CommandRequestInfo, CommandState> e:targetServerInterface.getCurrentCommandsState(cli).entrySet())
						{
							outputStream.println("Command:"+e.getKey().toString() +" status:"+e.getValue().toString());
						}
						outputStream.println();
					}
				}
				retVal = true;
			}
			else if("run".equals(subCommand) && commandParts.length == 4)
			{
				//Run the provided command.
				String[] subCommandParts = GenericUtilities.splitByChar(commandParts[3], Command.paramSplit);
				Command targetCommand = ReflectionHelper.getCommandByName(subCommandParts[0]);
				if(targetCommand == null)
				{
					if(GenericUtilities.isInteger(subCommandParts[0]))
					{
						targetCommand = ReflectionHelper.getCommandByID(Integer.parseInt(subCommandParts[0]));
					}
					if(targetCommand == null)
					{
						outputStream.println("Unable to find command of the provided id or name:"+subCommandParts[0]);
						break;
					}
				}
				Map<String,String> commParams = Command.splitCommandParams(commandParts[3]);
				CommandRequestInfo targetRequestInfo = ReflectionHelper.getCommandRequestInfo(targetCommand);
				targetRequestInfo.setParameters(commParams);
				for(ClientInfo targetCli:childConnections)
				{
					retVal = targetServerInterface.scheduleClientCommand(targetCli, targetRequestInfo) || retVal;
					if(retVal)
					{
						outputStream.println("Succesfully scheduled command on client:"+targetCli.toString());
					}
					else
					{
						outputStream.println("Error occured while scheduling command on client:"+targetCli.toString());
					}
				}
				
			}
			else if("get_resp".equals(subCommand) && commandParts.length == 4)
			{
				String targetUniqueId = commandParts[3];
				//Get the response of the provided command id
				for(ClientInfo targetCli:childConnections)
				{
					outputStream.println("For client:" + targetCli.toString());
					CommandResponseInfo resp = targetServerInterface.getCommandResponse(targetCli, targetUniqueId);
					if(resp != null)
					{
						outputStream.println(resp.toString());
					}
					else
					{
						outputStream.println("Response not available for the provided command");
					}
				}
				retVal = true;
			}
		} while(false);
		return retVal;
	}

	@Override
	public void displayClientCommandHelpMessage(PrintStream outputStream) 
	{
		outputStream.println("Usage: client [clientName] [status|run|get_resp] [command_scheduled_id|command_parameters]");
		outputStream.println("This command performs requested opreation on the provided client.");
		outputStream.println("\tclientName : Name of the client on which requested operation to be performed.");
		outputStream.println("\tstatus : This sub-command gets the status of the requested command or commands.");
		outputStream.println("\trun : This sub-command runs the provided command on the provided client.");
		outputStream.println("\tget_resp : This sub-command gets the response of the command provided by unique id.");
		outputStream.println("\tcommand_unique_id : Unique ID of the target command.");
		outputStream.println("\tcommand_parameters : Command parameters of the command that need to be run.");
		outputStream.println("\t parameters must be specified as <commandName|commandid>;<key1>=<value1>[;<key2>=<value2>..] etc");
		
	}

	//loglevel command handler
	@Override
	public boolean handleLogLevelCommand(String providedCommandLine,
			PrintStream outputStream,
			IServerStatusInterface targetServerInterface) 
	{
		boolean retVal = false;
		String[] commParts = GenericUtilities.splitBySpace(providedCommandLine);
		if(commParts != null && commParts.length == 2)
		{
			String toSetLev = commParts[1];
			if(toSetLev.equals("v") || toSetLev.equals("verbose"))
			{
				targetServerInterface.setVerboseLogLevel();
				retVal = true;
			}
			else if(toSetLev.equals("n") || toSetLev.equals("normal"))
			{
				targetServerInterface.setNormalLogLevel();
				retVal = true;
			}
		}
		return retVal;
	}

	@Override
	public void displayLogLevelCommandHelpMessage(PrintStream outputStream) 
	{
		outputStream.println("Usage: loglevel [v[erbose]|n[ormal]]");
		outputStream.println("This command sets the log level of the server.");
		outputStream.println("\tv[erbose] : Set log level to verbose (all log entries will be displayed).");
		outputStream.println("\tn[ormal] : Set log level to normal (only severe log entries will be displayed).");
	}

}
