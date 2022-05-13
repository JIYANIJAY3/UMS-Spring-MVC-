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

		List<UserBean> ul = new ArrayList<UserBean>();

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
			ul.add(ubean);
		}

		// Convert list To->JSON DATA
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		JsonObject json = new JsonObject();

		json.add("data", gson.toJsonTree(ul));

		log.info(json);
		return json.toString();
	}

	public boolean getEmailIsPresent(String email) {
		return this.userDaoImpl.getEmailIsPresent(email);
	}

	public int saveUser(UserBean user, MultipartFile[] file) {

		List<UserProfileBean> list = new ArrayList<UserProfileBean>();
		for (MultipartFile f : file) {

			UserProfileBean bean = new UserProfileBean();
			try {

				byte[] b = f.getBytes();

				bean.setProfiles(b);
				bean.setUserBean(user);
				list.add(bean);
			} catch (Exception e) {
				log.info(e);
			}
		}

		String encrptPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
		user.setPassword(encrptPassword);
		user.setUserProfile(list);
		for (UserAddressBean ua : user.getUserAddress()) {
			ua.setUserBean(user);
		}

		return this.userDaoImpl.saveUser(user);
	}

	public UserBean getEmployeeByEmail(String email) {

		return this.userDaoImpl.getEmployeeByEmail(email);
	}

	public List<UserProfileBean> getUserImg(int userid) {

		List<UserProfileBean> up = this.userDaoImpl.getUserImg(userid);
		List<UserProfileBean> list = new ArrayList<UserProfileBean>();

		for (UserProfileBean u : up) {
			UserProfileBean userProfile = new UserProfileBean();
			userProfile.setBase64Image(Base64.getEncoder().encodeToString(u.getProfiles()));
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
		for (UserAddressBean l : user.getUserAddress()) {
			UserAddressBean address = new UserAddressBean();
			address.setAddressId(l.getAddressId());
			address.setAddress(l.getAddress());
			address.setCity(l.getCity());
			address.setPinCode(l.getPinCode());
			address.setState(l.getState());
			address.setCountry(l.getCountry());
			address.setUserId(l.getUserId());
			list.add(address);
		}
		// Convert List Data To -> JSON Data
		Gson gson = new Gson();
		
		return gson.toJson(list);
	}
}
