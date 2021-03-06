/**
 * 
 */
package com.thralld.common.tcpnetwork;

import java.util.HashMap;

import com.thralld.common.aobjects.ConnectionSpecification;
import com.thralld.common.utilities.GenericUtilities;

/**
 * @author m4kh1ry
 *
 */
public class TCPConnectionSpecification extends ConnectionSpecification 
{
	
	public static final String SRC_IP_ADDRESS_PROPERTY_NAME = "SRC_IP";
	public static final String DST_IP_ADDRESS_PROPERTY_NAME = "DST_IP";
	public static final String SRC_PORT_NUMBER_PROPERTY ="SRC_PORT";
	public static final String DST_PORT_NUMBER_PROPERTY = "DST_PORT";
	private HashMap<String,Object> targetProperties = new HashMap<String, Object>();

	public static ConnectionSpecification getConnectionSpecification(String[] args) 
	{
		TCPConnectionSpecification toRet = null;
		if(args != null && args.length >= 4)
		{
			toRet = new TCPConnectionSpecification();
			GenericUtilities.insertIntoMap(toRet.targetProperties, SRC_IP_ADDRESS_PROPERTY_NAME, args[0]);
			GenericUtilities.insertIntoMap(toRet.targetProperties, SRC_PORT_NUMBER_PROPERTY, args[1]);
			GenericUtilities.insertIntoMap(toRet.targetProperties, DST_IP_ADDRESS_PROPERTY_NAME, args[2]);
			GenericUtilities.insertIntoMap(toRet.targetProperties, DST_PORT_NUMBER_PROPERTY, args[3]);
		}
		return toRet;
	}

	@Override
	public HashMap<String, Object> getPropertiesMap() 
	{
		return new HashMap<String, Object>(targetProperties);
	}

}
