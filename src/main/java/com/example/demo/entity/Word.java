package com.example.demo.entity;

public class Word {
	private int wordId;
	private String english;
	private String japanese;
	private String date;
	private boolean bool_known;
	public Word(String english, String japanese){
		this.english = english;
		this.japanese = japanese;
	}
	public Word(int wordId, String english, String japanese, String date, boolean bool_known){
		this.wordId = wordId;
		this.english = english;
		this.japanese = japanese;
		this.date = date;
		this.bool_known = bool_known;
	}
	
	public int getWordId() {
		return wordId;
	}
	public void setWordId(int wordId) {
		this.wordId = wordId;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getEnglish() {
		return english;
	}
	public String getJapanese() {
		return japanese;
	}
	public void setEnglish(String english) {
		this.english = english;
	}
	public void setJapanese(String japanese) {
		this.japanese = japanese;
	}
	public boolean isBool_known() {
		return bool_known;
	}
	public void setBool_known(boolean bool_known) {
		this.bool_known = bool_known;
	}
	@Override
	public String toString() {
		return "Word [wordId=" + wordId + ", english=" + english + ", japanese=" + japanese + ", date=" + date
				+ ", bool_known=" + bool_known + "]";
	}


}
