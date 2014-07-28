/**
 * 
 */
package com.thralld.common.utilities;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.thralld.common.annotations.CanReturnNull;
import com.thralld.common.aobjects.NetworkConnection;
import com.thralld.common.logging.Logger;

/**
 * This class handles serializing of objects to and from network connection object
 * @author m4kh1ry
 *
 */
public class NetworkObjectSerializer 
{
	
	
	/***
	 * This method receives an object of provided type over the provided connection object
	 * @param targetConn Connection from which object needs to be received
	 * @param type Java type of the object that needs to be received.
	 * @return Object read 
	 * 		or 
	 * 		null of error happens
	 */
	@SuppressWarnings("unchecked")
	@CanReturnNull
	public static <T> T receiveObject(NetworkConnection targetConn,Class<T> type)
	{
		T toRet = null;
		if(targetConn != null)
		{
			try 
			{
				ObjectInputStream objStream = new ObjectInputStream(targetConn.getInputStream());
				toRet = (T)objStream.readObject();
			} 
			catch (IOException e) 
			{
				Logger.logException("Problem occured while trying to receive an object",e);
			} 
			catch (ClassNotFoundException e) 
			{
				Logger.logException("Problem occured while trying to received an object of type:"+type.getCanonicalName(),e);
			}
			
		}
		return toRet;
	}
	
	/***
	 * This method sends the provided object over the connection
	 * @param targetConn Connection over which object needs to be sent
	 * @param toSend the target object that needs to be sent
	 * @return true if sending is successful
	 * 		or
	 * 		false
	 */
	public static boolean sendObject(NetworkConnection targetConn,Object toSend)
	{
		boolean toRet = false;
		if(targetConn != null && toSend != null)
		{
			try
			{
				ObjectOutputStream objStream = new ObjectOutputStream(targetConn.getOutputStream());
				objStream.writeObject(toSend);
				toRet = true;
			}
			catch(IOException e)
			{
				Logger.logException("Problem occured while trying to send the provided object", e);
			}
		}
		return toRet;
	}

}
