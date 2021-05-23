package com.example.demo.repository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Word;

@Repository
public class WordDaoImpl implements WordDao {
	
	private final JdbcTemplate jdbcTemplate;
	
	@Autowired
	public WordDaoImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public void insertWord(Word word, String userNo) {
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		String date = LocalDateTime.now().format(dateTimeFormatter);
		jdbcTemplate.update("insert into word(english, japanese, mistakes, date, bool_known, userNo)"
				+ " values(?,?,?,?,?,?)", word.getEnglish(), word.getJapanese(), 0, date, false, userNo);
	}

	@Override
	public List<Word> getWords(String userNo) {
		String sqlToGetWordlist = "select * from word where bool_known = false and userNo = ? order by wordId asc";
		List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sqlToGetWordlist, userNo);
		List<Word> wordList = new ArrayList<>();
		for(Map<String, Object> result : resultList) {
			Word word = new Word((int)result.get("wordId"), (String)result.get("english"), 
					(String)result.get("japanese"), (String)result.get("date"), (boolean)result.get("bool_known"));
			wordList.add(word);
			System.out.println(word);
		}
		return wordList;
	}
	
	@Override
	public List<Word> getKnownWords(String userNo) {
		String sqlToGetWordlist = "select * from word where bool_known = true and userNo = ? order by wordId asc";
		List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sqlToGetWordlist, userNo);
		List<Word> wordList = new ArrayList<>();
		for(Map<String, Object> result : resultList) {
			Word word = new Word((int)result.get("wordId"), (String)result.get("english"), 
					(String)result.get("japanese"), (String)result.get("date"), (boolean)result.get("bool_known"));
			wordList.add(word);
			System.out.println(word);
		}
		return wordList;
	}

	@Override
	public void knowThisWord(int wordId) {
		String sqlToDelete = "update word set bool_known = true where wordId = ?";
		jdbcTemplate.update(sqlToDelete, wordId);
	}

	@Override
	public void forgetThisWord(int wordId) {
		String sqlToDelete = "update word set bool_known = false where wordId = ?";
		jdbcTemplate.update(sqlToDelete, wordId);
	}
}
