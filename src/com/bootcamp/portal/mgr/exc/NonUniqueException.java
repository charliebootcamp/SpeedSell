package com.bootcamp.portal.mgr.exc;

public class NonUniqueException extends BaseSaveObjectException {
	private static final long serialVersionUID = -8586927716532162925L;

	public NonUniqueException(String message) {
		super(message);
	}
}
