/**
 * 
 */
package com.thralld.common.utilities;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author m4kh1ry
 *
 */
public class PropertyParser 
{
	/***
	 * 
	 * @param propertyFilePath
	 * @param defaultValue
	 * @return
	 */
	public static String readProperty(String propertyFilePath,String propertyName,String defaultValue)
	{
		String retVal = defaultValue;
		try
		{
			InputStream is = new FileInputStream(propertyFilePath);
	        Properties prop = new Properties();
	        prop.load(is);
	        if(prop.getProperty(propertyName) != null)
	        {
	        	retVal = prop.getProperty(propertyName);
	        }
		}
		catch(Exception e)
		{
			
		}
		return retVal;
	}
}
