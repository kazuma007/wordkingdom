package com.example.demo.repository;

import java.util.List;

import com.example.demo.entity.Word;

public interface WordDao {

	void insertWord(Word word, String userNo);
	List<Word> getWords(String userNo);
	void knowThisWord(int wordId);
	void forgetThisWord(int wordId);
	List<Word> getKnownWords(String userNo);
	
}
