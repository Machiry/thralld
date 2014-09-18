/**
 * 
 */
package com.thralld.common.utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;

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
	
	/***
	 * This method checks whether the file represented by the provided full paths exists or not.
	 * 
	 * @param fileFullPath The path of the file that needs to be checked.
	 * 
	 * @return true/false depending on whether file exists or not.
	 */
	public static boolean isFileExists(String fileFullPath)
	{
		boolean retVal = false;
		if(fileFullPath != null)
		{
			File tempF = new File(fileFullPath);
			retVal = tempF.exists();
		}
		return retVal;
	}
	
	/***
	 * This method returns the size of the file provided by its name
	 * 
	 * @param fileName full path of the file to get the size
	 * @return target file size.
	 */
	public static int getFileSize(String fileName)
	{
		int retFileSize = 0;
		if(fileName != null)
		{
			try
			{
				FileInputStream fis = new FileInputStream(fileName);
				retFileSize = (int)fis.getChannel().size();
				fis.close();
			}
			catch(Exception e)
			{
				
			}
			
		}
		return retFileSize;
	}
	
	/***
	 * This method returns the absolute path of the provided file path.
	 * 
	 * @param fileRelPath Source path of the file
	 * @return Absolute path of the provided file.
	 */
	public static String getFileFullPath(String fileRelPath)
	{
		String toRet = null;
		if(fileRelPath != null)
		{
			File f = new File(fileRelPath);
			toRet = f.getAbsolutePath();
		}
		return toRet;
	}
	
	/***
	 * This method reads the provided file and returns its contents in byte array.
	 * 
	 * @param filePath Path of the file to be read
	 * @return byte array containing file contents
	 */
	public static byte[] getFileContents(String filePath)
	{
		byte[] toRet = {};
		try
		{
			FileInputStream fis = new FileInputStream(new File(filePath));
			toRet = IOUtils.toByteArray(fis);
			fis.close();
		}
		catch(Exception e)
		{
			//Ignore.
		}
		return toRet;
	}
	
	/***
	 * 
	 * This method read the provided file and returns the contents whose maximum size is decidec by the caller.
	 * 
	 * @param filePath The path of the file to be read.
	 * @param maxSize Maximum size of the byte array to be returned.
	 * @return byte array containing the file contents.
	 */
	public static byte[] getFileContents(String filePath,int maxSize)
	{
		byte[] toRet = {};
		try
		{
			FileInputStream fis = new FileInputStream(new File(filePath));
			toRet = IOUtils.toByteArray(fis);
			if(toRet.length > maxSize)
			{
				byte[] src = toRet;
				toRet = new byte[maxSize];
				System.arraycopy(src, 0, toRet, 0, maxSize);
			}
			fis.close();
		}
		catch(Exception e)
		{
			//Ignore.
		}
		return toRet;
	}

}
