package com.jee.model.userAdmin;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="jee_user")
public class JeeUser implements Serializable{
	
	public static String userNameFieldValue = "userName";
	public static String emailFieldValue = "email";
	public static String telephoneFieldValue = "phone";
	
	public JeeUser() {
		super();
	}

	public JeeUser(String id, String password, String userName, Integer enabled) {
		this(id, password, userName, enabled, null, null, null, null, null,null);
	}

	public JeeUser(String id, String password, String userName, Integer enabled,
			String firstName, String lastName,
			String middleName, String department,
			String phone, String email) {
		super();
		this.id = id;
		this.password = password;
		this.userName = userName;
		this.enabled = enabled;
		this.firstName = firstName;
		this.lastName = lastName;
		this.middleName = middleName;
		this.department = department;
		this.phone = phone;
		this.email = email;
	}

	private static final long serialVersionUID = -589377241266230015L;

	@Id
	private String id;
	
	@Column(name="password")
	private String password;

	@Column(name="user_name")
	private String userName;
	
	/**
	 * enabled:1
	 * disabled:0
	 */
	@Column(name="enabled")
	private Integer enabled;
	
	@Column(name="first_name")
	private String firstName;
	
	@Column(name="last_name")
	private String lastName;
	
	@Column(name="middle_name")
	private String middleName;
	
	@Column(name="department")
	private String department;
	
	@Column(name="phone")
	private String phone;
	
	@Column(name="email")
	private String email;
	
	public Integer getEnabled() {
		return enabled;
	}

	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
