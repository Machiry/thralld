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
public interface IClientsCommandHandler 
{
	public abstract boolean handleClientsCommand(String providedCommandLine,PrintStream outputStream,IServerStatusInterface targetServerInterface);
	public abstract void displayClientsCommandHelpMessage(PrintStream outputStream);
}
