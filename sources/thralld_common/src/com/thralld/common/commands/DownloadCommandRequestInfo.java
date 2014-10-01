/**
 * 
 */
package com.thralld.common.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.thralld.common.aobjects.Command;
import com.thralld.common.aobjects.CommandRequestInfo;
import com.thralld.common.utilities.GenericUtilities;

/**
 * @author m4kh1ry
 *
 */
public class DownloadCommandRequestInfo extends CommandRequestInfo 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6760505838747243666L;
	
	public int no_of_files = 0;
	public ArrayList<String> toDownloadFiles = new ArrayList<String>();
	public ArrayList<Integer> maxFileSizes = new ArrayList<Integer>();

	/* (non-Javadoc)
	 * @see com.thralld.common.aobjects.CommandRequestInfo#getRequiredParameters()
	 */
	@Override
	public List<String> getRequiredParameters() 
	{
		ArrayList<String> toRet = new ArrayList<String>();
		toRet.add("num_files: Integer representing number of files.");
		toRet.add("files: Absolute path of the files(separated by comma) on client machine that needs to be downloaded.");
		toRet.add("[optional] max_files_sizes: Maximum sizes(separaed by comma) of the individual files that needs to be downloaded.-1 if unlimited");
		return toRet;
	}

	/* (non-Javadoc)
	 * @see com.thralld.common.aobjects.CommandRequestInfo#setParameters(java.util.Map)
	 */
	@Override
	public boolean setParameters(Map<String, String> toSetParam) 
	{
		if(toSetParam != null && toSetParam.size() > 0)
		{
			String numberOfFiles = toSetParam.get("num_files");
			String absFilePaths = toSetParam.get("files");
			if(numberOfFiles != null && GenericUtilities.isInteger(numberOfFiles) && absFilePaths != null)
			{
				this.no_of_files = Integer.parseInt(numberOfFiles);
				String[] individulalFiles = GenericUtilities.splitByChar(absFilePaths, ",");
				if(individulalFiles != null && individulalFiles.length == this.no_of_files)
				{
					toDownloadFiles.addAll(Arrays.asList(individulalFiles));
					String maxFileSizes = toSetParam.get("max_files_sizes");
					if(maxFileSizes != null && GenericUtilities.splitByChar(maxFileSizes, ",").length == this.no_of_files)
					{
						String[] individualFileSizes = GenericUtilities.splitByChar(maxFileSizes, ",");
						for(int i=0;i<individualFileSizes.length;i++)
						{
							String currSize = individualFileSizes[i];
							if(GenericUtilities.isInteger(currSize))
							{
								this.maxFileSizes.add(Integer.parseInt(currSize));
							}
							else
							{
								this.maxFileSizes.add(-1);
							}
						}
					}
					else
					{
						for(int i=0;i<toDownloadFiles.size();i++)
						{
							this.maxFileSizes.add(-1);
						}
					}
					return true;
				}
			}
		}
		return false;
	}
	
	@Override
	public boolean isValid()
	{
		boolean isValid = this.no_of_files >=0 && toDownloadFiles != null && maxFileSizes != null && toDownloadFiles.size() == maxFileSizes.size() && toDownloadFiles.size() == this.no_of_files;
		if(!isValid)
		{
			//TODO: cleanup
			//Do clean up if required
		}
		return isValid;
	}

	/* (non-Javadoc)
	 * @see com.thralld.common.aobjects.CommandRequestInfo#getTargetCommand()
	 */
	@Override
	public Command getTargetCommand() 
	{
		return new DownloadCommand();
	}

}
