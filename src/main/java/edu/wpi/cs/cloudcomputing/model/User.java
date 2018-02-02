package edu.wpi.cs.cloudcomputing.model;

import java.util.List;

public class User {
	
	private String username;
	private String password;
	private String email;
	private List<User> friends;
	private List<PrivateMessage> privateMessages;

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
