package com.model;

import java.io.*;
import java.net.*;
/*import java.util.*;*/

import org.springframework.beans.factory.annotation.Autowired;

import com.service.ChatService;
import com.service.ThreadService;

public class UserThread extends Thread {
	
	@Autowired
	ThreadService tService;
	
	@Autowired
	ChatService cService;

	private Socket socket;
	private ChatServer server;
	private PrintWriter writer;

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public ChatServer getServer() {
		return server;
	}

	public void setServer(ChatServer server) {
		this.server = server;
	}

	public PrintWriter getWriter() {
		return writer;
	}

	public void setWriter(PrintWriter writer) {
		this.writer = writer;
	}

	public UserThread(Socket socket, ChatServer server) {
		this.socket = socket;
		this.server = server;
	}

	public UserThread() {
		super();
	}

	@Override
	public String toString() {
		return "UserThread [socket=" + socket + ", server=" + server + ", writer=" + writer + "]";
	}

	public void run() {
		try {
			InputStream input = socket.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));

			OutputStream output = socket.getOutputStream();
			writer = new PrintWriter(output, true);

			//service.printUsers();
			tService.printUsers();

			String userName = reader.readLine();
			//server.addUserName(userName);
			//add method in chat service
			cService.addUserName(userName);

			String serverMessage = "New user connected: " + userName;
			//server.broadcast(serverMessage, this);
			cService.broadcast(serverMessage, this);

			String clientMessage;

			do {
				clientMessage = reader.readLine();
				serverMessage = "[" + userName + "]: " + clientMessage;
				//server.broadcast(serverMessage, this);
				cService.broadcast(serverMessage, this);

			} while (!clientMessage.equals("bye"));

			//server.removeUser(userName, this);
			cService.removeUser(userName, this);
			socket.close();

			serverMessage = userName + " has quitted.";
			//server.broadcast(serverMessage, this);
			cService.broadcast(serverMessage, this);

		} catch (IOException ex) {
			System.out.println("Error in UserThread: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

}
