package com.bootcamp.portal.domain;

public enum LotStates{
	CREATED (1L),
	APPROVED (2L),
	STARTED (3L),
	FINISHED (4L),
	SOLD (5L),
	PAID (6L);

	private final Long state;
	
	LotStates(Long state){
		this.state = state;
	}
	public Long getId(){
		return state;
	}
}