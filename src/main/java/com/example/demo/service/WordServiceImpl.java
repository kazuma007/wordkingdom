package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Word;
import com.example.demo.repository.WordDao;

@Service
public class WordServiceImpl implements WordService {
	
	private final WordDao wordDao;
	
	@Autowired
	public WordServiceImpl(WordDao wordDao) {
		this.wordDao = wordDao;
	}

	@Override
	public void insertWord(Word word, String userNo) {
		wordDao.insertWord(word, userNo);

	}

	@Override
	public List<Word> getWords(String userNo) {
		List<Word> wordList = wordDao.getWords(userNo);
		return wordList;
	}

	@Override
	public void knowThisWord(int wordId) {
		wordDao.knowThisWord(wordId);
	}

	@Override
	public List<Word> getKnownWords(String userNo) {
		List<Word> knownWordList = wordDao.getKnownWords(userNo);
		return knownWordList;
	}

	@Override
	public void forgetThisWord(int wordId) {
		wordDao.forgetThisWord(wordId);
	}

}
