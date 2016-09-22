package com.bootcamp.portal.mgr;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bootcamp.portal.domain.Feedback;
import com.bootcamp.portal.domain.Lot;
import com.bootcamp.portal.domain.Person;
import com.bootcamp.portal.mgr.base.BaseManager;
import com.bootcamp.portal.mgr.dto.FeedbackDto;
import com.bootcamp.portal.mgr.dto.StatDto;
import com.bootcamp.portal.mgr.exc.BaseSaveObjectException;
import com.bootcamp.portal.mgr.exc.ObjectRemovedException;

//ANGULAR JS
@Component
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class FeedbackDAO extends BaseManager{
	
	@Transactional
	public List<Feedback> getAllFeedback(){
		List<Feedback> result = objectList(Feedback.class, null);
	    return result;
	}
	
	@Transactional
	public List<Feedback> getFeedbackByUser(Person seller){
		try {
		      return (List<Feedback>) objectList(Feedback.class, Restrictions.eq("recipient", seller));
		    } catch (Exception e) {
		      return null;
		    }
	}
	
	@Transactional
	public Feedback getFeedbackById(long id) throws ObjectRemovedException{
		return entityById(Feedback.class, id);
	}
	
	@Transactional
	public void deleteFeedback(long Id){
		try {
		      hardDeleteObject(entityById(Feedback.class, Id));
		    } catch (ObjectRemovedException e) {
		      // TODO implement me!
		    } catch (BaseSaveObjectException e) {
		      // TODO implement me!
		    }
	}
	
	@Transactional
	public boolean createFeedback(FeedbackDto feedback) throws Exception{
		Feedback f = new Feedback();
		List<Feedback> feedbacks = objectList(Feedback.class, Restrictions.and(
				Restrictions.eq("lot", entityById(Lot.class, feedback.getLotId())),
				Restrictions.eq("sender", entityById(Person.class, feedback.getSenderId())),
				Restrictions.eq("recipient", entityById(Person.class, feedback.getRecipientId()))));
		
		if(!feedbacks.isEmpty()){
			return false;
		}
		f.setMarkId(feedback.getMarkId());
		f.setTitle(feedback.getTitle());
		f.setFeedback(feedback.getFeedback());
		f.setSubmitDate(feedback.getSubmitDate());
		f.setSender(entityById(Person.class, feedback.getSenderId()));
		f.setRecipient(entityById(Person.class, feedback.getRecipientId()));;
		f.setLot(entityById(Lot.class, feedback.getLotId()));
		
		try {
		      saveOrUpdate(f);
		    } catch (Exception e) {
		      throw new Exception("Exception on Feedback saving");
		    }
		    
		    return true;
	}

	@Transactional
	public List<StatDto> getStatByPersonId(Long Id) throws Exception{
		List<StatDto> stats= new ArrayList<>();
		StatDto bad = new StatDto();
		StatDto good = new StatDto();
		StatDto neutral = new StatDto();
	
		bad.setMark("Bad");
		try {
			bad.setCountFeedbacks((Long)getCount(Feedback.class, Restrictions.and(
					Restrictions.eq("markId", Long.valueOf(0)),
					Restrictions.eq("recipient", entityById(Person.class, Id))), null));
		} catch (Exception e) {
			throw new Exception("Exception in getStatByPersonId");
		}
		stats.add(bad);
		
		good.setMark("Good");
		try {
			good.setCountFeedbacks((Long)getCount(Feedback.class, Restrictions.and(
					Restrictions.eq("markId", Long.valueOf(1)),
					Restrictions.eq("recipient", entityById(Person.class, Id))), null));
		} catch (Exception e) {
			throw new Exception("Exception in getStatByPersonId");
		}
		stats.add(good);
		
		neutral.setMark("Neutral");
		try {
			neutral.setCountFeedbacks((Long)getCount(Feedback.class, Restrictions.and(
					Restrictions.eq("markId", Long.valueOf(2)),
					Restrictions.eq("recipient", entityById(Person.class, Id))), null));
		} catch (Exception e) {
			throw new Exception("Exception in getStatByPersonId");
		}
		stats.add(neutral);
		
		return stats;
	}
}
