package com.example.demo.repository;

import com.example.demo.entity.User;

public interface UserDao {

	int insertUser(User user);
	int deleteUser(User user);
	int updateUserPassword(User user);
	User selectUser(User user);
	User selectUserByNo(User user);
	
}
