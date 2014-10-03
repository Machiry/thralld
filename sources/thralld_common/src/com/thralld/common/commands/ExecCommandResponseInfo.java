/**
 * 
 */
package com.thralld.common.commands;

import java.util.HashMap;

import com.thralld.common.aobjects.CommandResponseInfo;

/**
 * This class represents ResponseInfo object of the ExecCommand.
 * @author m4kh1ry
 *
 */
public class ExecCommandResponseInfo extends CommandResponseInfo 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3378459378882918411L;
	//Refer: https://docs.google.com/document/d/16YvvUXawvcwjnN2xpwCf30zaxRJgaqQaTfFRnSRDkiw/edit?usp=sharing 
	//for more details on commands
	public int exitStatus = -1;
	public int stdoutLen = -1;
	public byte[] stdoutData = new byte[]{};
	public int stderrLen = -1;
	public byte[] stdoutErr = new byte[]{};
	public HashMap<String,byte[]> resultFileData = new HashMap<String, byte[]>();
	
	@Override
	public boolean isValid()
	{
		boolean retVal = true;
		retVal = retVal && (this.stdoutLen >= 0) && this.stdoutData.length == this.stdoutLen;
		retVal = retVal && (this.stderrLen >= 0) && this.stdoutErr.length == this.stderrLen;
		return retVal;
	}

	/***
	 * This method returns not available response object.
	 * @param uniqueID target unique id of the object to be returned.
	 * @return ExecCommandResponseInfo object representing not available response.
	 */
	public static ExecCommandResponseInfo getNotAvailableResponse(String uniqueID)
	{
		ExecCommandResponseInfo toRet = new ExecCommandResponseInfo();
		toRet.transactionID = uniqueID;
		toRet.setNotAvailableReponse();
		return toRet;
	}
	
	@Override
	public String toString()
	{
		String retVal = "";
		retVal = "UniqueID:" + this.transactionID + "\n";
		if(isValid())
		{
			retVal += "\tExit Status:" + Integer.toString(this.exitStatus) + "\n";
			retVal += "\t Stdout Data len:" + Integer.toString(this.stdoutLen) + "\n";
			retVal += "\t Stderr Data len:" + Integer.toString(this.stderrLen) + "\n";
			retVal += "\n\tResult Files:" +"\n";
			for(String fileName:this.resultFileData.keySet())
			{
				retVal += "\t " + fileName + ", Size=" + this.resultFileData.get(fileName).length + "\n";
			}
		}
		else
		{
			retVal += "\tInvalid Response";
		}
		return retVal;
	}

}
