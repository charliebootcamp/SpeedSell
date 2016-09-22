package com.bootcamp.portal.domain;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "users")
public class User extends AbstractEntity {
	private static final long serialVersionUID = -5520333334597964008L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(length = 255)
	private String name;

	@Column(length = 255, nullable = false)
	private String email;

	// verified email
	@Column
	private Boolean verified = false;

	@Column(length = 255)
	private String verificationCode;

	@Column(length = 255)
	private String password;

	@Column(nullable = false, insertable = false, updatable = false)
	private Date createDate = Calendar.getInstance().getTime();

	@Column(nullable = false)
	private Date updateDate = Calendar.getInstance().getTime();

	@Column(nullable = false)
	private Date lastLogin = Calendar.getInstance().getTime();

	public User() {
		super();
	}

	public User(Long id) {
		this.id = id;
	}

	@Override
	public Long getId() {
		return this.id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name =" + name + "]";
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getVerified() {
		return verified;
	}

	public void setVerified(Boolean verified) {
		this.verified = verified;
	}

	public String getVerificationCode() {
		return verificationCode;
	}

	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}
}