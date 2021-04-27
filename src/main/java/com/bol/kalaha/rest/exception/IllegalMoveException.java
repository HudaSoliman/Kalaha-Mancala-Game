package com.bol.kalaha.rest.exception;

public class IllegalMoveException extends RuntimeException {

	private static final long serialVersionUID = -3407336632879524317L;

	public IllegalMoveException(final String message) {
		super("Illegal move: " + message);
	}

	public IllegalMoveException(IllegalMoveType type) {
		this(type.getMessage());
	}

}
