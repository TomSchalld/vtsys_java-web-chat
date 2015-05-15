package chatserver_rmi;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ChatProxyImpl extends UnicastRemoteObject implements ChatProxy, Serializable {

	String username;
	ChatServer server;
	ClientProxy handle;
	public ChatProxyImpl(String username, ChatServer server, ClientProxy handle) throws RemoteException {
		this.username = username;
		this.server = server;
		this.handle = handle;
	}

	public void sendMessage(String message) throws RemoteException {
		this.server.sendMessage(message, this);
	}
	@Override
	public String getUsername() {
		return this.username;
	}
	@Override
	public ChatServer getServer() {
		return this.server;
	}
	@Override
	public ClientProxy getHandle() {
		return this.handle;
	}
	
}
