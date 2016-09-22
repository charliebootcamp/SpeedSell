package com.bootcamp.portal.domain;

// ANGULAR JS

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "persons")
public class Person extends AbstractEntity {
	private static final long serialVersionUID = 5520333334597964001L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "Id")
	private Long id;

	@Column(name = "username")
	private String name;//means username

	@Column(name = "email")
	private String email;

	@Column(name = "phone")
	private String phone;
	
	@Column(name = "isActive")
	private boolean isActive;	
		
	@Column(name = "homeAddress")
	private String homeAddress;
	
	@Column(name = "fullName")
	private String fullName;
	
	@Column(name="typeId")
	private int typeId;
	
	@Column
	private Boolean verified = false;

	@Column(length = 255)
	private String verificationCode;
	  
	@Column(length = 255)
	private String passwordHash;  	

	@Column(nullable = false, insertable = false, updatable = false)
	private Date createDate = Calendar.getInstance().getTime(); 

	@Column(nullable = false)
	private Date updateDate = Calendar.getInstance().getTime(); 
	  
	@Column(nullable = false)
	private Date lastLogin = Calendar.getInstance().getTime();
	
	@Column(length = 255)
	private String passwordChangeCode;
	
	public Person() {
		super();
	}

	public Person(Long id) {
		this.id = id;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
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

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String password) {
		this.passwordHash = password;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
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

	public String getPasswordChangeCode() {
		return passwordChangeCode;
	}

	public void setPasswordChangeCode(String passwordChangeCode) {
		this.passwordChangeCode = passwordChangeCode;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public String getHomeAddress() {
		return homeAddress;
	}

	public void setHomeAddress(String homeAddress) {
		this.homeAddress = homeAddress;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}	

	@Override
	public Long getId() {
		return this.id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	// because of serialization problem
	public Long getPid() {
		return this.id;
	}

	public void setPid(Long id) {
		this.id = id;
	}

	@Override
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

}