/**
 * 
 */
package com.thralld.common.interfaces;

import com.thralld.common.annotations.CanReturnNull;
import com.thralld.common.aobjects.ConnectionSpecification;
import com.thralld.common.aobjects.NetworkConnection;

/**
 * This interface is used for all network communication purposes.
 * Any implementation of this interface should have all these methods in place and perform the 
 * corresponding function;
 * 
 * @author m4kh1ry
 *
 */
public interface INetworkInterface {
	
	/***
	 * This method opens the connection specified by the argument.
	 * 
	 * @param targetConnSpec The connection specification of the target connection.
	 * @return true/false depending on whether the opening is successful or not
	 */
	public abstract boolean openConnection(ConnectionSpecification targetConnSpec);
	
	/***
	 * This method closes the connection specified by the argument.
	 * 
	 * @param targetConnSpec The connection specification which needs to be closed.
	 * @return true/false depending on whether the closing is successful or nor
	 */
	public abstract boolean closeConnection(ConnectionSpecification targetConnSpec);
	
	/***
	 * This method listens for an incoming connection request on the specified connection spec.
	 * 
	 * @param targetConnSpec
	 * @return NetworkConnection object representing the connection 
	 * 		or 
	 * 		null if unsuccessful
	 */
	@CanReturnNull
	public abstract NetworkConnection listen(ConnectionSpecification targetConnSpec);
	
	
	/***
	 *  Send provided data on to the connection specified by the input argument.
	 *   
	 * @param targetConn Target connection on which the data need to be sent.
	 * @param data Data that needs to be sent.
	 * @return True/False depending on whether the data is successfully sent or not.
	 */
	public abstract boolean sendData(NetworkConnection targetConn,byte[] data);
	
	
	/***
	 * Received data from provided connection.
	 * 
	 * @param targetConn Target connection from which the data need to be received.
	 * @param targetDataSize The size of data that is being expected to receive.
	 * @return Data that is being received.
	 */
	@CanReturnNull
	public abstract byte[] receiveData(NetworkConnection targetConn, int targetDataSize);

}
