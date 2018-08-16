package com.mannanlive.settlers.core.model.exception;

import com.mannanlive.settlers.core.model.board.TileType;

public class NotEnoughResourceException extends GameException {
    public NotEnoughResourceException(Long actual, Long discard, TileType type) {
        super(String.format("You have specified %d %s but you only have %d.", actual, type.name(), discard));
    }
}
