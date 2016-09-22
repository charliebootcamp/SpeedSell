package com.bootcamp.portal.mgr.dto;

import java.util.ArrayList;
import java.util.List;

public class SearchLotDto {
	
	private Long id;
	private String name;
	//private Long categoryId;
	private List<Long> categoryIds = new ArrayList<Long>();
	private Long priceFrom;
	private Long priceTo;
	private byte isRedemption;
	
	

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

	public List<Long> getCategoryIds() {
		return categoryIds;
	}

	public void setCategoryIds(List<Long> categoryIds) {
		this.categoryIds = categoryIds;
	}

	public Long getPriceFrom() {
		return priceFrom;
	}

	public void setPriceFrom(Long priceFrom) {
		this.priceFrom = priceFrom;
	}

	public Long getPriceTo() {
		return priceTo;
	}

	public void setPriceTo(Long priceTo) {
		this.priceTo = priceTo;
	}


//	public Long getCategoryId() {
//		return categoryId;
//	}
//
//	public void setCategoryId(Long categoryId) {
//		this.categoryId = categoryId;
//	}

	public byte getIsRedemption() {
		return isRedemption;
	}

	public void setIsRedemption(byte isRedemption) {
		this.isRedemption = isRedemption;
	}

}
