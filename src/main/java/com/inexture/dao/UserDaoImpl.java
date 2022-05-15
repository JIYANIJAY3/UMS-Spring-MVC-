package com.inexture.dao;

import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.inexture.model.UserBean;
import com.inexture.model.UserProfileBean;

@Repository
@Transactional
public class UserDaoImpl implements UserDaoInterface {

	final static Logger log = Logger.getLogger("debugLog");

	@Autowired
	private SessionFactory sessionFactory;

	public String getRole(String email, String password) {
		log.info("User Login");
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		UserBean user = (UserBean) session.createQuery("from UserBean where email=:Email").setParameter("Email", email)
				.uniqueResult();
		if (user == null) {
			tx.commit();
			return null;
		}
		System.out.println(user.getPassword() + " " + user.getEmail());
		if (BCrypt.checkpw(password, user.getPassword()) == true) {
			tx.commit();
			return user.getRole();
		} else {
			tx.commit();
			return " ";
		}
	}

	public List<UserBean> getAllEmployee() {
		Session session = sessionFactory.openSession();
		List<UserBean> userList = session.createQuery("from UserBean where role=:Role").setParameter("Role", "User")
				.getResultList();
		session.close();
		return userList;
	}

	public boolean getEmailIsPresent(String email) {
		Session session = sessionFactory.openSession();
		UserBean user = (UserBean) session.createQuery("from UserBean where email=:Email").setParameter("Email", email)
				.uniqueResult();
		session.close();
		return user != null;
	}

	public int saveUser(UserBean user) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.save(user);
		tx.commit();
		session.close();
		return 1;
	}

	public UserBean getEmployeeByEmail(String email) {
		Session session = sessionFactory.openSession();
		UserBean user = (UserBean) session.createQuery("from UserBean where email=:Email").setParameter("Email", email)
				.uniqueResult();
		session.close();
		return user;
	}

	public List<UserProfileBean> getUserImg(int userid) {
		Session session = sessionFactory.openSession();
		System.out.println("user profile list: ");
		List<UserProfileBean> list = session.createQuery("from UserProfileBean where userBean.UserId=:id")
				.setParameter("id", userid).getResultList();
		System.out.println("user profile list: " + list.size());
		session.close();
		return list;
	}

	public int deleteUserById(int UserId) {
		Session session = sessionFactory.openSession();
		UserBean user = (UserBean) session.get(UserBean.class, UserId);
		Transaction tx = session.beginTransaction();
		session.delete(user);
		tx.commit();
		session.close();
		return 1;
	}

	public UserBean getEmployeeById(int UserId) {
		Session session = sessionFactory.openSession();
		UserBean user = (UserBean) session.createQuery("from UserBean where UserId=:UserId")
				.setParameter("UserId", UserId).uniqueResult();
		session.close();
		return user;
	}

	@Transactional
	@Override
	public int updateEmployeeDetails(UserBean userBean, MultipartFile[] file) {
		Session session = sessionFactory.openSession();
		UserBean user = session.get(UserBean.class, userBean.getUserId());
		Transaction tx = session.beginTransaction();
		userBean.setPassword(user.getPassword());
//		session.clear();
		session.merge(userBean);
		tx.commit();
		session.close();
		return 1;
	}

	@Override
	public int deleteImage(int imageId) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		int delete = session.createQuery("delete from UserProfileBean where ImageId=:id").setParameter("id", imageId)
				.executeUpdate();
		tx.commit();
		session.close();
		return delete;
	}

	@Override
	public int checkAns(String email, String answer) {
		Session session = sessionFactory.openSession();

		UserBean user = (UserBean) session.createQuery("from UserBean where email=:email and answer=:answer")
				.setParameter("email", email).setParameter("answer", answer).uniqueResult();
		session.close();

		if (user == null) {
			return 0;
		}
		return user.getUserId();
	}

	@Override
	public int updatePassword(int UserId, String Password) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		int update = session.createQuery("update UserBean set password=:password where UserId=:UserId")
				.setParameter("password", Password).setParameter("UserId", UserId).executeUpdate();
		tx.commit();
		session.close();
		return update;
	}

}
