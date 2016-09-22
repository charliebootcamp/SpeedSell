package com.bootcamp.portal.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "useractivities")
public class UserActivities extends AbstractEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "Id")
	private Long id;

	@Column(name = "personId")
	private Long personId;

	@Column(name = "dateOf")
	private Date dateOf;

	@Column(name = "info")
	private String info;
	
	@Column(name = "typeId")
	private Long typeId;


	private static final long serialVersionUID = 5617406495442044911L;

	public UserActivities(){}
	
	public UserActivities(Long personId, Date dateOf, String info, Long typeId) {
		this.personId = personId;
		this.dateOf = dateOf;
		this.info = info;
		this.typeId = typeId;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public Long getPersonId() {
		return personId;
	}

	public void setPersonId(Long personId) {
		this.personId = personId;
	}

	public Date getDateOf() {
		return dateOf;
	}

	public void setDateOf(Date dateOf) {
		this.dateOf = dateOf;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}
}
