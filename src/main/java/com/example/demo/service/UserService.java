package com.example.demo.service;

import com.example.demo.entity.User;

public interface UserService {
	
	int insertUser(User user);
	int updateUserPassword(User user);
	int deleteUser(User user);
	User selectUser(User user);
	User selectUserByNo(User user);
	
}
