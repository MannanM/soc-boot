package com.mannanlive.settlers.core.model.game.observer;

import com.mannanlive.settlers.core.model.player.Player;

public class CreateGameEvent extends GameEvent {
    public CreateGameEvent(Player player) {
        this.setPlayerIfNull(player);
    }

    @Override
    public GameEventType getEvent() {
        return GameEventType.CREATE;
    }

    @Override
    public String toString() {
        return getPlayer().getName() + " has created a game.";
    }
}
