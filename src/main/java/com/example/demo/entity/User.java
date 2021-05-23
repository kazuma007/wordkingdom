package com.example.demo.entity;

public class User {
	private int userNo;
	private String userId;
	private String password;
	private String createdDate;
	
	public User(int userNo) {
		this.userNo = userNo;
	}
	
	public User(int userNo, String password) {
		this.userNo = userNo;
		this.password = password;
	}
	
	public User(String userId, String password) {
		this.userId = userId;
		this.password = password;
	}
	
	public User(int userNo, String userId, String password) {
		this.userNo = userNo;
		this.userId = userId;
		this.password = password;
	}
	
	public String getUserId() {
		return userId;
	}
	public int getUserNo() {
		return userNo;
	}
	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	
	
}