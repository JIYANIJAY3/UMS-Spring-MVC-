package com.inexture.dao;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

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
	
	int updateEmployeeDetails(UserBean userBean,MultipartFile[] file);
	
	int deleteImage(int imageId);
	
	int checkAns(String email, String answer);
	
	int updatePassword(int UserId, String Password);
}
