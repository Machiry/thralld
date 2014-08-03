/**
 * 
 */
package com.thralld.common.objects;

import java.io.Serializable;

/**
 * This class represents the initial request server sends to client during normal operation.
 * For more details refer Design doc.
 * @author m4kh1ry
 *
 */
public class ClientCommandQuery implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8158405557043018026L;
	public static final String AVAILABLE="Available";
	public static final String NOTAVAILABLE="NotAvailable";
	public int commandId = 0;
	public String commandVersion = "";

}
