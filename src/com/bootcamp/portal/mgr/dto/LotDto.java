package com.bootcamp.portal.mgr.dto;


import java.util.Calendar;
import java.util.Date;



import com.bootcamp.portal.domain.Lot;

public class LotDto {
	
	private Long id;
	private String name;
	private String info;
	private Long categoryId;
	private Long stateId;
	private Long buyPrice;
	private Boolean commission;
	private Long startPrice;
	private Date startDate;
	private Long duration;
	private Long redemption;
	private Long ownerId;
	private Long bidId;
	private String img;
	private Boolean premium;
	
	public LotDto(){}

	public LotDto(Lot lot) {
		this.id = lot.getId();
		this.name = lot.getName();
		this.info = lot.getInfo();
		this.categoryId = lot.getSubcategory().getId();
		this.stateId = lot.getState().getId();
		this.buyPrice = lot.getBuyPrice();
		this.commission = lot.isPaidCommission();
		this.startPrice = lot.getStartPrice();
		this.startDate = lot.getStartDate();
		this.duration = lot.getDuration();
		this.redemption = lot.getRedemption();
		this.ownerId = lot.getOwner().getId();
		if (lot.getBestBid() != null){
			this.bidId = lot.getBestBid().getId();
		}
		this.img = lot.getImg();
		this.premium = lot.getPremium();
	}

	public LotDto(Long id, Long stateId){
		this.id = id;
		this.stateId = stateId;
		this.startDate = Calendar.getInstance().getTime();
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

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public Long getStateId() {
		return stateId;
	}

	public void setStateId(Long stateId) {
		this.stateId = stateId;
	}

	public Long getBuyPrice() {
		return buyPrice;
	}

	public void setBuyPrice(Long buyPrice) {
		this.buyPrice = buyPrice;
	}

	
	public Boolean isCommission() {
		return commission;
	}

	public void setCommission(Boolean commission) {
		this.commission = commission;
	}

	public Long getStartPrice() {
		return startPrice;
	}

	public void setStartPrice(Long startPrice) {
		this.startPrice = startPrice;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Long getDuration() {
		return duration;
	}

	public void setDuration(Long duration) {
		this.duration = duration;
	}

	public Long getRedemption() {
		return redemption;
	}

	public void setRedemption(Long redemption) {
		this.redemption = redemption;
	}

	public Long getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
	}

	public Long getBidId() {
		return bidId;
	}

	public void setBidId(Long bestBidId) {
		this.bidId = bestBidId;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public Boolean getPremium() {
		return premium;
	}

	public void setPremium(Boolean premium) {
		this.premium = premium;
	}
	
}
