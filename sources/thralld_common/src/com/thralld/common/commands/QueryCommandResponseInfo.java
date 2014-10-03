/**
 * 
 */
package com.thralld.common.commands;

import java.util.ArrayList;
import java.util.List;

import com.thralld.common.aobjects.CommandResponseInfo;

/**
 * This class represents ResponseInfo object of the QueryCommand.
 * @author m4kh1ry
 *
 */
public class QueryCommandResponseInfo extends CommandResponseInfo 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9173785719595618099L;
	
	//Refer: https://docs.google.com/document/d/16YvvUXawvcwjnN2xpwCf30zaxRJgaqQaTfFRnSRDkiw/edit?usp=sharing 
	//for more details on commands
	public int noOfAvailableCommands=0;
	public List<Integer> commandIds = new ArrayList<Integer>();
	public List<String> commandVersions = new ArrayList<String>();
	public List<String> commandNames = new ArrayList<String>();
	
	@Override
	public boolean isValid()
	{
		boolean retVal = true;
		retVal = retVal && (this.commandIds.size() == this.noOfAvailableCommands);
		retVal = retVal && (this.commandNames.size() == this.noOfAvailableCommands);
		retVal = retVal && (this.commandVersions.size() == this.noOfAvailableCommands);
		return retVal;
	}
	
	/***
	 * This method returns not available response object.
	 * @param uniqueID target unique id of the object to be returned.
	 * @return QueryCommandResponseInfo object representing not available response.
	 */
	public static QueryCommandResponseInfo getNotAvailableResponse(String uniqueID)
	{
		QueryCommandResponseInfo toRet = new QueryCommandResponseInfo();
		toRet.transactionID = uniqueID;
		toRet.setNotAvailableReponse();
		return toRet;
	}
	
	@Override
	public String toString()
	{
		String retVal = "";
		retVal = "UniqueID:" + this.transactionID + "\n";
		if(this.isValid())
		{
			retVal += "\tNo of available commands:" + Integer.toString(this.noOfAvailableCommands) + "\n";
			for(int i=0;i<this.noOfAvailableCommands;i++)
			{
				retVal += "\t\t" + this.commandNames.get(i) + "(ID:" + Integer.toString(this.commandIds.get(i)) + "): Version=" + this.commandVersions.get(i) + "\n";
			}
		}
		else
		{
			retVal += "\tInvalid Response";
		}
		return retVal;
	}
}
