package com.thralld.common.tcpnetwork;

import com.thralld.common.aobjects.ConnectionSpecification;
import com.thralld.common.aobjects.NetworkConnection;
import com.thralld.common.interfaces.INetworkInterface;

public class TCPNetworkImplementation implements INetworkInterface {

	@Override
	public boolean openConnection(ConnectionSpecification targetConnSpec) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean closeConnection(ConnectionSpecification targetConnSpec) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public NetworkConnection listen(ConnectionSpecification targetConnSpec) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean sendData(NetworkConnection targetConn, byte[] data) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public byte[] receiveData(NetworkConnection targetConn, int targetDataSize) {
		// TODO Auto-generated method stub
		return null;
	}

}
