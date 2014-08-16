/**
 * 
 */
package com.thralld.common.utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.thralld.common.logging.Logger;

/**
 * This class contains all the utilities related to netwowrk connection. Ex:
 * Checking for network connectivity etc.
 * 
 * @author m4kh1ry
 *
 */
@SuppressWarnings("deprecation")
public class NetworkUtilities 
{
	public static boolean isInternetConnectionUp(List<String> urlstoSearchFor) 
	{
		boolean retVal = false;
		Random ran = new Random(System.currentTimeMillis());
		try 
		{
			String toSearchSite = urlstoSearchFor.get(ran
					.nextInt(urlstoSearchFor.size()));
			InetAddress inetAddr = InetAddress.getByName(toSearchSite);
			// We assume port 80 i.e HTTP is open.
			// If we are able to connect then we say internet connection is up.
			Socket connection = new Socket(inetAddr, 80);
			connection.close();
			retVal = true;
		} 
		catch (Exception e) 
		{
			Logger.logException(
					"Problem occured while checking internet connection.", e);
		}
		return retVal;
	}

	/***
	 * This method is used to read web page as lines of the provided url.
	 * @param url The target url that needs to be read.
	 * @return List of strings representing contents of the provided webpage.
	 */
	public static List<String> readWebPage(String url) 
	{
		ArrayList<String> toRet = new ArrayList<String>();
		@SuppressWarnings("resource")
		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(url);
		HttpResponse response = null;
		do 
		{
			try 
			{
				response = client.execute(request);
			} 
			catch (Exception e) 
			{
				Logger.logException(
						"Problem occured while trying to get response from http request",
						e);
				break;
			}
			InputStream in = null;
			try 
			{
				in = response.getEntity().getContent();
			} 
			catch (Exception e) 
			{
				Logger.logException(
						"Problem occured while trying to get contents from http response",
						e);
				break;
			}
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(in));
			String line = null;
			try 
			{
				while ((line = reader.readLine()) != null) 
				{
					toRet.add(line.trim());
				}
			} 
			catch (IOException e) 
			{
				Logger.logException(
						"Problem occured while trying to read line from http response",
						e);
				break;
			}
			try 
			{
				in.close();
			} 
			catch (IOException e) 
			{
				Logger.logException(
						"Problem occured while trying to close the http contents stream",
						e);
				break;
			}
		} while (false);
		return toRet;
	}

}
