/**
 * 
 */
package com.thralld.common.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.thralld.common.aobjects.Command;
import com.thralld.common.aobjects.CommandRequestInfo;

/**
 * @author m4kh1ry
 *
 */
public class QueryCommandRequestInfo extends CommandRequestInfo 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1220416226630246344L;
	//No members

	@Override
	public List<String> getRequiredParameters() 
	{
		//No parameters
		return new ArrayList<String>();
	}

	@Override
	public boolean setParameters(Map<String, Object> toSetParam) 
	{
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Command getTargetCommand() 
	{
		return new QueryCommand();
	}

}
