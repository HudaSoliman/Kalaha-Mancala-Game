package com.bol.kalaha.rest.exception;

public class PitNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -2674959480063466885L;

	public PitNotFoundException(final long id) {
		super("Could not find pit " + id);
	}
}
