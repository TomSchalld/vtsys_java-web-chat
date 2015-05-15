/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package chatserver_rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Thomas Schalldach & Fabian Brammer Copyright 2015
 */
public interface ClientProxy extends Remote {
	public void recieveMessage(String username, String message)
			throws RemoteException;

	public ChatClient getClient() throws RemoteException;
}
