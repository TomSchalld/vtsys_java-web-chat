package chatserver_rmi;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ChatServerImpl extends UnicastRemoteObject implements ChatServer,
		Serializable {

	List<ChatProxy> user = new ArrayList<ChatProxy>();

	// ChatProxyImpl cProxy;

	public ChatServerImpl() throws RemoteException {
		super();
	}

	@Override
	public ChatProxy subscribeUser(String username, ClientProxy handle)
			throws RemoteException {
		ChatProxy cp = new ChatProxyImpl(username, this, handle);
		if (user.add(cp)) {
			System.out.println(username + ": subscribed");
		}
		return cp;
	}

	@Override
	public boolean unsubscribeUser(String username) throws RemoteException {
		for (ChatProxy cp : this.user) {
			if (cp.getUsername().equals(username)) {
				this.user.remove(cp);
				System.out.println(username + ": unsubscribed");
				return true;
			}
		}
		return false;
	}
	@Override
	public void sendMessage(String message, ChatProxy proxy)
			throws RemoteException {
		
		for (ChatProxy cp : this.user) {
			try {
			cp.getHandle().recieveMessage(proxy.getUsername(), message);
			} catch(RemoteException ex) {
		        System.out.println("unabled to contact client "+cp.getUsername());
		        System.out.println("removing.");
		        this.unsubscribeUser(cp.getUsername());
		        
		      } 
		}
	    
		
	}
	@Override
	public List<ChatProxy> getUser() {
		return user;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Registry registry = null;
		try {
			registry = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
		} catch (RemoteException rex) {
			System.err.println("A wild RemoteException appeard: " + rex);
		}
		if (registry == null) {
			System.err.println("Cannot find registry");
			System.exit(0);
		}

		try {
			registry.rebind("ChatServer", new ChatServerImpl());
			System.out.println("ChatServerImpl registered as 'ChatServer' ...");
		} catch (java.rmi.ConnectException cex) {
			System.err
					.println("ConnectException while accessing registry (port = "
							+ Registry.REGISTRY_PORT + ")");
			System.err.println("Run 'rmiregistry " + Registry.REGISTRY_PORT
					+ "'");
			System.exit(0);
		} catch (Exception ex) {
			System.err
					.println("Exception during server registration (registry port = "
							+ Registry.REGISTRY_PORT + ")");
			ex.printStackTrace();
			System.exit(0);
		}

	}


}
