package com.bootcamp.portal.mgr.dto;


public class CardDto {
	
	private Long payerId;
	private Long lotId;
    private Long cardNumb;
    private Long cvc;
    private Long expMonth;
    private Long expYear;
    
    public CardDto(){}
    public CardDto(Long payerId, Long lotId,Long cardNumb, Long cvc, Long expMonth, Long expYear){
    	this.payerId = payerId;
    	this.lotId = lotId;
    	this.cardNumb = cardNumb;
    	this.cvc = cvc;
    	this.expMonth = expMonth;
    	this.expYear = expYear;
    }
    public Long getPayerId() {
    	return payerId;
    }
    
    public void setPayerId(Long payerId) {
    	this.payerId = payerId;
    }
    
    public Long getLotId() {
    	return lotId;
    }
    
    public void setLotId(Long lotId) {
    	this.lotId = lotId;
    }
    
	public Long getCardNumb() {
		return cardNumb;
	}
	public void setCardNumb(Long cardNumb) {
		this.cardNumb = cardNumb;
	}
	public Long getCvc() {
		return cvc;
	}
	public void setCvc(Long cvc) {
		this.cvc = cvc;
	}
	public Long getExpMonth() {
		return expMonth;
	}
	public void setExpMonth(Long expMonth) {
		this.expMonth = expMonth;
	}
	public Long getExpYear() {
		return expYear;
	}
	public void setExpYear(Long expYear) {
		this.expYear = expYear;
	}
    
}