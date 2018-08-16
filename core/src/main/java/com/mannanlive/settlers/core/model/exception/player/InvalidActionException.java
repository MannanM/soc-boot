package com.mannanlive.settlers.core.model.exception.player;

import com.mannanlive.settlers.core.model.exception.GameException;
import com.mannanlive.settlers.core.model.game.GameStage;

public class InvalidActionException extends GameException {
    public InvalidActionException(GameStage stage) {
        super("This action is not allowed at this time.");
    }
}
