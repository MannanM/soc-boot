package com.mannanlive.settlers.core.model.game.observer;

import com.mannanlive.settlers.core.model.player.Player;

public abstract class GameEvent {
    private int version;
    private Player player;
    private String message;

    public Player getPlayer() {
        return player;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public void setPlayerIfNull(Player player) {
        if (this.player == null) {
            this.player = player;
        }
    }

    public abstract GameEventType getEvent();
}
