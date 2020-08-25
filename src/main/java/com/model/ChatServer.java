package com.model;

import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {

	private int port;
	private Set<String> userName = new HashSet<>();
	private Set<UserThread> userThreads = new HashSet<>();

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public Set<String> getUserName() {
		return userName;
	}

	public void setUserName(Set<String> userName) {
		this.userName = userName;
	}

	public Set<UserThread> getUserThreads() {
		return userThreads;
	}

	public void setUserThreads(Set<UserThread> userThreads) {
		this.userThreads = userThreads;
	}

	public ChatServer(int port) {
		super();
		this.port = port;
	}

	public ChatServer() {
		super();
	}

	@Override
	public String toString() {
		return "ChatServer [port=" + port + ", userName=" + userName + ", userThreads=" + userThreads + "]";
	}

	public void execute() {
		try (ServerSocket serverSocket = new ServerSocket(port)) {

			System.out.println("Chat Server is listening on port " + port);

			while (true) {
				Socket socket = serverSocket.accept();
				System.out.println("New user connected");

				UserThread newUser = new UserThread(socket, this);
				userThreads.add(newUser);
				newUser.start();

			}

		} catch (IOException ex) {
			System.out.println("Error in the server: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	public static void main(String[] args) {
		if (args.length < 1) {
			System.out.println("Syntax: java ChatServer <port-number>");
			System.exit(0);
		}

		int port = Integer.parseInt(args[0]);

		ChatServer server = new ChatServer(port);
		server.execute();
	}

}
