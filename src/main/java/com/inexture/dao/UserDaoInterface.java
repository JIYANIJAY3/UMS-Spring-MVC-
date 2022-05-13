package com.inexture.dao;

import java.util.List;

import com.inexture.model.UserBean;
import com.inexture.model.UserProfileBean;

public interface UserDaoInterface {

	String getRole(String email, String password);

	List<UserBean> getAllEmployee();

	boolean getEmailIsPresent(String email);

	int saveUser(UserBean user);

	UserBean getEmployeeByEmail(String email);

	List<UserProfileBean> getUserImg(int userid);

	int deleteUserById(int UserId);
	
	UserBean getEmployeeById(int UserId);
}
