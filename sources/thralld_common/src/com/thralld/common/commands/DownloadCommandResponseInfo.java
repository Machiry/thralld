/**
 * 
 */
package com.thralld.common.commands;

import java.util.ArrayList;

import com.thralld.common.aobjects.CommandResponseInfo;

/**
 * This class represents ResponseInfo object of the DownloadCommand.
 * @author m4kh1ry
 *
 */
public class DownloadCommandResponseInfo extends CommandResponseInfo 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6422237562984182592L;
	
	//Refer: https://docs.google.com/document/d/16YvvUXawvcwjnN2xpwCf30zaxRJgaqQaTfFRnSRDkiw/edit?usp=sharing 
	//for more details on commands
	public int no_of_files = 0;
	public ArrayList<Integer> result_status = new ArrayList<Integer>();
	public ArrayList<String> result_strings = new ArrayList<String>();
	public ArrayList<Integer> file_sizes = new ArrayList<Integer>();
	public ArrayList<String> client_local_paths = new ArrayList<String>();
	public ArrayList<byte[]> file_contents = new ArrayList<byte[]>();
	
	
	@Override
	public boolean isValid()
	{
		boolean retVal = false;
		if(result_status != null && result_strings != null && file_sizes != null
				&& client_local_paths != null && file_contents != null && this.no_of_files > 0)
		{
			if(result_status.size() == this.no_of_files && this.no_of_files == this.result_strings.size()
					&& this.file_sizes.size() == this.no_of_files && this.client_local_paths.size() == this.no_of_files
					&& this.file_contents.size() == this.no_of_files)
			{
				retVal = true;
				for(int i=0;i<this.file_sizes.size();i++)
				{
					if(this.file_sizes.get(i) != this.file_contents.get(i).length)
					{
						retVal = false;
						break;
					}
				}
				
			}
		}
		return retVal;
	}
	
	/***
	 * This method returns not available response object.
	 * @param uniqueID target unique id of the object to be returned.
	 * @return DownloadCommandResponseInfo object representing not available response.
	 */
	public static DownloadCommandResponseInfo getNotAvailableResponse(String uniqueID)
	{
		DownloadCommandResponseInfo toRet = new DownloadCommandResponseInfo();
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
			retVal += "\tNo of downloaded files:" + Integer.toString(this.no_of_files) + "\n";
			for(int i=0;i<this.no_of_files;i++)
			{
				retVal += "\t\tFile:" + this.client_local_paths.get(i) + "\n";
				if(this.result_status.get(i) == 0)
				{
					retVal += "\t\t\tSucess" +"\n";
					retVal += "\t\t\tSize:" + Integer.toString(this.file_sizes.get(i)) + "\n";
				}
				else
				{
					retVal += "\t\t\t" + this.result_strings.get(i) + "\n";
				}
				
			}
		}
		else
		{
			retVal += "\tInvalid Response";
		}
		return retVal;
	}

}
