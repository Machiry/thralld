/**
 * 
 */
package com.thralld.common.logging;

import java.util.logging.Level;

/**
 * This class handles logging.
 * @author m4kh1ry
 *
 */
public class Logger 
{

	private static final String exceptionPrefix = "EXCEPTION:";
	private static java.util.logging.Logger targetLogger  = java.util.logging.Logger.getLogger("DefaultLogger");
	private static boolean isInitialized = false;
	
	/***
	 * This method initializes the logger with the provided logger name
	 * @param loggerName the name of the logger to use.
	 */
	public static synchronized void initialize(String loggerName)
	{
		if(!isInitialized && loggerName != null)
		{
			targetLogger = java.util.logging.Logger.getLogger(loggerName);
			logInfo("Logger initialized");
		}
	}
	
	/***
	 * Log the provided exception
	 * @param className The class name where the exception happened
	 * @param e The exception that needs to be logged.
	 */
	public static void logException(String msg,Exception e)
	{
		targetLogger.log(Level.SEVERE, exceptionPrefix + msg, e);
	}
	
	/***
	 * Log provided error message.
	 * @param msg Error message that needs to be displayed.
	 */
	public static void logError(String msg)
	{
		targetLogger.log(Level.WARNING, msg);
	}
	
	/***
	 * Log provided information.
	 * @param msg info message that needs to be displayed.
	 */
	public static void logInfo(String msg)
	{
		targetLogger.log(Level.INFO, msg);
	}
}
