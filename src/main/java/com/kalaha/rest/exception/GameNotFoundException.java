package com.kalaha.rest.exception;

public class GameNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -2576221153177453295L;

    public GameNotFoundException(final long id) {
        super("Could not find game " + id);
    }
}
