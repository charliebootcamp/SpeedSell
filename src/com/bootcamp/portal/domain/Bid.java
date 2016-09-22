package com.bootcamp.portal.domain;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import com.bootcamp.portal.mgr.dto.BidDto;

@Entity
@Table(name = "bids")
public class Bid extends AbstractEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "Id")
	private Long id;

	@Column(name = "bidDate")
	private Date bidDate;

	@Column(name = "amount")
	private Long amount;

	@Column(name = "lotId")
	private Long lotId;

	@Column(name = "isDeleted")
	private boolean isDeleted;

	@OneToOne
	@JoinColumn(name = "bidderId")
	private Person bidder;

	private static final long serialVersionUID = 5617406495442044911L;

	public Bid() {}

	public Bid(BidDto bidDto, Person p){
		this.amount = bidDto.getAmount();
		this.bidDate = Calendar.getInstance().getTime();
		this.lotId = bidDto.getLotId();
		this.isDeleted = false;
		this.bidder = p;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public Date getBidDate() {
		return bidDate;
	}

	public void setBidDate(Date bidDate) {
		this.bidDate = bidDate;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public Person getBidder() {
		return bidder;
	}

	public void setBidder(Person bidder) {
		this.bidder = bidder;
	}

	public Long getLotId() {
		return lotId;
	}

	public void setLotId(Long lotId) {
		this.lotId = lotId;
	}

	@Override
	public String getName() {
		return "";
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
}
