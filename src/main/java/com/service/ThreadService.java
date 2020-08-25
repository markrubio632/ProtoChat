package com.service;

import java.io.*;
/*import java.net.*;
import java.util.*;*/

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*import com.model.*;*/

@Service
public class ThreadService {
	/*
	 * private Socket socket; private ChatServer server;
	 */
	private PrintWriter writer;

	@Autowired
	ChatService chatService;

	/**
	 * Sends a list of online users to the newly connected user.
	 */
	public void printUsers() {
		if (chatService.hasUsers()) {
			writer.println("Connected users: " + chatService.getUserNames());
		} else {
			writer.println("No other users connected");
		}
	}

	/**
	 * Sends a message to the client.
	 */
	public void sendMessage(String message) {
		writer.println(message);
	}

}
