/**
 * 
 */
package com.thralld.common.tcpnetwork;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.thralld.common.aobjects.NetworkConnection;
import com.thralld.common.logging.Logger;
import com.thralld.common.objects.ClientInfo;
import com.thralld.common.objects.ServerInfo;

/**
 * @author m4kh1ry
 *
 */
public class TCPNetworkConnection extends NetworkConnection 
{
	
	public Socket currrentConnSocket = null;
	public ServerSocket currentServSocket = null;

	
	public TCPNetworkConnection(ServerSocket serverConn,Socket clientConn) 
	{
		this.currentServSocket = serverConn;
		this.currrentConnSocket = clientConn;
	}
	
	/* (non-Javadoc)
	 * @see com.thralld.common.aobjects.NetworkConnection#getInputStream()
	 */
	@Override
	public InputStream getInputStream() 
	{
		if(this.currrentConnSocket != null)
		{
			try
			{
				return this.currrentConnSocket.getInputStream();
			}
			catch(IOException e)
			{
				Logger.logException("Unable to get input stream from socket:" + this.currrentConnSocket.toString(), e);
			}
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.thralld.common.aobjects.NetworkConnection#getOutputStream()
	 */
	@Override
	public OutputStream getOutputStream() 
	{
		if(this.currrentConnSocket != null)
		{
			try
			{
				return this.currrentConnSocket.getOutputStream();
			}
			catch(IOException e)
			{
				Logger.logException("Unable to get output stream from socket:" + this.currrentConnSocket.toString(), e);
			}
		}
		return null;
	}

	@Override
	public ClientInfo getClientInfo() 
	{
		ClientInfo toRet = null;
		if(this.currrentConnSocket != null)
		{
			toRet = new ClientInfo();
			toRet.setClientName(this.currrentConnSocket.getInetAddress().toString());
			toRet.setLocalEndPoint(this.currrentConnSocket.getLocalSocketAddress().toString());
			toRet.setRemoteEndPoint(this.currrentConnSocket.getRemoteSocketAddress().toString());
		}
		return toRet;
	}

	@Override
	public ServerInfo getServerInfo() 
	{
		ServerInfo toRet = null;
		if(this.currrentConnSocket != null)
		{
			toRet = new ServerInfo();
			toRet.setServerName(this.currrentConnSocket.getInetAddress().toString());
			toRet.setLocalEndPoint(this.currrentConnSocket.getLocalSocketAddress().toString());
			toRet.setRemoteEndPoint(this.currrentConnSocket.getRemoteSocketAddress().toString());
		}
		return toRet;
	}
	
	@Override
	public String toString()
	{
		String toRet = null;
		if(this.currrentConnSocket != null)
		{
			toRet = "Client Connection, Local Endpoint:" + this.currrentConnSocket.getLocalSocketAddress().toString();
			toRet += " Remote Endpoint:" + this.currrentConnSocket.getRemoteSocketAddress().toString();
		}
		if(this.currentServSocket != null)
		{
			toRet = "Server Connection, Local Endpoint:" + this.currentServSocket.getLocalSocketAddress().toString();
		}
		return toRet;
	}

}
