/**
 * 
 */
package com.thralld.server.core;

import com.thralld.common.aobjects.ConnectionSpecification;
import com.thralld.common.aobjects.NetworkConnection;
import com.thralld.common.interfaces.INetworkInterface;
import com.thralld.common.logging.Logger;
import com.thralld.common.tcpnetwork.TCPConnectionSpecification;
import com.thralld.common.tcpnetwork.TCPNetworkImplementation;
import com.thralld.server.interfaces.IServerThreadFeedback;

/**
 * This is the server main thread.
 * This listens on a port, waiting for connections from client , creates a server thread for each connected client.
 * 
 * @author m4kh1ry
 *
 */
public class ServerMainThread extends Thread 
{
	private ConnectionSpecification targetConnectionSpecification = null;
	private IServerThreadFeedback serverMainFeedback = null;
	INetworkInterface targetNetInterface = null;
	NetworkConnection serverConnection = null;
	public boolean isStopped = false;
	
	public ServerMainThread(int portno,IServerThreadFeedback parentServerMain) throws Exception
	{
		String[] parms = new String[]{null,Integer.toString(portno),null,null};
		targetConnectionSpecification = TCPConnectionSpecification.getConnectionSpecification(parms);
		this.serverMainFeedback = parentServerMain;
		targetNetInterface = new TCPNetworkImplementation();
		serverConnection = targetNetInterface.openConnection(targetConnectionSpecification);
		if(serverConnection == null)
		{
			Exception e = new Exception("Unable to open server connection");
			Logger.logException("Error while trying to open a connection with network spec:"+targetConnectionSpecification.toString(), e);
			throw e;
		}
		Logger.logInfo("Successfully opened connection to :" + targetConnectionSpecification.toString());
	}
	
	@Override
	public void run()
	{
		Logger.logInfo("Starting to listen on:"+this.serverConnection.toString());
		while(true)
		{
			NetworkConnection clientConnection = this.targetNetInterface.listen(this.serverConnection);
			if(clientConnection == null)
			{
				Logger.logError("Looks like server connection:"+this.serverConnection.toString()+" is closed.");
				break;
			}
			
			//Start a thread for the client.
			ServerThread clientThread = new ServerThread(clientConnection,targetNetInterface,this.serverMainFeedback);
			Logger.logInfo("Got connection request from:"+clientConnection.getClientInfo().toString());
			Logger.logInfo("Starting new Server thread for:"+clientConnection.getClientInfo().toString());
			clientThread.start();
		}
		this.isStopped = true;
		Logger.logError("Main server thread for :"+this.targetConnectionSpecification.toString()+" has stopped.");
	}
	
	/***
	 * Stop the Main thread.
	 * @return true if successful or false
	 */
	public boolean stopMainThread()
	{
		boolean retVal = false;
		retVal = this.targetNetInterface.closeConnection(this.serverConnection);
		return retVal;
	}
	
	/***
	 * This method returns the connection representing the current server.
	 * 
	 * @return NetworkConnection object representing server connection.
	 */
	public NetworkConnection getServerNetworkConnection()
	{
		return this.serverConnection;
	}

}
