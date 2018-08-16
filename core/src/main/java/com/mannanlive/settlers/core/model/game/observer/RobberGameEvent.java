package com.mannanlive.settlers.core.model.game.observer;

import com.mannanlive.settlers.core.model.game.GameTile;

public class RobberGameEvent extends GameEvent {
    private GameTile tile;
    private int tileId;

    public RobberGameEvent(int tileId, GameTile tile) {
        this.tileId = tileId;
        this.tile = tile;
    }

    public int getTileId() {
        return tileId;
    }

    public GameTile getTile() {
        return tile;
    }

    @Override
    public GameEventType getEvent() {
        return GameEventType.PLACE_ROBBER;
    }

    @Override
    public String toString() {
        return getPlayer().getName() + " has placed the robber on a " +
                tile.getTileType() + " (" + tile.getTile().getNumber() + ")";
    }
}
