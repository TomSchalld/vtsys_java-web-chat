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
public interface ChatProxy extends Remote{
	
    public void sendMessage(String message) throws RemoteException;
    public String getUsername() throws RemoteException;
	ChatServer getServer()throws RemoteException;
	ClientProxy getHandle()throws RemoteException;
}
