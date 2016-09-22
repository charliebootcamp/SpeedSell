package com.bootcamp.portal.mgr;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ManagerFactory {

	@Autowired
	private ChildManager childManager;

	@Autowired
	private PersonDAO userManager;

	static private ManagerFactory instance;

	@PostConstruct()
	public void initStatics() {
		instance = this;
	}

	static public ChildManager getChildManager() {
		return instance.childManager;
	}

	static public PersonDAO getUserManager() {
		return instance.userManager;
	}
}