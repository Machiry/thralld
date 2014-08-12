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

}
