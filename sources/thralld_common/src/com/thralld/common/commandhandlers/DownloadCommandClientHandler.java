/**
 * 
 */
package com.thralld.common.commandhandlers;

import com.thralld.common.aobjects.ClientCommandHandler;
import com.thralld.common.aobjects.CommandRequestInfo;
import com.thralld.common.aobjects.NetworkConnection;
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
public class DownloadCommandClientHandler extends ClientCommandHandler {

	/* (non-Javadoc)
	 * @see com.thralld.common.aobjects.ClientCommandHandler#processCommand(com.thralld.common.aobjects.NetworkConnection, com.thralld.common.aobjects.CommandRequestInfo)
	 */
	@Override
	public boolean processCommand(NetworkConnection targetNetworkConnection,
			CommandRequestInfo toProcess) 
	{
		boolean toRet = false;
		if(toProcess != null && targetNetworkConnection !=null)
		{
			do
			{
				if(!(toProcess instanceof DownloadCommandRequestInfo) || !((DownloadCommandRequestInfo)toProcess).isValid())
				{
					Exception e = new Exception("Improper request info object.");
					Logger.logException("RequestInfo object is not instance of DownloadCommandRequestInfo", e);
				}
				else
				{
					DownloadCommandRequestInfo currentRequest = (DownloadCommandRequestInfo)toProcess;
					Logger.logInfo("Starting Client QueryCommand processing on connection:"+targetNetworkConnection.toString());
					DownloadCommandScheduleInfo scheduleInfo = new DownloadCommandScheduleInfo();
					scheduleInfo.transactionID = currentRequest.transactionID;
					scheduleInfo.no_of_files = currentRequest.no_of_files;
					for(int i=0;i<currentRequest.no_of_files;i++)
					{
						scheduleInfo.file_sizes.add(FileUtilities.getFileSize(currentRequest.toDownloadFiles.get(i)));
					}
					if(!NetworkObjectSerializer.sendObject(targetNetworkConnection, scheduleInfo))
					{
						Logger.logError("Unable to send DownloadCommandScheduleInfo to network connection:"+targetNetworkConnection.toString());
						break;
					}
					Logger.logInfo("DownloadCommandScheduleInfo sent on connection:"+targetNetworkConnection.toString());
					
					//Construct response info object
					DownloadCommandResponseInfo respInfo = new DownloadCommandResponseInfo();
					respInfo.transactionID = currentRequest.transactionID;
					respInfo.no_of_files = currentRequest.no_of_files;
					for(int i=0;i<currentRequest.no_of_files;i++)
					{
						byte[] fileContents = {};
						String currentFilePath = currentRequest.toDownloadFiles.get(i);
						int result_status = 0;
						String resultString = "SUCCESS";
						if(FileUtilities.isFileExists(currentFilePath))
						{
							if(currentRequest.maxFileSizes.get(i) >= 0)
							{
								fileContents = FileUtilities.getFileContents(currentFilePath, currentRequest.maxFileSizes.get(i));
							}
							else
							{
								fileContents = FileUtilities.getFileContents(currentFilePath);
							}
						}
						else
						{
							result_status = -1;
							resultString = "FileNotExists";
						}
						respInfo.client_local_paths.add(FileUtilities.getFileFullPath(currentFilePath));
						respInfo.file_contents.add(fileContents);
						respInfo.result_status.add(result_status);
						respInfo.result_strings.add(resultString);
						respInfo.file_sizes.add(fileContents.length);
					}
					
					
					Logger.logInfo("Trying to send DownloadCommandResponseInfo to connection:"+targetNetworkConnection.toString());
					
					if(!NetworkObjectSerializer.sendObject(targetNetworkConnection, respInfo))
					{
						Logger.logError("Unable to send DownloadCommandResponseInfo to network connection:"+targetNetworkConnection.toString());
						break;
					}
					Logger.logInfo("DownloadCommandResponseInfo sent on connection:"+targetNetworkConnection.toString());
					
					toRet = true;
				}
			} while(false);
		}
		return toRet;
	}

}
