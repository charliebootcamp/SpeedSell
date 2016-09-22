package com.bootcamp.portal.domain;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "lots")
public class Lot extends AbstractEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "Id")
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "info")
	private String info;
	
	@Column(name = "buyPrice")
	private Long buyPrice;

	@Column(name = "commission")
	private Boolean isPaidCommission;

	@Column(name = "startPrice")
	private Long startPrice;

	@Column(name = "startDate")
	private Date startDate;

	@Column(name = "duration")
	private Long duration;

	@Column(name = "redemption")
	private Long redemption;

	@OneToOne
	@JoinColumn(name = "categoryId")
	private Category subcategory;
	
	@OneToOne
	@JoinColumn(name = "stateId")
	private State state;

	@OneToOne
	@JoinColumn(name = "ownerId")
	private Person owner;
	
	@OneToOne
	@JoinColumn(name = "bidId")
	private Bid bestBid;
	
	@Column(name="img")
	private String img;

	@Column(name = "premium")
	private Boolean premium;
	
	public Boolean getPremium() {
		return premium;
	}

	public void setPremium(Boolean premium) {
		this.premium = premium;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

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

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public Category getSubcategory() {
		return subcategory;
	}

	public void setSubcategory(Category subcategory) {
		this.subcategory = subcategory;
	}

	public Long getBuyPrice() {
		return buyPrice;
	}

	public void setBuyPrice(Long buyPrice) {
		this.buyPrice = buyPrice;
	}


	public Boolean isPaidCommission() {
		return isPaidCommission;
	}

	public void setPaidCommission(Boolean isPaidCommission) {
		this.isPaidCommission = isPaidCommission;
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

	
	public Person getOwner() {
		return owner;
	}

	public void setOwner(Person owner) {
		this.owner = owner;
	}

	public Bid getBestBid() {
		return bestBid;
	}

	public void setBestBid(Bid bestBid) {
		this.bestBid = bestBid;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

}
