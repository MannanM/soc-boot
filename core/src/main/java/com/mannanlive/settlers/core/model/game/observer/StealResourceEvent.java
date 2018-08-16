package com.mannanlive.settlers.core.model.game.observer;

import com.mannanlive.settlers.core.model.board.TileType;
import com.mannanlive.settlers.core.model.player.Player;

public class StealResourceEvent extends GameEvent {
    private Player robbedPlayer;
    private TileType resource;
    private Long amount;

    public StealResourceEvent() {
    }

    public StealResourceEvent(Player victim) {
        robbedPlayer = victim;
    }

    public StealResourceEvent(Player victim, TileType stolenResource) {
        robbedPlayer = victim;
        resource = stolenResource;
        amount = 1L;
    }

    public Player getRobbedPlayer() {
        return robbedPlayer;
    }

    public void setRobbedPlayer(Player robbedPlayer) {
        this.robbedPlayer = robbedPlayer;
    }

    public TileType getResource() {
        return resource;
    }

    public void setResource(TileType resource) {
        this.resource = resource;
    }

    public Long getAmount() {
        return amount;
    }

    @Override
    public GameEventType getEvent() {
        return GameEventType.STEAL_RESOURCE;
    }

    @Override
    public String toString() {
        if (robbedPlayer == null) {
            return getPlayer().getName() + " stole no resources";
        }

        if (resource == null) {
            return getPlayer().getName() + " stole no resources from " + robbedPlayer.getName();
        }

        return getPlayer().getName() + " stole " + resource + " from " + robbedPlayer.getName();
    }
}
