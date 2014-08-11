/**
 * 
 */
package com.thralld.server.ui.interfaces;

import java.io.PrintStream;

import com.thralld.server.interfaces.IServerStatusInterface;

/**
 * @author m4kh1ry
 *
 */
public interface IServerStatusCommandHandler 
{
	public abstract boolean handleServerStatusCommand(String providedCommandLine,PrintStream outputStream,IServerStatusInterface targetServerInterface);
	public abstract void displayServerStatusCommandHelpMessage(PrintStream outputStream);

}
