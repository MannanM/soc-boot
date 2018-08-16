package com.mannanlive.settlers.core.model.exception.player;

import com.mannanlive.settlers.core.model.exception.GameException;

public class PlayerNotFoundException extends GameException {
    public PlayerNotFoundException(long player) {
        super(String.format("No player with id %d found", player));
    }
}
