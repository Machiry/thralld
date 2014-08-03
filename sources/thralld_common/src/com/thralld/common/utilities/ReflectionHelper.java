/**
 * 
 */
package com.thralld.common.utilities;

import java.util.ArrayList;
import java.util.List;

import com.thralld.common.annotations.CanReturnNull;
import com.thralld.common.aobjects.Command;
import com.thralld.common.commands.QueryCommand;
import com.thralld.common.logging.Logger;

/**
 * This  class helps with reflection tasks.
 * @author m4kh1ry
 *
 */
public class ReflectionHelper 
{
	/***
	 * This method gets list of class of all the available commands.
	 * @return List of classes 
	 */
	public static List<Class<?>> getCommandClasses()
	{
		ArrayList<Class<?>> toRet = new ArrayList<Class<?>>();
		toRet.add(QueryCommand.class);
		return toRet;
	}
	
	/***
	 * This method return Command object for a given command.
	 * @param commandName target command name
	 * @return Command object of the target Command or null  if command is not found.
	 */
	@CanReturnNull
	public static Command getCommandByName(String commandName)
	{
		Command toRet = null;
		ArrayList<Class<?>> availableCommands = (ArrayList<Class<?>>) ReflectionHelper.getCommandClasses();
		for(Class<?> comm:availableCommands)
		{
			try
			{
				Command currComm = (Command)comm.newInstance();
				if(currComm.getCommandName().equals(commandName))
				{
					toRet = currComm;
					break;
				}
			}
			catch(Exception e)
			{
				Logger.logException("Problem occured while trying to get command by name", e);
			}
		}
		return toRet;
	}
}
