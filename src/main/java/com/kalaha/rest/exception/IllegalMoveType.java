package com.kalaha.rest.exception;

public enum IllegalMoveType {
	FIRST_PLAYER_TURN("It's First Player's turn"),
	SECOND_PLAYER_TURN("It's Second Player's turn"),
	HOME_PIT_START_INDEX("Can not start from a home pit"),
	SELECTED_EMPTY_PIT("Can not start from empty pit");

	private String message;
	
	private IllegalMoveType(String m) {
		this.message = m;
	}
	
	String getMessage() {
		return message;
	}
	

}
