package com.dressapp;

public class User {
	
	private int userId;
	private boolean isConnected;
	private String username;
	
	public User(int userId, boolean isConnected, String username) {
		super();
		this.userId = userId;
		this.isConnected = isConnected;
		this.username = username;
	}
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public boolean isConnected() {
		return isConnected;
	}
	public void setConnected(boolean isConnected) {
		this.isConnected = isConnected;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	

}
