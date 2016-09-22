package com.bootcamp.portal.mgr.exc;

public class ObjectRemovedException extends BaseSaveObjectException {
	private static final long serialVersionUID = -8586927716532162925L;

	public ObjectRemovedException(String message) {
		super(message);
	}
}
