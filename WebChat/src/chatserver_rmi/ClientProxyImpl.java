package chatserver_rmi;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.AccessException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class ClientProxyImpl extends UnicastRemoteObject implements ClientProxy, Serializable {
	
	ChatClient client;
	public ClientProxyImpl(ChatClient client)  throws RemoteException {
		// TODO Auto-generated constructor stub
		this.client = client;
	}
	
	
	@Override
	public ChatClient getClient() {
		return client;
	}

	@Override
	public void recieveMessage(String username, String message)
			throws RemoteException {
		// TODO Auto-generated method stub
		this.client.recieveMessage(username, message);
	}
	
	

}
