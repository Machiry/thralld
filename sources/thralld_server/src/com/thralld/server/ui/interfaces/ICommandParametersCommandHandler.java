/**
 * 
 */
package com.thralld.server.ui.interfaces;

import java.io.PrintStream;

import com.thralld.server.interfaces.IServerStatusInterface;

/**
 * This interface represents a command handler for comm_params command.
 * @author m4kh1ry
 *
 */
public interface ICommandParametersCommandHandler 
{
	/***
	 * This is the main handler function that handles the command.
	 * 
	 * @param providedCommandLine the entire command line provided.
	 * @param outputStream Output to write to.
	 * @param targetServerInterface Server interface to work with.
	 * @return true if command is well formed else false
	 */
	public abstract boolean handleCommandParametersCommand(String providedCommandLine,PrintStream outputStream,IServerStatusInterface targetServerInterface);
	
	/***
	 * This method displays help pertaining to this command.
	 * 
	 * @param outputStream Output to write to.
	 */
	public abstract void displayCommandParametersCommandHelpMessage(PrintStream outputStream);
}
