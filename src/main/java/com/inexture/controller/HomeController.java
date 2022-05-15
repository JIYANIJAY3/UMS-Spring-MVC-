package com.inexture.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

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

	@RequestMapping("/AdminProfile")
	public String AdminProfile() {
		return "AdminProfile";
	}

	@RequestMapping("/ResetPassword")
	public String ResetPassword() {
		return "ResetPassword";
	}

	@RequestMapping("/AdminHome")
	public String AdminHome(HttpSession session) {
		String role = (String) session.getAttribute("role");

		if (role.equals("Admin")) {
			return "AdminHome";
		} else {
			return "UserHome";
		}
	}

	@ResponseBody
	@RequestMapping(path = "/submitform", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public String submitform(@Valid @RequestParam("userProfile.profiles[]") MultipartFile[] file, UserBean user,
			Model model, BindingResult bindingResult, HttpServletRequest r) {

		log.info(file);

		log.info(user);

		if (bindingResult.hasErrors()) {
			model.addAttribute("user", user);
			return "registration";
		}

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
			session.setAttribute("role", "Admin");
			UserBean user = userImpl.getEmployeeByEmail(email);
			session.setAttribute("User", user);
			return "redirect:AdminHome";
		} else if (role.equals("User")) {

			UserBean user = userImpl.getEmployeeByEmail(email);
			int userId = user.getUserId();
			List<UserProfileBean> userProfile = userImpl.getUserImg(userId);

			session.setAttribute("role", "User");
			session.setAttribute("User", user);
			session.setAttribute("UserProfile", userProfile);

			return "redirect:UserHome";
		} else {
			model.addAttribute("email", email);
			model.addAttribute("password", password);
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

	@RequestMapping("/updateUserController")
	public String updateUserController() {
		return "registration";
	}

	@ResponseBody
	@RequestMapping(path = "/UpdateUserDetails", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public String UpdateUserDetails(@RequestParam("userProfile.profiles[]") MultipartFile[] file, UserBean userBean,
			HttpSession session, BindingResult result) {
		UserBean user = (UserBean) session.getAttribute("User");
		int status = userImpl.updateEmployeeDetails(userBean, file);

		if (status > 0 && !user.getRole().equals("User")) {
			UserBean oldUser = (UserBean) userImpl.getEmployeeByEmail(userBean.getEmail());
			List<UserProfileBean> userProfile = userImpl.getUserImg(userBean.getUserId());
			session.setAttribute("User", oldUser);
			session.setAttribute("UserProfile", userProfile);
			return "Update User";
		} else {
			UserBean oldUser = (UserBean) userImpl.getEmployeeByEmail(userBean.getEmail());
			List<UserProfileBean> userProfile = userImpl.getUserImg(userBean.getUserId());
			session.setAttribute("User", oldUser);
			session.setAttribute("UserProfile", userProfile);
			return "Update";
		}
	}

	@RequestMapping("/logout")
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			log.info("session null");
			return "redirect:/";
		} else {
			session.removeAttribute("User");
			session.invalidate();
			return "redirect:/";
		}
	}

	@ResponseBody
	@RequestMapping("/GetAllUserAddress")
	public String GetAllUserAddress(@RequestParam("userId") int userId) {
		return userImpl.getEmployeeById(userId);
	}

	@ResponseBody
	@RequestMapping("/GetAllUserDetails")
	public String GetAllUserDetails(@RequestParam("email") String email, @RequestParam("userId") int userId,
			HttpSession session) {
		UserBean oldUser = (UserBean) userImpl.getEmployeeByEmail(email);
		List<UserProfileBean> userProfile = userImpl.getUserImg(userId);
		session.setAttribute("User", oldUser);
		session.setAttribute("UserProfile", userProfile);
		return "ok";
	}

	@ResponseBody
	@RequestMapping("/DeleteUserProfile")
	public String DeleteUserProfile(@RequestParam("profileId") int profileId, @RequestParam("userId") int userId,
			HttpSession session) {
		int status = userImpl.deleteImage(profileId);

		if (status > 0) {
			List<UserProfileBean> userProfile = userImpl.getUserImg(userId);
			session.setAttribute("UserProfile", userProfile);
			return "delete";
		} else {
			return "";
		}
	}

	@RequestMapping("/ForgotPassword")
	public String ForgotPassword(@RequestParam("email") String email, @RequestParam("answer") String answer,
			@RequestParam("password") String password, Model model) {

		int userId = userImpl.checkAns(email, answer);

		if (userId > 0) {
			int update = userImpl.updatePassword(userId, password);
			if (update > 0) {
				model.addAttribute("updatePassword", "Update Password Succesfully");
				return "ResetPassword";
			} else {
				return "ResetPassword";
			}
		} else {
			model.addAttribute("error", "somthing went erong");
			return "ResetPassword";
		}
	}

}
