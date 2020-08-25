package com.controller;

import java.net.*;

import org.springframework.stereotype.Controller;

import java.io.*;

@Controller
public class ClientController {

	private String hostname;
	private int port;
	private String userName;

	public ClientController(String hostname, int port) {
		this.hostname = hostname;
		this.port = port;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void execute() {
		try {
			Socket socket = new Socket(hostname, port);

			System.out.println("Connected to the chat server");

			new ReadThreadController(socket, this).start();
			// new WriteThreadController(socket, this).start();
			new WriteThreadController(socket, this).start();

		} catch (UnknownHostException ex) {
			System.out.println("Server not found: " + ex.getMessage());
		} catch (IOException ex) {
			System.out.println("I/O Error: " + ex.getMessage());
		}

	}

	public static void main(String[] args) {
		if (args.length < 2)
			return;

		String hostname = args[0];
		int port = Integer.parseInt(args[1]);

		ClientController client = new ClientController(hostname, port);
		client.execute();
	}

}
