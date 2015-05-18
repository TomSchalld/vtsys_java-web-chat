package web;

import java.io.*;
import java.rmi.NotBoundException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.catalina.SessionEvent;

import chatserver_rmi.ChatClient;

public class HalloWelt extends HttpServlet {
	static List<ChatClient> clients = new LinkedList<ChatClient>();

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/plain;charset=UTF-8");
		// Allocate a output writer to write the response message into the
		// network socket
		
		PrintWriter out = response.getWriter();
		System.out.println(request.getParameter("username"));
		if (request.getParameter("method").equals("subscribe")) {
			try {
				HttpSession session = request.getSession();
				session.setAttribute("username", request.getParameter("username"));
				clients.add(new ChatClient(request.getParameter("username"),session));
				
			} catch (NotBoundException e) {
				e.printStackTrace();
			}
		} else if (request.getParameter("method").equals("unsubscribe")) {
			ChatClient tmp = this.search(request.getParameter("username"));
			if (tmp != null) {
				tmp.logout();
				clients.remove(tmp);
			}
		} else if (request.getParameter("method").equals("SEND")) {
			ChatClient tmp = this.search(request.getParameter("username"));
			if(tmp!=null){
				tmp.sendMessage(request.getParameter("message"));
				out.println(request.getParameter("message"));
			}
		} else if (request.getParameter("method").equals("RECIEVE")) {
			ChatClient tmp = this.search(request.getParameter("username"));
			while(!tmp.hasNewMessage()){
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(tmp.hasNewMessage()){
				out.println(tmp.getMessage());
				tmp.setNewMessage(false);
			}
			
		}

		// Write the response message, in an HTML page
		try {

		} finally {
			out.close(); // Always close the output writer
		}
	}

	public ChatClient search(String username) {
		for (ChatClient cc : clients) {
			if (cc.getUsername().equals(username)) {
				return cc;
			}
		}
		return null;
	}

}
