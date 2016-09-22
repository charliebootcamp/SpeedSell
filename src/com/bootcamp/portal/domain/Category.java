package com.bootcamp.portal.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity
@Table(name = "categories")
public class Category extends AbstractEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "Id")
	private Long id;

	@Column(name = "name")
	private String name;

	@NotFound(action = NotFoundAction.IGNORE)
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "parentId")
	private Category parentCategory;

	@JsonSerialize(include = JsonSerialize.Inclusion.NON_EMPTY)//Avoiding empty json arrays.objects
    @OneToMany(mappedBy = "parentCategory", fetch = FetchType.EAGER)
	private List<Category> subcategories = new ArrayList<Category>();

	@Column(name = "commission")
	private Long commission;
	

	private static final long serialVersionUID = 5617406495442044911L;

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
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getPid() {
		return this.id;
	}

	public void setPid(Long id) {
		this.id = id;
	}

	public Category getParentCategory() {
		return parentCategory;
	}

	public void setParentCategory(Category parentCategory) {
		this.parentCategory = parentCategory;
	}

	public List<Category> getSubcategories() {
		return subcategories;
	}

	public void setSubcategories(List<Category> subcategories) {
		this.subcategories = subcategories;
	}
	
	public Long getCommission() {
		return commission;
	}
	
	public void setCommission(Long commission) {
		this.commission = commission;
	}
}
