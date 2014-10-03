/**
 * 
 */
package com.thralld.common.commands;

import com.thralld.common.aobjects.CommandScheduleInfo;

/**
 * This class represents ScheduleInfo object of the ExecCommand.
 * @author m4kh1ry
 *
 */
public class ExecCommandScheduleInfo extends CommandScheduleInfo 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5992590205614420587L;
	
	public int processID = -1;
	public int currentCPUInfo = -1;

}
