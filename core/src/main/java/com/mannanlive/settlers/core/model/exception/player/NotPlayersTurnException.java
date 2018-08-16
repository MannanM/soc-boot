package com.mannanlive.settlers.core.model.exception.player;

import com.mannanlive.settlers.core.model.exception.GameException;
import com.mannanlive.settlers.core.model.player.Player;

public class NotPlayersTurnException extends GameException {
    public NotPlayersTurnException(Player currentPlayer, Player player) {
        super(String.format("It is currently %s turn not %s.", currentPlayer.getName(), player.getName()));
    }
}
