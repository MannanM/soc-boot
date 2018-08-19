package com.mannanlive.settlers.core.model.exception;

import com.mannanlive.settlers.core.model.board.TileType;

public class NotEnoughResourceException extends GameException {
    public NotEnoughResourceException(Long need, Long have, TileType type) {
        super(String.format("You need %d %s but you %s.", need, type.name().toLowerCase(), getText(have)));
    }

    private static String getText(Long have) {
        if (have == 0) {
            return "have none";
        }
        return String.format("only have %d", have);
    }
}
