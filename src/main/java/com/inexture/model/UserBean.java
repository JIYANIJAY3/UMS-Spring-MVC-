package com.inexture.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;

@Entity
@Table(name = "user_details")
public class UserBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int UserId;

	@NotBlank
	@Column(name = "FirstName")
	private String firstName;

	@Column(name = "LastName")
	private String lastName;

	@Column(name = "Dob")
	private String dob;

	@Column(name = "Role")
	private String role = "User";

	@Transient
	private String secutiryQuestion;

	@Column(name = "mobaileNo")
	private String mobaileNo;
	private String gender;
	private String answer;
	private String language;

	@Transient
	private String hobby;

	@Column(name = "Email_Address", unique = true)
	private String email;

	private String password;

	@Transient
	private String base64Image;

	@Autowired
	@OneToMany(mappedBy = "userBean", cascade = CascadeType.ALL, fetch = FetchType.EAGER,orphanRemoval = true)
	private List<UserAddressBean> userAddress;

	@Autowired
	@OneToMany(mappedBy = "userBean", cascade = CascadeType.ALL)
	private List<UserProfileBean> userProfile;

	public int getUserId() {
		return UserId;
	}

	public void setUserId(int userId) {
		UserId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getSecutiryQuestion() {
		return secutiryQuestion;
	}

	public void setSecutiryQuestion(String secutiryQuestion) {
		this.secutiryQuestion = secutiryQuestion;
	}

	public String getMobaileNo() {
		return mobaileNo;
	}

	public void setMobaileNo(String mobaileNo) {
		this.mobaileNo = mobaileNo;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getHobby() {
		return hobby;
	}

	public void setHobby(String hobby) {
		this.hobby = hobby;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getBase64Image() {
		return base64Image;
	}

	public void setBase64Image(String base64Image) {
		this.base64Image = base64Image;
	}

	public List<UserAddressBean> getUserAddress() {
		return userAddress;
	}

	public void setUserAddress(List<UserAddressBean> userAddress) {
		this.userAddress = userAddress;
	}

	public List<UserProfileBean> getUserProfile() {
		return userProfile;
	}

	public void setUserProfile(List<UserProfileBean> userProfile) {
		this.userProfile = userProfile;
	}

	@Override
	public String toString() {
		return "UserBean [UserId=" + UserId + ", firstName=" + firstName + ", lastName=" + lastName + ", dob=" + dob
				+ ", role=" + role + ", secutiryQuestion=" + secutiryQuestion + ", mobaileNo=" + mobaileNo + ", gender="
				+ gender + ", answer=" + answer + ", language=" + language + ", hobby=" + hobby + ", email=" + email
				+ ", password=" + password + ", base64Image=" + base64Image + ", userAddress=" + userAddress
				+ ", userProfile=" + userProfile + "]";
	}

}
