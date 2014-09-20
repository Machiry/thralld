/**
 * 
 */
package com.thralld.common.commandhandlers;

import java.io.File;

import com.thralld.common.annotations.CanReturnNull;
import com.thralld.common.aobjects.CommandRequestInfo;
import com.thralld.common.aobjects.CommandResponseInfo;
import com.thralld.common.aobjects.NetworkConnection;
import com.thralld.common.aobjects.ServerCommandHandler;
import com.thralld.common.commands.DownloadCommandRequestInfo;
import com.thralld.common.commands.DownloadCommandResponseInfo;
import com.thralld.common.commands.DownloadCommandScheduleInfo;
import com.thralld.common.logging.Logger;
import com.thralld.common.utilities.FileUtilities;
import com.thralld.common.utilities.NetworkObjectSerializer;

/**
 * @author m4kh1ry
 *
 */
public class DownloadCommandServerHandler extends ServerCommandHandler 
{
	
	//The directory where all the downloaded files will be saved.
	public static String downloadDir = null;

	/* (non-Javadoc)
	 * @see com.thralld.common.aobjects.ServerCommandHandler#processCommand(com.thralld.common.aobjects.NetworkConnection, com.thralld.common.aobjects.CommandRequestInfo)
	 */
	@Override
	@CanReturnNull
	public CommandResponseInfo processCommand(
			NetworkConnection targetNetworkConnection,
			CommandRequestInfo toProcess) 
	{
		DownloadCommandResponseInfo toRet = null;
		if(toProcess != null && targetNetworkConnection !=null)
		{
			do
			{
				if(!(toProcess instanceof DownloadCommandRequestInfo))
				{
					Exception e = new Exception("Improper request info object.");
					Logger.logException("RequestInfo object is not instance of DownloadCommandRequestInfo", e);
				}
				else
				{
					Logger.logInfo("Starting DownloadCommand processing on connection:"+targetNetworkConnection.toString());
					//Command not available return not available response.
					if(!isCommandAvailableAtClient(targetNetworkConnection, toProcess))
					{
						Logger.logError("Command:" +toProcess.toString() +" not available on client:"+targetNetworkConnection.toString());
						toRet = DownloadCommandResponseInfo.getNotAvailableResponse(toProcess.transactionID);
						break;
					}
					
					if(!NetworkObjectSerializer.sendObject(targetNetworkConnection, toProcess))
					{
						Logger.logError("Unable to send DownloadCommandRequestInfo to network connection:"+targetNetworkConnection.toString());
						break;
					}
					Logger.logInfo("DownloadCommandRequestInfo sent on connection:"+targetNetworkConnection.toString());
					DownloadCommandScheduleInfo scheduleInfo = NetworkObjectSerializer.receiveObject(targetNetworkConnection, DownloadCommandScheduleInfo.class);
					if(scheduleInfo == null)
					{
						Logger.logError("Unable to receive DownloadCommandScheduleInfo from network connection:"+targetNetworkConnection.toString());
						break;
					}
					Logger.logInfo("DownloadCommandScheduleInfo received from connection:"+targetNetworkConnection.toString());
					if(scheduleInfo.transactionID != toProcess.transactionID)
					{
						Logger.logError("Incorrect DownloadCommandScheduleInfo object received. Sent:"+toProcess.transactionID +" Received:"+scheduleInfo.transactionID);
					}
					if(scheduleInfo.isValid())
					{
						Logger.logError("Improper DownloadCommandScheduleInfo object received. Sent:"+toProcess.transactionID +" Received:"+scheduleInfo.transactionID);
					}
					toRet = NetworkObjectSerializer.receiveObject(targetNetworkConnection, DownloadCommandResponseInfo.class);
					if(toRet == null)
					{
						Logger.logInfo("Unable to receive DownloadCommandResponseInfo from network connection:"+targetNetworkConnection.toString());
						break;
					}
					Logger.logInfo("DownloadCommandResponseInfo received from connection:"+targetNetworkConnection.toString());
					if(!toRet.isValid())
					{
						Logger.logError("Improper DownloadCommandResponseInfo object received for:"+toProcess.transactionID);
					}
					else
					{
						//IF the response is valid, we save all the downloaded files to the current directory.
						if(downloadDir == null)
						{
							//Save the files in current dir
							for(int i=0;i<toRet.file_contents.size();i++)
							{
								if(toRet.result_status.get(i) == 0)
								{
									//TODO: Fix this, what happens when client is on Windows and Server is on linux
									FileUtilities.saveFile(toRet.file_contents.get(i),(new File(toRet.client_local_paths.get(i))).getName());
								}
							}
						}
						else
						{
							//Save the files relative to download dir
							for(int i=0;i<toRet.file_contents.size();i++)
							{
								if(toRet.result_status.get(i) == 0)
								{
									//TODO: Fix this, what happens when client is on Windows and Server is on linux
									FileUtilities.saveFile(toRet.file_contents.get(i),downloadDir,(new File(toRet.client_local_paths.get(i))).getName());
								}
							}
						}
					}
				}
			} while(false);
		}
		return toRet;
	}

}
