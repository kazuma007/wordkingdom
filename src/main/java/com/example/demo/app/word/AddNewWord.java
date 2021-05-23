package com.example.demo.app.word;

import javax.validation.constraints.NotNull;

public class AddNewWord {
	@NotNull
	private String english;
	@NotNull
	private String japanese;
	
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
}
