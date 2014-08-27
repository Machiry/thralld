/**
 * 
 */
package com.thralld.common.utilities;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.thralld.common.annotations.CanReturnNull;
import com.thralld.common.annotations.ThralldCommandVersion;
import com.thralld.common.aobjects.Command;
import com.thralld.common.aobjects.CommandRequestInfo;
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
	 * This method return Command object for a given command name.
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
	
	/***
	 * This method return Command object for a given command id.
	 * @param commandId target command name
	 * @return Command object of the target Command or null  if command is not found.
	 */
	@CanReturnNull
	public static Command getCommandByID(int commandId)
	{
		Command toRet = null;
		ArrayList<Class<?>> availableCommands = (ArrayList<Class<?>>) ReflectionHelper.getCommandClasses();
		for(Class<?> comm:availableCommands)
		{
			try
			{
				Command currComm = (Command)comm.newInstance();
				if(currComm.getCommandID() == commandId)
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
	
	/***
	 * This method return version of the provided command object.
	 * 
	 * @param comm target command.
	 * @return Version string of the command
	 */
	public static String getCommandVersion(Object comm)
	{
		Annotation[] availableAnno = comm.getClass().getAnnotations();
		for(Annotation annon:availableAnno)
		{
			if(annon instanceof ThralldCommandVersion)
			{
				return ((ThralldCommandVersion)annon).value();
			}
		}
		return "N/A";
	}
	
	/***
	 * This method returns CommandRequestInfo object of the provided command.
	 * 
	 * @param targetCommand The command whose request info object needs to be created.
	 * @return Created command request info object.
	 */
	public static CommandRequestInfo getCommandRequestInfo(Command targetCommand)
	{
		CommandRequestInfo toRet = null;
		if(targetCommand != null)
		{
			try
			{
				toRet = (CommandRequestInfo)targetCommand.getCommandRequestInfoType().newInstance();
				toRet.transactionID = UUID.randomUUID().toString();
			}
			catch(Exception e)
			{
				Logger.logException("Problem occured while trying to create requestinfo object for command:" + targetCommand.toString(), e);
			}
		}
		return toRet;
	}
}
