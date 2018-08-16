package com.mannanlive.settlers.core.model.exception;

public class GameException extends RuntimeException {
    public GameException() {
    }

    public GameException(String message) {
        super(message);
    }
}
