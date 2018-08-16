package com.mannanlive.settlers.core.model.game.observer;

import com.mannanlive.settlers.core.model.board.Connector;

public class RoadGameEvent extends GameEvent {
    private Connector node;

    public RoadGameEvent(Connector node) {
        this.node = node;
    }

    public Connector getConnector() {
        return node;
    }

    @Override
    public GameEventType getEvent() {
        return GameEventType.BUILT_ROAD;
    }

    @Override
    public String toString() {
        return getPlayer().getName() + " has built a road";
    }
}
