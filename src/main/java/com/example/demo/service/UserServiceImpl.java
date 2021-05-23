package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserDao;

@Service
public class UserServiceImpl implements UserService{

	private final UserDao userDao;
	
	@Autowired
	public UserServiceImpl(UserDao userDao) {
		this.userDao = userDao;
	}
	
	@Override
	public int insertUser(User user) {
		return userDao.insertUser(user);

	}

	@Override
	public User selectUser(User user) {
		return userDao.selectUser(user);
	}
	
	@Override
	public User selectUserByNo(User user) {
		return userDao.selectUserByNo(user);
	}

	@Override
	public int updateUserPassword(User user) {
		return userDao.updateUserPassword(user);
	}

	@Override
	public int deleteUser(User user) {
		return userDao.deleteUser(user);
	}

}
