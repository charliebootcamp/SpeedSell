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

import com.bootcamp.portal.domain.UserActivities;
import com.bootcamp.portal.mgr.UserActivitiesDAO;
import com.bootcamp.portal.mgr.annotation.PermissionRequired;

// ANJULAR JS 
// TODO improve exception handling

@Controller
@PermissionRequired
public class UserActivitiesController {

	@Autowired
	private UserActivitiesDAO UActivitiesManager;
	
	protected static final Logger LOGGER = Logger
			.getLogger(UserActivitiesController.class);

	@RequestMapping(value = "uact/{id}", method = RequestMethod.GET)
	public ResponseEntity<UserActivities> getById(@PathVariable("id") Long id)
			throws Exception {
		UserActivities uActivity = UActivitiesManager.getById(id);
		return new ResponseEntity<UserActivities>(uActivity, HttpStatus.OK);
	}

	@RequestMapping(value = "uact", method = RequestMethod.GET)
	public ResponseEntity<List<UserActivities>> getAllUserActivities() {
		List<UserActivities> list = UActivitiesManager.getAllUserActivities();
		return new ResponseEntity<List<UserActivities>>(list, HttpStatus.OK);
	}

	@RequestMapping(value = "uact/personId/{id}", method = RequestMethod.GET)
	public ResponseEntity<List<UserActivities>> getUserActivitiesByPersonId(@PathVariable("id")Long id) 
			throws Exception {
		List<UserActivities> list = UActivitiesManager.getUserActivitiesByPersonId(id);
		return new ResponseEntity<List<UserActivities>>(list, HttpStatus.OK);
	}

    @RequestMapping(value = "uact", method = RequestMethod.POST)
    public ResponseEntity<Void> addUserActivities(@RequestBody UserActivities uActivity,
            UriComponentsBuilder builder) throws Exception {
        boolean flag = false;
        flag = UActivitiesManager.addUserActivity(uActivity);
        if (!flag) {
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
               
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("uActivity/{id}")
                .buildAndExpand(uActivity.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }
}