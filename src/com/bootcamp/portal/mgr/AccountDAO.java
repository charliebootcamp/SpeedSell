package com.bootcamp.portal.mgr;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bootcamp.portal.domain.Bid;
import com.bootcamp.portal.domain.Lot;
import com.bootcamp.portal.domain.LotStates;
import com.bootcamp.portal.domain.Person;
import com.bootcamp.portal.domain.State;
import com.bootcamp.portal.mgr.base.BaseManager;
import com.bootcamp.portal.mgr.exc.ObjectRemovedException;

//ANGULAR JS
@Component
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class AccountDAO extends BaseManager {

	@Transactional
	public List<Lot> getActiveOrders(Long bidderId) throws ObjectRemovedException {
		List<Lot> result = new ArrayList<>();
		Lot tmp;
		//Getting all bids made by bidder 
		List<Bid> bids = new ArrayList<>(objectList(Bid.class, Restrictions.and(
				Restrictions.eq("bidder", entityById(Person.class, bidderId)),  
				Restrictions.eq("isDeleted", false)))) ;

		Set<Long> lotsId = new TreeSet<>();
		if(bids != null){
			//Getting unique lot id from bids
			for(int i=0;i<bids.size();i++){
				lotsId.add(bids.get(i).getLotId());
			}
			
			//Comparing lot id where user made bid with started lots
			for(Long i:lotsId){
				tmp = entityById(Lot.class, i);
				if(tmp.getState().getId() == LotStates.STARTED.getId()){
					result.add(tmp);
				}
			}
		}
		return result;
	}

	@Transactional
	public List<Lot> getOrders(Long personId, Long stateId) throws ObjectRemovedException {
		List<Lot> result = new ArrayList<>();
		//Getting all lots that have state stateId
		List<Lot> lots = new ArrayList<>(objectList(Lot.class, Restrictions.eq("state", entityById(State.class, stateId))));
		if(!lots.isEmpty()){
			//Comparing lot id where user made bid with started lots
			for(Lot i:lots){
				if(i.getBestBid()!=null){
					if(i.getBestBid().getBidder().getId()== personId){
						result.add(i);
					}
				}
			}
		}
		return result;
	}
	
	
	@Transactional
	public List<Lot> getLots(Long personId, Long stateId) throws ObjectRemovedException {
		
		//Getting all lots that have state Started and have match in ownerId
		
		
		List<Lot> result = new ArrayList<>(objectList(Lot.class, 
				Restrictions.and(
						Restrictions.eq("state", entityById(State.class, stateId)),
						Restrictions.eq("owner", entityById(Person.class, personId)))));
		
		return result;
	}

}
