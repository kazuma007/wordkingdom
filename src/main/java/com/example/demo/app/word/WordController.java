package com.example.demo.app.word;

import java.util.ArrayList;


import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Word;
import com.example.demo.service.WordService;

@Controller
@RequestMapping("/")
public class WordController {
	
	private final WordService wordService;
	
	@Autowired
	HttpSession session;
	
	@Autowired
	public WordController(WordService wordService) {
		this.wordService = wordService;
	}
	
	@GetMapping("/loginPage")
	public String goToLoginPage(Model model) {
		return "login";
	}
	
	@GetMapping("/addition")
	public String add(Model model) {
		Integer userNo = (Integer) session.getAttribute("userNo");
		if(Objects.isNull(userNo)) {
			return "login";
		}
		return "index";
	}
	
	@GetMapping("/learning")
	public String learn(Model model) {
		Integer userNo = (Integer) session.getAttribute("userNo");
		if(Objects.isNull(userNo)) {
			return "login";
		}
		List<Word> wordList = wordService.getWords(String.valueOf(userNo));
		if (CollectionUtils.isEmpty(wordList)) {
			model.addAttribute("emptyFlag", true);
			return "learn";
		}
		Collections.shuffle(wordList);
		int wordCnt = 0;
		session.setAttribute("wordList", wordList);
		session.setAttribute("wordCnt", wordCnt);
		model.addAttribute("newWordEng", wordList.get(0).getEnglish());
		model.addAttribute("newWordJpn", wordList.get(0).getJapanese());
		model.addAttribute("newWordId", wordList.get(0).getWordId());
		return "learn";
	}
	
	@ModelAttribute
	AddNewWord setupForm() {
	    return new AddNewWord();
	}

	@GetMapping
	public String index(Model model) {
		Integer userNo = (Integer) session.getAttribute("userNo");
		if(Objects.isNull(userNo)) {
			return "login";
		}
		return "index";
	}
	
	@GetMapping("/wordlist")
	public String wordlist(Model model) {
		Integer userNo = (Integer) session.getAttribute("userNo");
		if(Objects.isNull(userNo)) {
			return "login";
		}
		List<Word> wordList = wordService.getWords(String.valueOf(userNo));
		model.addAttribute("wordList", wordList);
		model.addAttribute("isKnownList", false);
		return "wordlist";
	}
	
	@GetMapping("/knownWordlist")
	public String knownWordlist(Model model) {
		Integer userNo = (Integer) session.getAttribute("userNo");
		if(Objects.isNull(userNo)) {
			return "login";
		}
		List<Word> knownWordList = wordService.getKnownWords(String.valueOf(userNo));
		model.addAttribute("wordList", knownWordList);
		model.addAttribute("isKnownList", true);
		return "wordlist";
	}
	
	@GetMapping("/knowThisWord")
	public String knowThisWord(@RequestParam int wordId,
			Model model, 
			RedirectAttributes ra) {
		Integer userNo = (Integer) session.getAttribute("userNo");
		if(Objects.isNull(userNo)) {
			return "login";
		}
		wordService.knowThisWord(wordId);
		return "redirect:/wordlist";
	}
	
	@GetMapping("/forgetThisWord")
	public String forgetThisWord(@RequestParam int wordId,
			Model model, 
			RedirectAttributes ra) {
		Integer userNo = (Integer) session.getAttribute("userNo");
		if(Objects.isNull(userNo)) {
			return "login";
		}
		wordService.forgetThisWord(wordId);
		return "redirect:/knownWordlist";
	}
	
	@PostMapping("/addNewWord")
	public String addNewWord(@Validated AddNewWord addNewWord,
			BindingResult br, Model model, RedirectAttributes ra) {
		Integer userNo = (Integer) session.getAttribute("userNo");
		if(Objects.isNull(userNo)) {
			return "login";
		}
		Word word = new Word(addNewWord.getEnglish(), addNewWord.getJapanese());
		wordService.insertWord(word, String.valueOf(userNo));
		model.addAttribute("title", "Added new word (" + word.getEnglish() + "/" + word.getJapanese() + ")");
		return "index";
	}
	
	@GetMapping("/addNewWord")
	public String AddNewWord() {
		Integer userNo = (Integer) session.getAttribute("userNo");
		if(Objects.isNull(userNo)) {
			return "login";
		}
		return "index";
	}
	
	@GetMapping("/nextWord")
	public String nextWord(Model model) {
		Integer userNo = (Integer) session.getAttribute("userNo");
		if(Objects.isNull(userNo)) {
			return "login";
		}
		List<Word> wordList = (ArrayList<Word>) session.getAttribute("wordList");
		int wordCnt = (int) session.getAttribute("wordCnt");
		wordCnt += 1;
		if(wordCnt >= wordList.size()) {
			model.addAttribute("finishedMsg", "You've completed one loop! Add some new words.");
			return "index";
		}
		model.addAttribute("newWordEng", wordList.get(wordCnt).getEnglish());
		model.addAttribute("newWordJpn", wordList.get(wordCnt).getJapanese());
		session.setAttribute("wordCnt", wordCnt);
		return "learn";
	}
}
