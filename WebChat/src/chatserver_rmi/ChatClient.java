/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package chatserver_rmi;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.AccessException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Thomas Schalldach & Fabian Brammer Copyright 2015
 */
public class ChatClient implements Serializable {
	ChatProxy cp;
	ClientProxy client;
	String username;
	ChatServer server;
	boolean newMessage;
	boolean isWebUser;
	String message;
	HttpSession session;
	public boolean hasNewMessage() {
		return newMessage;
	}

	public void setNewMessage(boolean newMessage) {
		this.newMessage = newMessage;
	}

	public String getMessage() {
		return message;
	}

	public ChatClient() throws RemoteException, MalformedURLException,
			NotBoundException {
		super();
		this.username = IO.readString("Username: ");
		String serverAddress = IO.readString("IP-Adresse des Servers: ");
		this.server = (ChatServer) Naming.lookup("rmi://" + serverAddress
				+ ":1099/ChatServer");
		this.client = new ClientProxyImpl(this);
		System.out.println(this.username + " wird eingeloggt ...");
		this.cp = server.subscribeUser(this.username, client);
		this.isWebUser = false;
		if (cp != null) {
			System.out.println("... done.");
		}
	}

	public ChatClient(String username, HttpSession session) throws RemoteException,
			MalformedURLException, NotBoundException {
		super();
		this.username = username;
		this.server = (ChatServer) Naming
				.lookup("rmi://localhost:1099/ChatServer");
		this.client = new ClientProxyImpl(this);
		System.out.println(this.username + " wird eingeloggt ...");
		this.cp = server.subscribeUser(this.username, client);
		this.newMessage = false;
		this.message = null;
		this.isWebUser = true;
		this.session = session;
		if (cp != null) {
			System.out.println("... done.");
		}
	}

	public void logout() throws RemoteException {
		if (this.server.unsubscribeUser(this.username)) {
			System.out.println(this.username + " erfolgreich ausgeloggt.");
			if(!this.isWebUser){
				System.exit(0);
			}
		} else {
			System.out.println(this.username + " fehler beim ausloggen.");
		}
	}

	public void recieveMessage(String username, String message)
			throws RemoteException {
		// TODO Auto-generated method stub
		if (!this.isWebUser) {
			this.postMessage(username, message);
		} else {
			this.postMessageToWeb(username, message);
		}
	}

	public void postMessage(String username, String message) {
		System.out.println(username + " schreibt:  " + message);
	}

	public void postMessageToWeb(String username, String message) {

		this.message = (username + " schreibt: " + message);
		this.setNewMessage(true);

	}

	public void sendMessage(String message) throws RemoteException {
		this.cp.sendMessage(message);
	}

	public ChatProxy getCp() {
		return cp;
	}

	public ClientProxy getClient() {
		return client;
	}

	public String getUsername() {
		return username;
	}

	public ChatServer getServer() {
		return server;
	}

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) throws MalformedURLException {
		// TODO Auto-generated method stub
		try {
			ChatClient client = new ChatClient();

			while (true) {
				String massage = IO.readString("@" + client.getUsername()
						+ ": ");
				if (massage.equals("exit")) {
					client.logout();
					break;
				}
				client.sendMessage(massage);
			}
		} catch (AccessException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	}

}
