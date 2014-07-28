package com.thralld.common.tcpnetwork;

import java.net.ServerSocket;
import java.net.Socket;

import com.thralld.common.aobjects.ConnectionSpecification;
import com.thralld.common.aobjects.NetworkConnection;
import com.thralld.common.interfaces.INetworkInterface;
import com.thralld.common.logging.Logger;

public class TCPNetworkImplementation implements INetworkInterface 
{

	@Override
	public NetworkConnection openConnection(ConnectionSpecification targetConnSpec)
	{
		TCPNetworkConnection toRet = null;
		if(targetConnSpec != null)
		{
			try
			{
				Object dstIPAddr = targetConnSpec.getPropertiesMap().get(TCPConnectionSpecification.DST_IP_ADDRESS_PROPERTY_NAME);
				Object srcPortNumber = targetConnSpec.getPropertiesMap().get(TCPConnectionSpecification.SRC_PORT_NUMBER_PROPERTY);
				Object dstPortNumber = targetConnSpec.getPropertiesMap().get(TCPConnectionSpecification.DST_PORT_NUMBER_PROPERTY);
				if(srcPortNumber == null && dstIPAddr == null && dstPortNumber == null)
				{
					Logger.logError("Not enough information to create a TCPNetworkConnection");
				}
				else
				{
					if(dstIPAddr == null && srcPortNumber != null)
					{
						toRet = new TCPNetworkConnection(new ServerSocket(Integer.parseInt((String)srcPortNumber)),null);
					}
					else if(dstIPAddr != null && dstPortNumber != null)
					{
						toRet = new TCPNetworkConnection(null, new Socket((String)dstIPAddr, Integer.parseInt((String)dstPortNumber)));
					}
				}
			}
			catch(Exception e)
			{
				Logger.logException("Problem occurred while trying to open a connection", e);
			}
		}
		return toRet;
	}

	@Override
	public boolean closeConnection(NetworkConnection targetConn) 
	{
		boolean toRet = false;
		TCPNetworkConnection targetTCPConn = (TCPNetworkConnection)targetConn;
		if(targetTCPConn != null)
		{
			try
			{
				if(targetTCPConn.currentServSocket != null)
				{
					targetTCPConn.currentServSocket.close();
				}
				if(targetTCPConn.currrentConnSocket != null)
				{
					targetTCPConn.currrentConnSocket.close();
				}
				toRet = true;
			}
			catch(Exception e)
			{
				Logger.logException("Exception occured while trying to close the connection", e);
			}
		}
		return toRet;
	}

	@Override
	public NetworkConnection listen(NetworkConnection targetConn) 
	{
		TCPNetworkConnection toRet = null;
		TCPNetworkConnection srcConn = (TCPNetworkConnection)targetConn;
		if(srcConn != null && srcConn.currentServSocket != null)
		{
			try
			{
				toRet = new TCPNetworkConnection(null, srcConn.currentServSocket.accept());
			}
			catch(Exception e)
			{
				Logger.logException("Problem occured while trying to listen", e);
			}
		}
		return toRet;
	}

	@Override
	public boolean sendData(NetworkConnection targetConn, byte[] data) 
	{
		boolean toRet = false;
		TCPNetworkConnection conn = (TCPNetworkConnection)targetConn;
		if(conn != null && conn.currrentConnSocket != null)
		{
			try
			{
				conn.currrentConnSocket.getOutputStream().write(data);
				conn.currrentConnSocket.getOutputStream().flush();
				toRet = true;
			}
			catch(Exception e)
			{
				Logger.logException("Problem occured while sending data", e);
			}
		}
		return toRet;
	}

	@Override
	public byte[] receiveData(NetworkConnection targetConn, int targetDataSize) 
	{
		byte[] toRet = null;
		TCPNetworkConnection conn = (TCPNetworkConnection)targetConn;
		if(conn != null && conn.currentServSocket !=null)
		{
			try
			{
				toRet = new byte[targetDataSize];
				if(conn.currrentConnSocket.getInputStream().read(toRet, 0, targetDataSize) != targetDataSize)
				{
					toRet = null;
				}
			}
			catch(Exception e)
			{
				toRet = null;
				Logger.logException("Problem occured while trying to receive data", e);
			}
		}
		return toRet;
	}

}
