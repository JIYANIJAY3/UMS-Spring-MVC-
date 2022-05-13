package com.inexture.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;
import com.inexture.model.UserBean;
import com.inexture.model.UserProfileBean;

public interface UserInterface {

	String getRole(String email, String password);

	String getAllUser();

	boolean getEmailIsPresent(String email);

	int saveUser(UserBean user, MultipartFile[] file);

	UserBean getEmployeeByEmail(String email);

	List<UserProfileBean> getUserImg(int userid);

	int deleteUserById(int UserId);

	String getEmployeeById(int UserId);
}
