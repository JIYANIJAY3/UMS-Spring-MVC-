package com.inexture.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;

@Entity
@Table(name = "user_address_details")
public class UserAddressBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Transient
	private int userId;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int addressId;

	private String country;
	private String state;
	
	@NotBlank(message = "not empty")
	private String city;
	private String pinCode;
	private String address;

	@Transient
	private List<UserAddressBean> list;

	@Autowired
	@ManyToOne
	private UserBean userBean;

	public UserBean getUserBean() {
		return userBean;
	}

	public void setUserBean(UserBean userBean) {
		this.userBean = userBean;
	}

	public int getAddressId() {
		return addressId;
	}

	public void setAddressId(String addressId) {

		if (addressId.isEmpty()) {
			this.addressId = 0;
		} else {
			this.addressId = Integer.parseInt(addressId);
		}
	}

	public List<UserAddressBean> getList() {
		return list;
	}

	public void setList(List<UserAddressBean> list) {
		this.list = list;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPinCode() {
		return pinCode;
	}

	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}
