package com.bootcamp.portal.web.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.bootcamp.portal.domain.Lot;
import com.bootcamp.portal.mgr.AccountDAO;
import com.bootcamp.portal.mgr.PersonDAO;
import com.bootcamp.portal.mgr.annotation.PermissionRequired;
import com.bootcamp.portal.mgr.exc.ObjectRemovedException;
// ANJULAR JS 
// TODO improve exception handling

@Controller
@PermissionRequired
public class AccountController {

	@Autowired
	private AccountDAO AccountManager;

	@Autowired
	private PersonDAO PersonManager;
	
	protected static final Logger LOGGER = Logger
			.getLogger(AccountController.class);

	@RequestMapping(value = "orders/active/{bidderId}", method = RequestMethod.GET)
	public ResponseEntity<List<Lot>> getActiveOrders(@PathVariable("bidderId") Long bidderId) throws ObjectRemovedException {
		List<Lot> list = AccountManager.getActiveOrders(bidderId);
		return new ResponseEntity<List<Lot>>(list, HttpStatus.OK);
	}
	
	@RequestMapping(value = "orders", method = RequestMethod.GET)
	public ResponseEntity<List<Lot>> getWaitForPayOrders(@RequestParam(value="personId", defaultValue="0") Long personId, @RequestParam(value="stateId", defaultValue="0") Long stateId) throws ObjectRemovedException {
		List<Lot> list = AccountManager.getOrders(personId,stateId);
		return new ResponseEntity<List<Lot>>(list, HttpStatus.OK);
	}
	
	@RequestMapping(value = "account/lots", method = RequestMethod.GET)
	public ResponseEntity<List<Lot>> getLots(@RequestParam(value="personId", defaultValue="0") Long personId, @RequestParam(value="stateId", defaultValue="0") Long stateId) throws ObjectRemovedException {
		List<Lot> list = AccountManager.getLots(personId,stateId);
		return new ResponseEntity<List<Lot>>(list, HttpStatus.OK);
	}
	
}