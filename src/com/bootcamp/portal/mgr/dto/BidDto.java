package com.bootcamp.portal.mgr.dto;


public class BidDto {
    
    private Long id;
    private Long amount;
    private Long bidderId;
    private Long lotId;
    
    public BidDto(){}
    public BidDto(Long amount, Long bidderId, Long lotId) {
		this.amount = amount;
		this.bidderId = bidderId;
		this.lotId = lotId;
	}
    public BidDto(Long id, Long amount, Long bidderId, Long lotId) {
		this.id = id;
    	this.amount = amount;
		this.bidderId = bidderId;
		this.lotId = lotId;
	}
    
	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Long getBidderId() {
        return bidderId;
    }

    public void setBidderId(Long bidderId) {
        this.bidderId = bidderId;
    }

    public Long getLotId() {
        return lotId;
    }

    public void setLotId(Long lotId) {
        this.lotId = lotId;
    }
}