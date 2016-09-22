package com.bootcamp.portal.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "feedbacks")
public class Feedback extends AbstractEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "Id")
	private Long id;

	@Column(name = "markId")
	private Long markId;

	@Column(name = "title")
	private String title;

	@Column(name = "feedback")
	private String feedback;

	@Column(name = "submitDate")
	private Date submitDate;

	@OneToOne
	@JoinColumn(name = "senderId")
	private Person sender;

	@OneToOne
	@JoinColumn(name = "recipientId")
	private Person recipient;

	@OneToOne
	@JoinColumn(name = "lotId")
	private Lot lot;

	/**
	 *
	 */
	private static final long serialVersionUID = -7960210363435128282L;



	public Person getSender() {
		return sender;
	}

	public void setSender(Person sender) {
		this.sender = sender;
	}

	public Person getRecipient() {
		return recipient;
	}

	public void setRecipient(Person recipient) {
		this.recipient = recipient;
	}

	public Lot getLot() {
		return lot;
	}

	public void setLot(Lot lot) {
		this.lot = lot;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getMarkId() {
		return markId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

	public Date getSubmitDate() {
		return submitDate;
	}

	public void setSubmitDate(Date submitDate) {
		this.submitDate = submitDate;
	}

	@Override
	public Long getId() {
		return this.id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;

	}

	public void setMarkId(Long markId) {
		this.markId = markId;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

}
