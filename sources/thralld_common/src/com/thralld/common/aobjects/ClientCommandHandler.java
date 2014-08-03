/**
 * 
 */
package com.thralld.common.aobjects;

/**
 * This class represents the general structure of client side command handler.
 * @author m4kh1ry
 *
 */
public abstract class ClientCommandHandler 
{
	/***
	 * This method handles the underlying command on client side.
	 * It gets request info, process commands and sends response back to server.
	 * 
	 * @param reqInfo The target request to process.
	 * @return true if request processing is successful or false.
	 */
	public abstract boolean processCommand(NetworkConnection targetNetworkConnection,CommandRequestInfo reqInfo);

}
