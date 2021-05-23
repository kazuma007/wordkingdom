package com.example.demo.app.word;

import java.util.Objects;

import javax.servlet.http.HttpSession;


import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;

@Controller
@RequestMapping("/")
public class UserController {
	
	private final UserService userService;

	@Autowired
	HttpSession session;
	
	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@PostMapping("/registerUser")
	public String resisterUser(@Validated NewUserForm newUserForm,
			BindingResult br, Model model, RedirectAttributes ra) {
		if(br.hasErrors() || !newUserForm.getPassword().equals(newUserForm.getPasswordToConfirm())) {
			model.addAttribute("msg_register_error", "確認用パスワードと異なります。");
			return "newUser";
		}
		String hashedCode = BCrypt.hashpw(newUserForm.getPassword(), BCrypt.gensalt());
		User user = new User(newUserForm.getUserId(), hashedCode);
		try {
			User userFromDBUser = userService.selectUser(user);
		} catch (EmptyResultDataAccessException e) {
			// 取得できない場合EmptyResultDataAccessExceptionがthrowされる
			userService.insertUser(user);
			model.addAttribute("msg_login_error", "ご登録ありがとうございます。こちらからログインしてご利用ください。");
			return "login";
		}
		model.addAttribute("msg_register_error", "このユーザーIDはすでに使われています。");
		return "newUser";
	}
	
	@GetMapping("/registerUser")
	public String registerUser() {
		return "newUser";
	}
	
	@GetMapping("/newUserPage")
	public String goToNewUser(Model model) {
		model.addAttribute("newUserForm",new NewUserForm());
		return "newUser";
	}
	
	@PostMapping("/login")
	public String login(@Validated NewUserForm newUserForm,
			BindingResult br, Model model, RedirectAttributes ra) {

		User user = new User(newUserForm.getUserId(), newUserForm.getPassword());
		
		User registeredUser;
		try {
			registeredUser = userService.selectUser(user);
		} catch (EmptyResultDataAccessException e) {
			// 取得できない場合EmptyResultDataAccessExceptionがthrowされる
			model.addAttribute("msg_login_error", "ユーザーIDまたはパスワードが異なります。");
			return "login";
		}
			
		if(BCrypt.checkpw(newUserForm.getPassword(), registeredUser.getPassword())) {
			session.setAttribute("userNo", registeredUser.getUserNo()); 
			return "index";
		}
		
		model.addAttribute("msg_login_error", "ユーザーIDまたはパスワードが異なります。");
		return "login";
	}
	
	@GetMapping("/samplelogin")
	public String sampleLogin(Model model) {

		User user = new User("HelloWorld57", "");
		
		User registeredUser;
		try {
			registeredUser = userService.selectUser(user);
		} catch (EmptyResultDataAccessException e) {
			// 取得できない場合EmptyResultDataAccessExceptionがthrowされる
			model.addAttribute("msg_login_error", "ユーザーIDまたはパスワードが異なります。");
			return "login";
		}
			
		session.setAttribute("userNo", registeredUser.getUserNo()); 
		return "index";
	}
	
	@GetMapping("/userInfoPage")
	public String goToLoginPage(Model model) {
		Integer userNo = (Integer) session.getAttribute("userNo");
		if(Objects.isNull(userNo)) {
			return "login";
		}
		return "userInfo";
	}
	
	@PostMapping("/editPassword")
	public String editPassword(@Validated EditPasswordForm editPasswordForm,
			BindingResult br, Model model, RedirectAttributes ra) {
		Integer userNo = (Integer) session.getAttribute("userNo");
		if(Objects.isNull(userNo)) {
			return "login";
		}
		
		if(!editPasswordForm.getNewPassword().equals(editPasswordForm.getNewPasswordConfirm())) {
			model.addAttribute("msg_password_error", "確認用パスワードと異なります。");
			return "userInfo";
		}
		User user =  new User(userNo);
		User userFromDBUser = userService.selectUserByNo(user);
		if (!BCrypt.checkpw(editPasswordForm.getCurrentPassword(), userFromDBUser.getPassword())) {
			model.addAttribute("msg_password_error", "登録されているパスワードと異なります。");
			return "userInfo";
		}
		String hashedCode = BCrypt.hashpw(editPasswordForm.getNewPassword(), BCrypt.gensalt());
		User userToInsert = new User(userNo, hashedCode);
		int result = userService.updateUserPassword(userToInsert);
		if (result == 1) {
			model.addAttribute("msg_password_error", "パスワードの変更に成功しました。");
		}
		return "userInfo";
	}
	
	@GetMapping("/editPassword")
	public String editPassword() {
		Integer userNo = (Integer) session.getAttribute("userNo");
		if(Objects.isNull(userNo)) {
			return "login";
		}
		return "userInfo";
	}
	
	
	@GetMapping("/deleteUser")
	public String deleteUser(@Validated EditPasswordForm editPasswordForm,
			BindingResult br, Model model, RedirectAttributes ra) {
		Integer userNo = (Integer) session.getAttribute("userNo");
		if(Objects.isNull(userNo)) {
			return "login";
		}
		User user =  new User(userNo);
		int result = userService.deleteUser(user);
		return "login";
	}
}
