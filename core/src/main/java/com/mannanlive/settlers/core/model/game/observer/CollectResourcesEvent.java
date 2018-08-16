package com.mannanlive.settlers.core.model.game.observer;

import com.mannanlive.settlers.core.model.board.TileType;
import com.mannanlive.settlers.core.model.player.Player;

import java.util.Map;

public class CollectResourcesEvent extends GameEvent {
    private Map<TileType, Long> resources;

    public CollectResourcesEvent(Map<TileType, Long> resources, Player player) {
        this.resources = resources;
        setPlayerIfNull(player);
    }

    public Map<TileType, Long> getResources() {
        return resources;
    }

    @Override
    public GameEventType getEvent() {
        return GameEventType.COLLECT_RESOURCES;
    }

    @Override
    public String toString() {
        return getPlayer().getName() + " has collected " + resources;
    }
}
