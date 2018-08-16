package com.mannanlive.settlers.core.model.game.observer;

import com.mannanlive.settlers.core.model.board.TileType;

import java.util.List;

public class DiscardExcessResourcesEvent extends GameEvent  {
    private final List<TileType> discardedResources;

    public DiscardExcessResourcesEvent(List<TileType> discardedResources) {
        this.discardedResources = discardedResources;
    }

    @Override
    public GameEventType getEvent() {
        return GameEventType.DISCARD_EXCESS_RESOURCES;
    }

    @Override
    public String toString() {
        return String.format("%s has discarded %s", getPlayer().getName(), discardedResources);
    }

    public List<TileType> getDiscardedResources() {
        return discardedResources;
    }
}
