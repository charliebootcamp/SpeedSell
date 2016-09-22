package com.bootcamp.portal.web;

import java.io.Serializable;
import java.util.Date;


import com.bootcamp.portal.domain.Person;


public class AuthenticatedUser implements Serializable {
	private static final long serialVersionUID = 3249434119940913045L;

	private Long id;
	private String email;
	private Date createDate;	 
	private Date updateDate; 	 
	private Date lastLogin;
	private int typeId;

	public AuthenticatedUser() {
	}

	public AuthenticatedUser(Person user) {
		this.setId(user.getId());
		this.setEmail(user.getEmail());
		this.setCreateDate(user.getCreateDate());
		this.setLastLogin(user.getLastLogin());
		this.setTypeId(user.getTypeId());
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public Date getCreateDate() {
		return createDate;
	}
	
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
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

}