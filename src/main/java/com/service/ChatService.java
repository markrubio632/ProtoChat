package com.service;

/*import java.io.*;
import java.net.*;*/
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.model.UserThread;

@Service
public class ChatService {

	private Set<String> userNames = new HashSet<>();
	private Set<UserThread> userThreads = new HashSet<>();

	@Autowired
	ThreadService threadService;

	/**
	 * Delivers a message from one user to others (broadcasting)
	 */
	public void broadcast(String message, UserThread excludeUser) {
		for (UserThread aUser : userThreads) {
			if (aUser != excludeUser) {
				//aUser.sendMessage(message);
				
				threadService.sendMessage(message);
			}
		}
	}

	/**
	 * Stores username of the newly connected client.
	 */
	public void addUserName(String userName) {
		userNames.add(userName);
	}

	/**
	 * When a client is disconneted, removes the associated username and UserThread
	 */
	public void removeUser(String userName, UserThread aUser) {
		boolean removed = userNames.remove(userName);
		if (removed) {
			userThreads.remove(aUser);
			System.out.println("The user " + userName + " quitted");
		}
	}

	Set<String> getUserNames() {
		return this.userNames;
	}

	/**
	 * Returns true if there are other users connected (not count the currently
	 * connected user)
	 */
	public boolean hasUsers() {
		return !this.userNames.isEmpty();
	}
}