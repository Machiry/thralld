/**
 * 
 */
package com.thralld.common.aobjects;



/**
 * This class represents the general structure of  server side command handler.
 * @author m4kh1ry
 *
 */
public abstract class ServerCommandHandler 
{
	/***
	 * This method handles the underlying command on server side
	 * It receives  a requestinfo and returns responseinfo.
	 * 
	 * @param targetNetworkConnection Network connection which needs to be used for communication.
	 * @param toProcess Request info for the command
	 * @return Received response info object or null (if an error occurs)
	 */
	public abstract CommandResponseInfo processCommand(NetworkConnection targetNetworkConnection,CommandRequestInfo toProcess);

}
