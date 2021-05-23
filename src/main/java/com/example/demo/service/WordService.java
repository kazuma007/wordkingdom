package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Word;

public interface WordService {

	void insertWord(Word word, String userNo);
	List<Word> getWords(String userNo);
	List<Word> getKnownWords(String userNo);
	void knowThisWord(int wordId);
	void forgetThisWord(int wordId);
	
}
