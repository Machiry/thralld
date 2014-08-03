/**
 * 
 */
package com.thralld.common.aobjects;

import java.util.HashMap;
import java.util.Map;

/**
 * Object that contains the specification of a connection.
 * The data required for each network implementation might vary, that is the reason why we have properties map.
 * properties map is a key value store that contains data required for a connection.
 * 
 * For ex:
 * In case of TCP/IP implemenation this map might conatin:
 * "srcip":127.0.0.1
 * "dstip":127.0.0.1
 * "srcport":80 etc
 * 
 * @author m4kh1ry
 *
 */
public abstract class ConnectionSpecification 
{
	public abstract HashMap<String,Object> getPropertiesMap();
	
	@Override
	public String toString()
	{
		String toRet = null;
		toRet = "ConnectionSpecification:";
		for(Map.Entry<String,Object> e:getPropertiesMap().entrySet())
		{
			toRet += e.getKey() + "=" + (e.getValue()== null? e.getValue().toString():"N/A") + ",";
		}
		return toRet;
	}
	
}
