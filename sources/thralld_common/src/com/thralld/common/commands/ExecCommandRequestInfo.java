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
 * This class represents ResponseInfo object of the ExecCommand.
 * @author m4kh1ry
 *
 */
public class ExecCommandRequestInfo extends CommandRequestInfo 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2739444544701425786L;

	public String executableName = "";
	int use_shell = 1;
	public String executableClientPath = "";
	public ArrayList<String> arguments = new ArrayList<String>();
	public ArrayList<String> resultFiles = new ArrayList<String>();
	int isbackgroundProcess = 0;
	/* (non-Javadoc)
	 * @see com.thralld.common.aobjects.CommandRequestInfo#getRequiredParameters()
	 */
	@Override
	public List<String> getRequiredParameters() 
	{
		ArrayList<String> toRet = new ArrayList<String>();
		toRet.add("exec_name: String representing name of the executable to run.");
		toRet.add("[optional:default=0]use_shell: Integer representing whether to use shell(non-zero) or not(zero).");
		toRet.add("exec_path: Absolute path of the executable to run on client.");
		toRet.add("[optional:default=\"\"]args: Arguments(separated by comma) for the excutable to run.");
		toRet.add("[optional:default=\"\"]res_files: Absolute path of the files(separated by comma) on client machine that needs to be downloaded.");
		toRet.add("[optionl:default=0]is_back: Integer representing whether to run the command as background process(non-zero) or not(zero).");
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
			if(toSetParam.containsKey("exec_name"))
			{
				this.executableName = toSetParam.get("exec_name");
				if(toSetParam.containsKey("exec_path"))
				{
					this.executableClientPath = toSetParam.get("exec_path");
					if(toSetParam.containsKey("use_shell") && GenericUtilities.isInteger(toSetParam.get("use_shell")))
					{
						this.use_shell = Integer.parseInt(toSetParam.get("use_shell"));
					}
					if(toSetParam.containsKey("is_back") && GenericUtilities.isInteger(toSetParam.get("is_back")))
					{
						this.isbackgroundProcess = Integer.parseInt(toSetParam.get("is_back"));
					}
					if(toSetParam.containsKey("args"))
					{
						this.arguments.clear();
						this.arguments.addAll(Arrays.asList(toSetParam.get("args").split(",")));
					}
					if(toSetParam.containsKey("res_files"))
					{
						this.resultFiles.clear();
						this.resultFiles.addAll(Arrays.asList(toSetParam.get("res_files").split(",")));
					}
					return true;
				}
			}
		}
		this.arguments.clear();
		this.resultFiles.clear();
		this.isbackgroundProcess = 0;
		this.use_shell = 1;
		return false;
	}
	
	@Override
	public boolean isValid()
	{
		boolean isValid = this.executableName != null && !this.executableName.isEmpty() && this.executableClientPath != null && !this.executableClientPath.isEmpty();
		if(!isValid)
		{
			this.arguments.clear();
			this.resultFiles.clear();
			
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
		return new ExecCommand();
	}

}
