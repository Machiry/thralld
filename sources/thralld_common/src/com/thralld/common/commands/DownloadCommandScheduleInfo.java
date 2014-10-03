/**
 * 
 */
package com.thralld.common.commands;

import java.util.ArrayList;

import com.thralld.common.aobjects.CommandScheduleInfo;

/**
 * This class represents ScheduleInfo object of the DownloadCommand.
 * @author m4kh1ry
 *
 */
public class DownloadCommandScheduleInfo extends CommandScheduleInfo 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6558606682495892086L;
	
	public int no_of_files = 0;
	public ArrayList<Integer> file_sizes = new ArrayList<Integer>();
	
	@Override
	public boolean isValid()
	{
		return this.file_sizes != null && this.no_of_files >=0 && this.file_sizes.size() == this.no_of_files;
	}

}
