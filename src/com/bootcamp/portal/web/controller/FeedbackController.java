package com.bootcamp.portal.web.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.UriComponentsBuilder;

import com.bootcamp.portal.domain.Feedback;
import com.bootcamp.portal.domain.Person;
import com.bootcamp.portal.mgr.FeedbackDAO;
import com.bootcamp.portal.mgr.PersonDAO;
import com.bootcamp.portal.mgr.annotation.PermissionRequired;
import com.bootcamp.portal.mgr.dto.FeedbackDto;
import com.bootcamp.portal.mgr.dto.StatDto;

@Controller
@PermissionRequired
public class FeedbackController {

	
	@Autowired
	private FeedbackDAO FeedbackManager;
	
	@Autowired
	private PersonDAO PersonManager;

	protected static final Logger LOGGER = Logger
			.getLogger(FeedbackController.class);
	
	@RequestMapping(value = "feedbackdata/{id}", method = RequestMethod.GET)
	public ResponseEntity<Feedback> getFeedbackById(@PathVariable("id") Long id)
			throws Exception {
		Feedback feedback = FeedbackManager.getFeedbackById(id);
		return new ResponseEntity<Feedback>(feedback, HttpStatus.OK);
	}

	@RequestMapping(value = "feedbackdata", method = RequestMethod.GET)
	public ResponseEntity<List<Feedback>> getAllFeedback() {
		List<Feedback> list = FeedbackManager.getAllFeedback();
		return new ResponseEntity<List<Feedback>>(list, HttpStatus.OK);
	}

	@RequestMapping(value = "feedbackdata", method = RequestMethod.POST)
	public ResponseEntity<Void> addFeedback(@RequestBody FeedbackDto feedback,
			UriComponentsBuilder builder) throws Exception {
		boolean flag = false;
		flag = FeedbackManager.createFeedback(feedback);
		if (!flag) {
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(builder.path("feedbackdata/{id}")
				.buildAndExpand(feedback.getId()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "persons/{personId}/feedbacks", method = RequestMethod.GET)
	public ResponseEntity<List<Feedback>> getFeedbackByUser(
			@PathVariable("personId") Long personId) throws Exception {
		Person p = PersonManager.getById(personId);
		List<Feedback> list = FeedbackManager.getFeedbackByUser(p);
		return new ResponseEntity<List<Feedback>>(list, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "feedbackdata/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteFeedback(@PathVariable("id") Long id) {
		FeedbackManager.deleteFeedback(id);;
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
	
	@RequestMapping(value = "stat/{id}", method = RequestMethod.GET)
	public ResponseEntity<List<StatDto>> getStatByPersonId(@PathVariable("id") Long id) throws Exception {
		List<StatDto> list = FeedbackManager.getStatByPersonId(id);
		return new ResponseEntity<List<StatDto>>(list, HttpStatus.OK);
	}
}
