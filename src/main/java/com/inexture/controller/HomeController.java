package com.inexture.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.inexture.model.UserBean;
import com.inexture.model.UserProfileBean;
import com.inexture.service.UserImpl;

@Controller
public class HomeController {

	final static Logger log = Logger.getLogger("debugLog");

	@Autowired
	public UserImpl userImpl;

	@RequestMapping("/index")
	public String home() {
		return "index";
	}

	@RequestMapping("/UserHome")
	public String UserHome() {
		return "UserHome";
	}

	@RequestMapping("/UserProfile")
	public String UserProfile() {
		return "UserProfile";
	}

	@RequestMapping("/registration")
	public String registration() {
		return "registration";
	}

	@RequestMapping("/AdminHome")
	public String AdminHome() {
		return "AdminHome";
	}

	@ResponseBody
	@RequestMapping(path = "/submitform", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public String submitform(@RequestParam("userProfile.profiles[]") MultipartFile[] file, UserBean user, Model model,
			BindingResult bindingResult, HttpServletRequest r) {

		log.info(file);

		log.info(user);
		int status = userImpl.saveUser(user, file);
		if (status > 0) {
			return "Successfully Added...";
		} else {
			return "";
		}

	}

	@RequestMapping("/login")
	public String login(HttpServletRequest request, HttpSession session, Model model) {
		String email = request.getParameter("email");
		String password = request.getParameter("password");

		String role = userImpl.getRole(email, password);

		if (role.equals("Admin")) {
			return "redirect:AdminHome";
		} else if (role.equals("User")) {

			UserBean user = userImpl.getEmployeeByEmail(email);
			int userId = user.getUserId();
			List<UserProfileBean> userProfile = userImpl.getUserImg(userId);

			session.setAttribute("User", user);
			session.setAttribute("UserProfile", userProfile);

			return "redirect:UserHome";
		} else {
			model.addAttribute("errormassage", "Not Match Email And Password");
			return "index";
		}
	}

	@ResponseBody
	@RequestMapping("/getAllUser")
	public String getAllUser() {
		log.info("get all user details");
		return userImpl.getAllUser();
	}

	@ResponseBody
	@RequestMapping("/CheckEmailIsPresent")
	public String CheckEmailIsPresent(HttpServletRequest request) {
		String email = request.getParameter("email");

		boolean status = userImpl.getEmailIsPresent(email);
		if (status) {
			return "done";
		} else {
			return " ";
		}
	}

	@ResponseBody
	@RequestMapping("/DeleteUser")
	public String DeleteUser(@RequestParam("UserId") int userId) {
		int status = userImpl.deleteUserById(userId);

		if (status > 0)
			return "done";
		else
			return "";
	}

	@RequestMapping("/logout")
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			return "redirect:/";
		} else {
			session.removeAttribute("User");
			session.invalidate();
			return "redirect:/";
		}
	}

	@ResponseBody
	@RequestMapping("/GetAllUserAddress")
	public String GetAllUserAddress(@RequestParam("UserId") int userId) {
		return userImpl.getEmployeeById(userId);
	}
}
