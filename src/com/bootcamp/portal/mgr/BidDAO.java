package com.bootcamp.portal.mgr;

import java.util.Calendar;
import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bootcamp.portal.domain.Bid;
import com.bootcamp.portal.domain.Lot;
import com.bootcamp.portal.domain.LotStates;
import com.bootcamp.portal.domain.Person;
import com.bootcamp.portal.mgr.base.BaseManager;
import com.bootcamp.portal.mgr.dto.BidDto;
import com.bootcamp.portal.mgr.exc.ObjectRemovedException;

//ANGULAR JS
@Component
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class BidDAO extends BaseManager {
	
	private static final Long MAX_BID = 100L;
	private static final Long REACTION = 10000L;

	@Transactional
	public List<Bid> getAllBids() {
		List<Bid> result = objectList(Bid.class, Restrictions.eq("isDeleted", false));
		return result;
	}
	
	@Transactional
	public Bid getById(Long id) throws ObjectRemovedException {
		return entityById(Bid.class, id);
	}
	
	@Transactional
	public List<Bid> getBidsByLotId(Long lotId) throws ObjectRemovedException{
		return objectList(Bid.class, Restrictions.and(Restrictions.eq("lotId", lotId),  Restrictions.eq("isDeleted", false)));
	}

	@Transactional
	public Void addBid(BidDto bid) throws Exception {
		if(bid.getAmount() == null){
			throw new Exception("Your bid is wrong. Please enter another.");
		}

		Bid b = new Bid(bid, entityById(Person.class, bid.getBidderId()));
		Lot lot = entityById(Lot.class, bid.getLotId());
		if((lot.getStartDate().getTime()+lot.getDuration()) < Calendar.getInstance().getTime().getTime()){
			throw new Exception("Auction finished.");
		}
		if(lot.getBestBid() != null && b.getBidDate().getTime() - lot.getBestBid().getBidDate().getTime() <= REACTION){
			throw new Exception("Your reaction is less " + REACTION/1000 + " seconds. Please try again, if you are not a robot.");
		}
		if(lot.getOwner().getId() == b.getBidder().getId()){
			throw new Exception("You can`t bid your own lot.");
		}
		
		List<Bid> bids = objectList(Bid.class, Restrictions.and(Restrictions.eq("lotId", bid.getLotId()),Restrictions.ge("amount", bid.getAmount()),Restrictions.eq("isDeleted", false)));
		
		if(!bids.isEmpty()){
			throw new Exception("Another user put higher bid. Try to rise your bid minimum to " + (lot.getBestBid().getAmount()+1) + "$.");
		}
		
		if(lot.getState().getId() != LotStates.STARTED.getId()){
			throw new Exception("Auction finished.");
		}
		
		if(lot.getBestBid() != null && b.getBidder().getId() == lot.getBestBid().getBidder().getId()){
			throw new Exception("You can`t bid twice in row.");
		}
		
		if(b.getAmount() <= lot.getStartPrice()){
			throw new Exception("Your bid is to small. Please rise it to minimum " + (lot.getStartPrice() + 1) + "$.");
		}
		
		if(lot.getBestBid() != null && b.getAmount() != lot.getRedemption() && b.getAmount() > MAX_BID + lot.getBestBid().getAmount()){
			throw new Exception("Your bid is too high. Try to bid less then " + (MAX_BID + lot.getBestBid().getAmount() + 1) + ".");
		}else if (lot.getBestBid() == null && b.getAmount() != lot.getRedemption() && bid.getAmount() > MAX_BID + lot.getStartPrice()) {
			throw new Exception("Your bid is too high. Try to bid less then " + (MAX_BID + lot.getStartPrice() + 1) + ".");
		}
		
		try {
			saveOrUpdate(b);
		} catch (Exception e) {
			throw new Exception("Exception on Bid saving");
		}
		
		//update bestBid in lot
		lot.setBestBid(b);
		try {
			saveOrUpdate(lot);
		} catch (Exception e) {
			throw new Exception("Exception on Lot updating");
		}
		return null;
	}

	@Transactional
	public void deleteBid(Long id) throws Exception {
		try {
			Bid b = entityById(Bid.class, id);
			b.setDeleted(true);
			try {
				saveOrUpdate(b);
			} catch (Exception e) {
				throw new Exception("Exception on Bid deleting");
			}
		} catch (Exception e) {
			throw new Exception("Exception getting bid");
		}
	}

	@Transactional
	public boolean BidExists(String name, String parentId) {
		// TODO implement me!
		return false;
	}
}
