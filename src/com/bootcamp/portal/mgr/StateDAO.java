package com.bootcamp.portal.mgr;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bootcamp.portal.domain.State;
import com.bootcamp.portal.mgr.base.BaseManager;
import com.bootcamp.portal.mgr.exc.ObjectRemovedException;

//ANGULAR JS
@Component
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class StateDAO extends BaseManager {

	public StateDAO() {
		super();
	}

	@Transactional
	public List<State> getAllStates() {
		List<State> result = objectList(State.class, null);
		return result;
	}

	@Transactional
	public State getById(Long id) throws ObjectRemovedException {
		return entityById(State.class, id);
	}
	
	@Transactional
	public boolean StateExists(String name, String parentId) {
		// TODO implement me!
		return false;
	}
}
