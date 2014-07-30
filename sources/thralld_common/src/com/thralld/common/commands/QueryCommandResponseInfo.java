/**
 * 
 */
package com.thralld.common.commands;

import java.util.ArrayList;
import java.util.List;

import com.thralld.common.aobjects.CommandResponseInfo;

/**
 * @author m4kh1ry
 *
 */
public class QueryCommandResponseInfo extends CommandResponseInfo 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9173785719595618099L;
	public int noOfAvailableCommands=0;
	public List<Integer> commandIds = new ArrayList<Integer>();
	public List<String> commandVersions = new ArrayList<String>();
	public List<String> commandNames = new ArrayList<String>();
}
