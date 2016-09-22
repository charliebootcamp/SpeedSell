package com.bootcamp.portal.mgr.dto;

import com.bootcamp.portal.domain.Category;

public class CategoryDto {
	
	private Long id;
	private String name;
	private Long parentId;
	private Long commission;
	
	public CategoryDto(){}

	public CategoryDto(Category category) {
		this.id = category.getId();
		this.name = category.getName();
		this.parentId = category.getParentCategory().getId();
		this.commission = category.getCommission();
	}

	public Long getCommission() {
		return commission;
	}

	public void setCommission(Long commission) {
		this.commission = commission;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

}
