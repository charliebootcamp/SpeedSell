package com.bootcamp.portal.web.controller;


import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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

import com.bootcamp.portal.domain.LotStates; 
import com.bootcamp.portal.domain.Lot;
import com.bootcamp.portal.domain.LotStates;
import com.bootcamp.portal.domain.UActivities;
import com.bootcamp.portal.domain.UserActivities;
import com.bootcamp.portal.mgr.BidDAO;
import com.bootcamp.portal.mgr.LotDAO;
import com.bootcamp.portal.mgr.PersonDAO;
import com.bootcamp.portal.mgr.StateDAO;
import com.bootcamp.portal.mgr.UserActivitiesDAO;
import com.bootcamp.portal.mgr.annotation.PermissionRequired;
import com.bootcamp.portal.mgr.dto.BidDto;
import com.bootcamp.portal.mgr.dto.CardDto;
import com.bootcamp.portal.mgr.dto.LotDto;
import com.bootcamp.portal.mgr.exc.ObjectRemovedException;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;

// ANJULAR JS 
// TODO improve exception handling

@Controller
@PermissionRequired
public class PaymentController {
	
	@Autowired
	private UserActivitiesDAO userActivitiesManager;
	
	@Autowired
	private PersonDAO personManager;
	
	@Autowired
	private LotDAO lotManager;
	
	@Autowired
	private StateDAO stateManager;
	
	@Autowired
	private BidDAO bidManager;

	protected static final Logger LOGGER = Logger
			.getLogger(PaymentController.class);

	@SuppressWarnings("finally")
	@RequestMapping(value = "payment", method = RequestMethod.PUT)
	public ResponseEntity<Void> payCommission(@RequestBody CardDto uCard)
			throws Exception {
		Lot lot = lotManager.getById(uCard.getLotId());
		try{
			pay(uCard, lot.getSubcategory().getCommission());
		} catch (StripeException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}
		catch (NoSuchMethodError e) {
			//not known exception
			e.printStackTrace();
		}
		
		finally{
			lot.setPaidCommission(true);
			lotManager.updateLot(new LotDto(lot));
			
			UserActivities userActivities = new UserActivities(
					uCard.getPayerId(), 
					Calendar.getInstance().getTime(),
					"Payed a commission for lot "+uCard.getLotId()+".",
					UActivities.PAYED_A_COMMISSION.getUActId());
			
	        userActivitiesManager.addUserActivity(userActivities);
			return new ResponseEntity<Void>(HttpStatus.PARTIAL_CONTENT);
		}
	}
	
	@SuppressWarnings("finally")
	@RequestMapping(value = "redemption", method = RequestMethod.PUT)
	public ResponseEntity<Void> payRedemption(@RequestBody CardDto uCard)
			throws Exception {
		
		Lot lot = lotManager.getById(uCard.getLotId());
		try{
			pay(uCard, lot.getRedemption());
		} catch (StripeException e) {
            e.printStackTrace();
        } catch(Exception e) {
        	e.printStackTrace();
        	return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
        catch (NoSuchMethodError e) {
        	//not known exception
        	//e.printStackTrace();
		}
			
        finally{
        	bidManager.addBid(new BidDto(lot.getRedemption(), uCard.getPayerId(), lot.getId()));
        	lot = lotManager.getById(uCard.getLotId());
        	lot.setState(stateManager.getById(LotStates.PAID.getId()));
        	lot.setBuyPrice(lot.getRedemption());
        	lotManager.updateLot(new LotDto(lot));
        			
			UserActivities userActivities = new UserActivities(
					uCard.getPayerId(), 
					Calendar.getInstance().getTime(),
					"Payed a redemption for lot "+uCard.getLotId()+".",
					UActivities.PAYED_A_REDEMPTION.getUActId());
			
	        userActivitiesManager.addUserActivity(userActivities);
        	return new ResponseEntity<Void>(HttpStatus.PARTIAL_CONTENT);
        }
	}
	
	
	@RequestMapping(value = "confirmsell", method = RequestMethod.PUT)
	public ResponseEntity<Void> confirmLot(@RequestBody Long id) throws ObjectRemovedException {
		Lot lot = lotManager.getById(id);
		LotDto lotdto = new LotDto(lot);
		lotdto.setStateId(LotStates.PAID.getId());
		try {
			lotManager.updateLot(lotdto);
			return new ResponseEntity<Void>(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}
	}
	
	@SuppressWarnings("finally")
	@RequestMapping(value="lots/premium/ask", method = RequestMethod.PUT)
	public ResponseEntity<Void> askPremium(@RequestBody CardDto uCard) throws Exception{
		try {
			pay(uCard, lotManager.getById(uCard.getLotId()).getSubcategory().getCommission());
			
		} catch (ObjectRemovedException e) {
			e.printStackTrace();
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		} catch (StripeException e) {
			e.printStackTrace();
		}
		finally{
			boolean flag = false;
			flag = lotManager.makePremium(uCard.getLotId(), false);
			if (!flag){
				return new ResponseEntity<Void>(HttpStatus.CONFLICT);
			} 
        			
			UserActivities userActivities = new UserActivities(
					uCard.getPayerId(), 
					Calendar.getInstance().getTime(),
					"Payed a commissiom for premium lot "+uCard.getLotId()+".",
					UActivities.PAYED_A_COMMISSION.getUActId());
			
	        userActivitiesManager.addUserActivity(userActivities);
	        return new ResponseEntity<Void>(HttpStatus.OK);
        }
	}
	
	private ResponseEntity<Void> pay(CardDto card, Long amount) throws StripeException, ObjectRemovedException{
		Map<String, Object> cardMap = new HashMap<String, Object>();
        cardMap.put("name", personManager.getById(card.getPayerId()).getFullName());
        cardMap.put("number", card.getCardNumb());
        cardMap.put("cvc", card.getCvc());
        cardMap.put("exp_month", card.getExpMonth());
        cardMap.put("exp_year", card.getExpYear());
        Stripe.apiKey = "sk_test_ampP1yQZD9jZN5KhVsCKfS4i";
  		Map<String, Object> chargeMap =  new HashMap<String, Object>();
        chargeMap.put("amount", amount);
        chargeMap.put("currency", "usd");
        chargeMap.put("card", cardMap);
        
            Charge charge = Charge.create(chargeMap);
            if (charge == null){
            	return new ResponseEntity<Void>(HttpStatus.CONFLICT);
            } else return new ResponseEntity<Void>(HttpStatus.OK);
        
	}
	
}