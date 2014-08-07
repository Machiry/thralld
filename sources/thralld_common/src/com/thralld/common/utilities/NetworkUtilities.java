/**
 * 
 */
package com.thralld.common.utilities;

import java.net.InetAddress;
import java.net.Socket;
import java.util.List;
import java.util.Random;

import com.thralld.common.logging.Logger;

/**
 * This class contains all the utilities related to netwowrk connection.
 * Ex: Checking for network connectivity etc.
 * @author m4kh1ry
 *
 */
public class NetworkUtilities 
{
	public static boolean isInternetConnectionUp(List<String> urlstoSearchFor)
	{
		boolean retVal = false;
		Random ran= new Random(System.currentTimeMillis());
		try
		{
			String toSearchSite = urlstoSearchFor.get(ran.nextInt(urlstoSearchFor.size()));
			InetAddress inetAddr = InetAddress.getByName(toSearchSite);
			//We assume port 80 i.e HTTP is open.
			//If we are able to connect then we say internet connection is up.
			Socket connection = new Socket(inetAddr, 80);
			connection.close();
			retVal = true;
		}
		catch(Exception e)
		{
			Logger.logException("Problem occured while checking internet connection.", e);
		}
		return retVal;
	}

}
