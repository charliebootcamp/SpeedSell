package com.bootcamp.portal.web.controller;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.bootcamp.portal.domain.Bid;
import com.bootcamp.portal.domain.UActivities;
import com.bootcamp.portal.domain.UserActivities;
import com.bootcamp.portal.mgr.BidDAO;
import com.bootcamp.portal.mgr.UserActivitiesDAO;
import com.bootcamp.portal.mgr.annotation.PermissionRequired;
import com.bootcamp.portal.mgr.dto.BidDto;
import com.bootcamp.portal.mgr.exc.ObjectRemovedException;

// ANJULAR JS 
// TODO improve exception handling

@Controller
@PermissionRequired
public class BidController {

	@Autowired
	private BidDAO BidManager;
	
	@Autowired
	private UserActivitiesDAO userActivitiesManager;

	protected static final Logger LOGGER = Logger
			.getLogger(BidController.class);

	@RequestMapping(value = "bid/{id}", method = RequestMethod.GET)
	public ResponseEntity<Serializable> getBidById(@PathVariable("id") Long id)
			throws Exception {
		try {
	        return new ResponseEntity<Serializable>(BidManager.getById(id), HttpStatus.OK);
	    }
	    catch(ObjectRemovedException e) {
	    	LOGGER.error(e.getMessage());
	    	return new ResponseEntity<Serializable>("Error 404. There is no such bid.",HttpStatus.NOT_FOUND);
	    }
	}

	@RequestMapping(value = "bid/lotId/{id}", method = RequestMethod.GET)
	public ResponseEntity<List<Bid>> getBidsByLotId(@PathVariable("id")Long id) 
			throws Exception {
		List<Bid> list = BidManager.getBidsByLotId(id);
		return new ResponseEntity<List<Bid>>(list, HttpStatus.OK);
	}

    @RequestMapping(value = "create_bid", method = RequestMethod.POST)
    public ResponseEntity<Object> addBid(@RequestBody BidDto bid,
            UriComponentsBuilder builder) throws Exception {
        try {
			BidManager.addBid(bid);
			//create userActivity report (Long personId, Date dateOf, String info, Long typeId)
			UserActivities userActivities = new UserActivities(
					bid.getBidderId(), 
					Calendar.getInstance().getTime(),
					"User bid lot id = " + bid.getLotId() + " with amount = " + bid.getAmount(),
					UActivities.MADE_BID.getUActId());
	        	userActivitiesManager.addUserActivity(userActivities);
	    }
	    catch(Exception e) {
	    	LOGGER.error(e.getMessage());
	    	Map<String,String> result = new HashMap<String,String>();

	    	result.put("message", e.getMessage());
	    	return new ResponseEntity<Object>(result,HttpStatus.CONFLICT);
	    }
        
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("bid/{id}")
                .buildAndExpand(bid.getId()).toUri());
        return new ResponseEntity<Object>(headers, HttpStatus.CREATED);
    }

	@RequestMapping(value = "delete_bid/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteBid(@PathVariable("id") Long id) throws Exception {
		BidManager.deleteBid(id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
}