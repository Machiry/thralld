/**
 * 
 */
package com.thralld.server.interfaces;

import com.thralld.common.objects.ClientInfo;
import com.thralld.server.core.ServerThread;

/**
 * This is the interface which is used to communicate the creation and termination of any client thread.
 * @author m4kh1ry
 *
 */
public interface IServerThreadFeedback 
{
	/***
	 * This method is used to update about creation of server thread to main thread.
	 * 
	 * @param newClientInfo The client info object of the client to be added.
	 * @param newServerThread Target server thread being created.
	 */
	public abstract void updateNewClent(ClientInfo newClientInfo,ServerThread newServerThread);
	
	/***
	 * This method is used to notify about terminations of a server thread.
	 * 
	 * @param toRemoveClientInfo The client info object to be removed.
	 * @param toRemoveServerThread Server thread to be removed.
	 */
	public abstract void notifyTerminatingClient(ClientInfo toRemoveClientInfo,ServerThread toRemoveServerThread);
	
}
