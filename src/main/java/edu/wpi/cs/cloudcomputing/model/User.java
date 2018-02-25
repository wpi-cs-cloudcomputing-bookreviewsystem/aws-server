package edu.wpi.cs.cloudcomputing.model;

import java.util.List;

public class User {
	
	private String username;
	private String email;
	private List<User> friends;
	private List<PrivateMessage> privateMessages;
	
	public User() {}
	
	public User(String username, String email) {
		this.username = username;
		this.email = email;
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public List<User> getFriends() {
		return friends;
	}
	public void setFriends(List<User> friends) {
		this.friends = friends;
	}
	public List<PrivateMessage> getPrivateMessages() {
		return privateMessages;
	}
	public void setPrivateMessages(List<PrivateMessage> privateMessages) {
		this.privateMessages = privateMessages;
	}
	
}
