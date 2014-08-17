/**
 * 
 */
package com.thralld.common.utilities;

/**
 * This class contains generic utility functions required by our project
 * @author m4kh1ry
 *
 */
public class GenericUtilities 
{
	/***
	 * This method merges the provided string array in to single array.
	 * @param args The member strings to be merged.
	 * @return Merged string
	 */
	public static String concactStrings(String[] args)
	{
		String toRet = null;
		if(args != null && args.length > 0)
		{
			toRet = args[0];
		}
		for(int i=0;i<args.length;i++)
		{
			toRet += " " + args[i];
		}
		return toRet;
	}
	
	/***
	 *  This method checks whether the provided string is integer or not.
	 *  
	 * @param s The string that needs to be checked.
	 * @return true/false depending on whether the string is valid integer or not.
	 */
	public static boolean isInteger(String s) 
	{
	    try 
	    { 
	        Integer.parseInt(s); 
	    } 
	    catch(NumberFormatException e) 
	    { 
	        return false; 
	    }
	    return true;
	}
	
	/***
	 * Split the provided string by space and return the parts.
	 * @param originalString The string to be split.
	 * 
	 * @return Parts of the splitted string.
	 */
	public static String[] splitBySpace(String originalString)
	{
		return originalString.split("\\s+");
	}

}
