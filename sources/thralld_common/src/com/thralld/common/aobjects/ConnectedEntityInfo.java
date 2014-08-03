/**
 * 
 */
package com.thralld.common.aobjects;

import java.util.HashMap;

/**
 * This object represents an entity that the current system (client or server) is connected to.
 * @author m4kh1ry
 *
 */
public abstract class ConnectedEntityInfo 
{
	protected String entityName = "";
	public HashMap<String,String> propertiesMap = new HashMap<String, String>();
	private static final String LOCAL_END_POINT_PROPERTY = "local_end_point";
	private static final String REMOTE_END_POINT_PROPERTY = "remote_end_point";
	
	/***
	 * This method sets local end point of this connected entity.
	 * @param endPoint String representation of end point
	 */
	public void setLocalEndPoint(String endPoint)
	{
		if(endPoint != null)
		{
			if(propertiesMap.containsKey(LOCAL_END_POINT_PROPERTY))
			{
				propertiesMap.remove(LOCAL_END_POINT_PROPERTY);
			}
			propertiesMap.put(LOCAL_END_POINT_PROPERTY, endPoint);
		}
	}
	
	/***
	 * This method sets  remote end point of this connected entity.
	 * @param endPoint String representation of end point
	 */
	public void setRemoteEndPoint(String endPoint)
	{
		if(endPoint != null)
		{
			if(propertiesMap.containsKey(REMOTE_END_POINT_PROPERTY))
			{
				propertiesMap.remove(REMOTE_END_POINT_PROPERTY);
			}
			propertiesMap.put(REMOTE_END_POINT_PROPERTY, endPoint);
		}
	}
	
	/***
	 * This method returns remote end point.
	 * @return String representing remote end point.
	 */
	public String getRemoteEndPoint()
	{
		return propertiesMap.get(REMOTE_END_POINT_PROPERTY);
		
	}
	
	/***
	 * This method returns local end point.
	 * @return String representing local end point.
	 */
	public String getLocalEndPoint()
	{
		return propertiesMap.get(LOCAL_END_POINT_PROPERTY);
		
	}
	@Override
	public String toString()
	{
		String toRet = null;
		toRet = "EntityName:"+entityName +" " + 
				LOCAL_END_POINT_PROPERTY + ":" + getLocalEndPoint() +" " +
				REMOTE_END_POINT_PROPERTY +":" + getRemoteEndPoint();
		return toRet;
	}
	
	@Override
	public int hashCode()
	{
		int retVal = 0;
		retVal = this.entityName.hashCode() ^ this.propertiesMap.hashCode();
		return retVal;
	}
	
	@Override
	public boolean equals(Object o)
	{
		boolean retVal = false;
		if(o != null)
		{
			ConnectedEntityInfo toCompare = (ConnectedEntityInfo)o;
			retVal = this.entityName.equals(toCompare.entityName) && this.propertiesMap.equals(toCompare.propertiesMap);
		}
		return retVal;
	}

}
