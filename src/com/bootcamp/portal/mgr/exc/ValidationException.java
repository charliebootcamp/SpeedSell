package com.bootcamp.portal.mgr.exc;

public class ValidationException extends BaseSaveObjectException {
	private static final long serialVersionUID = -837806755873790110L;

	public ValidationException(String message) {
		super(message);
	}

}
