package com.bootcamp.portal.mgr.exc;

public class ObjectParentRemovedException extends BaseSaveObjectException {
	private static final long serialVersionUID = 8456673188878136335L;

	public ObjectParentRemovedException(String message) {
		super(message);
	}

}
