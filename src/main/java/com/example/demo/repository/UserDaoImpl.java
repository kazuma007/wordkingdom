package com.example.demo.repository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.User;
import com.example.demo.entity.Word;

@Repository
public class UserDaoImpl implements UserDao{
	
	private final JdbcTemplate jdbcTemplate;
	
	@Autowired
	public UserDaoImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public int insertUser(User user) {
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		String date = LocalDateTime.now().format(dateTimeFormatter);
		int result = jdbcTemplate.update("insert into user(userId, password, createdDate)"
				+ " values(?,?,?)", user.getUserId(), user.getPassword(), date);
		return result;
	}
	
	@Override
	public User selectUser(User user) throws EmptyResultDataAccessException {
		String sqlToGetWordlist = "select * from user where userId = ?";
		Map<String, Object> result = jdbcTemplate.queryForMap(sqlToGetWordlist, user.getUserId());
		User returnUser = new User(
				(int)result.get("userNo"),
				(String)result.get("userId"),
				(String)result.get("password")
		);
		return returnUser;
	}
	
	@Override
	public User selectUserByNo(User user) {
		String sqlToGetWordlist = "select * from user where userNo = ?";
		Map<String, Object> result = jdbcTemplate.queryForMap(sqlToGetWordlist, user.getUserNo());
		User returnUser = new User(
				(int)result.get("userNo"),
				(String)result.get("userId"),
				(String)result.get("password")
		);
		return returnUser;
	}

	@Override
	public int updateUserPassword(User user) {
		int result = jdbcTemplate.update("update user set password = ? where userNo = ?", user.getPassword(), user.getUserNo());
		return result;
	}

	@Override
	public int deleteUser(User user) {
		int result = jdbcTemplate.update("delete from user where userNo = ?", user.getUserNo());
		return result;
	}
	
	
}
