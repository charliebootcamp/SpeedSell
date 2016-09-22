package com.bootcamp.portal.mgr;

import java.util.List;


import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bootcamp.portal.domain.UserActivities;
import com.bootcamp.portal.mgr.base.BaseManager;
import com.bootcamp.portal.mgr.exc.ObjectRemovedException;

//ANGULAR JS
@Component
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserActivitiesDAO extends BaseManager {

	@Transactional
	public List<UserActivities> getAllUserActivities() {
		return objectList(UserActivities.class, null);
	}
	
	@Transactional
	public UserActivities getById(Long id) throws ObjectRemovedException {
		return entityById(UserActivities.class, id);
	}
	
	@Transactional
	public List<UserActivities> getUserActivitiesByPersonId(Long personId) throws ObjectRemovedException{
		return objectList(UserActivities.class, Restrictions.eq("personId", personId),null,null,org.hibernate.criterion.Order.desc("id"));
	}

	@Transactional
	public Boolean addUserActivity(UserActivities uActivity) throws Exception {
		try {
			saveOrUpdate(uActivity);
			return true;
		} catch (Exception e) {
			throw new Exception("Exception on UserActivities saving");
		}
	}

	@Transactional
	public boolean UserActivitiesExists(String name, String parentId) {
		// TODO implement me!
		return false;
	}
}
