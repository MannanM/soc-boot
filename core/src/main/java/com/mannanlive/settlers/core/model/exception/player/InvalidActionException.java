package com.mannanlive.settlers.core.model.exception.player;

import com.mannanlive.settlers.core.model.exception.GameException;
import com.mannanlive.settlers.core.model.game.GameStage;

import java.util.Arrays;

public class InvalidActionException extends GameException {
    public InvalidActionException(GameStage stage, GameStage[] stages) {
        super(String.format("This action (%s) is not allowed at this time %s.", stage, Arrays.toString(stages)));
    }
}
