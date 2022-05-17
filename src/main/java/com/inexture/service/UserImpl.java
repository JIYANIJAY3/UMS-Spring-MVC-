package com.inexture.service;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.apache.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.inexture.dao.UserDaoImpl;
import com.inexture.model.UserAddressBean;
import com.inexture.model.UserBean;
import com.inexture.model.UserProfileBean;

@Service("userImpl")
public class UserImpl implements UserInterface {

	static final Logger log = Logger.getLogger("debugLog");

	@Autowired
	public UserDaoImpl userDaoImpl;

	public String getRole(String email, String password) {
		return this.userDaoImpl.getRole(email, password);
	}

	public String getAllUser() {
		List<UserBean> userList = this.userDaoImpl.getAllEmployee();

		List<UserBean> userlist = new ArrayList<UserBean>();

		for (UserBean ub : userList) {
			UserBean ubean = new UserBean();
			ubean.setUserId(ub.getUserId());
			ubean.setFirstName(ub.getFirstName());
			ubean.setLastName(ub.getLastName());
			ubean.setDob(ub.getDob());
			ubean.setMobaileNo(ub.getMobaileNo());
			ubean.setGender(ub.getGender());
			ubean.setLanguage(ub.getLanguage());
			ubean.setEmail(ub.getEmail());
			userlist.add(ubean);
		}

		// Convert list To->JSON DATA
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		JsonObject json = new JsonObject();

		json.add("data", gson.toJsonTree(userlist));

		log.info(json);
		return json.toString();
	}

	public boolean getEmailIsPresent(String email) {
		return this.userDaoImpl.getEmailIsPresent(email);
	}

	public int saveUser(UserBean user, MultipartFile[] file) {

		List<UserProfileBean> list = new ArrayList<UserProfileBean>();
		for (MultipartFile getfile : file) {

			UserProfileBean bean = new UserProfileBean();
			try {

				byte[] bytefile = getfile.getBytes();

				bean.setProfiles(bytefile);
				bean.setUserBean(user);
				list.add(bean);
			} catch (Exception e) {
				log.info(e);
			}
		}

		String encrptPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
		user.setPassword(encrptPassword);
		user.setUserProfile(list);
		
		for (UserAddressBean useraddress : user.getUserAddress()) {
			useraddress.setUserBean(user);
		}

		return this.userDaoImpl.saveUser(user);
	}

	public UserBean getEmployeeByEmail(String email) {

		return this.userDaoImpl.getEmployeeByEmail(email);
	}

	public List<UserProfileBean> getUserImg(int userid) {

		//up -> userprofile
		List<UserProfileBean> up = this.userDaoImpl.getUserImg(userid);
		List<UserProfileBean> list = new ArrayList<UserProfileBean>();

		for (UserProfileBean user : up) {
			UserProfileBean userProfile = new UserProfileBean();
			userProfile.setBase64Image(Base64.getEncoder().encodeToString(user.getProfiles()));
			userProfile.setImageId(user.getImageId());
			list.add(userProfile);
		}

		return list;
	}

	public int deleteUserById(int UserId) {

		return this.userDaoImpl.deleteUserById(UserId);
	}

	public String getEmployeeById(int UserId) {

		UserBean user = this.userDaoImpl.getEmployeeById(UserId);
		List<UserAddressBean> list = new ArrayList<UserAddressBean>();

		System.out.println(user.getUserId());
		for (UserAddressBean userlist : user.getUserAddress()) {
			UserAddressBean address = new UserAddressBean();
			address.setAddressId(String.valueOf(userlist.getAddressId()));
			address.setAddress(userlist.getAddress());
			address.setCity(userlist.getCity());
			address.setPinCode(userlist.getPinCode());
			address.setState(userlist.getState());
			address.setCountry(userlist.getCountry());
			address.setUserId(userlist.getUserId());
			list.add(address);
		}
		// Convert List Data To -> JSON Data
		Gson gson = new Gson();

		return gson.toJson(list);
	}

	@Override
	public int updateEmployeeDetails(UserBean user, MultipartFile[] file) {

		List<UserProfileBean> list = new ArrayList<UserProfileBean>();

		if (file.length > 0) {
			for (MultipartFile getfile : file) {

				if (getfile.getSize() > 0) {
					UserProfileBean bean = new UserProfileBean();
					try {
						byte[] bytefile = getfile.getBytes();
						bean.setProfiles(bytefile);
						bean.setUserBean(user);
						list.add(bean);
					} catch (Exception e) {
						log.info(e);
					}
				}
			}
		}

		user.getUserAddress().stream().forEach(address -> {
			address.setUserBean(user);
		});
		user.setUserProfile(list);

		return this.userDaoImpl.updateEmployeeDetails(user, file);
	}

	@Override
	public int deleteImage(int imageId) {
		return this.userDaoImpl.deleteImage(imageId);
	}

	@Override
	public int checkAns(String email, String answer) {	
		return this.userDaoImpl.checkAns(email, answer);
	}

	@Override
	public int updatePassword(int UserId, String Password) {

		String password = BCrypt.hashpw(Password, BCrypt.gensalt());
		return this.userDaoImpl.updatePassword(UserId, password);
	}
}
