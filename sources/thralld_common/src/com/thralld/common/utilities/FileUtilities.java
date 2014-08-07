/**
 * 
 */
package com.thralld.common.utilities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import com.thralld.common.logging.Logger;

/**
 * This file contains utilities to perform some operations on the files.
 * 
 * @author m4kh1ry
 *
 */
public class FileUtilities 
{
	/***
	 * This method reads given file and returns the contents as list of lines.
	 * 
	 * @param filePath The target file to read. 
	 * 
	 * @return List of strings in the file.
	 */
	public static List<String> readLines(String filePath)
	{
		ArrayList<String> toRet = new ArrayList<String>();
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(filePath));
			String line = null;
			while ((line = br.readLine()) != null) 
			{
			   line = line.trim();
			   if(!line.isEmpty())
			   {
				   toRet.add(line);
			   }
			}
			br.close();
		}
		catch(Exception e)
		{
			Logger.logException("Problem occured while reading file", e);
		}
		return toRet;
		
	}

}
