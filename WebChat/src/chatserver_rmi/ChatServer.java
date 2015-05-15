/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package chatserver_rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author Thomas Schalldach & Fabian Brammer Copyright 2015
 */
public interface ChatServer extends Remote {
	
	//public void sendMessage(String username, String message) throws RemoteException;
	public ChatProxy subscribeUser(String username, ClientProxy handle)
			throws RemoteException;
	
	public boolean unsubscribeUser(String username) throws RemoteException;

	public List<ChatProxy> getUser() throws RemoteException;

	public void sendMessage(String message, ChatProxy server) throws RemoteException;

}
