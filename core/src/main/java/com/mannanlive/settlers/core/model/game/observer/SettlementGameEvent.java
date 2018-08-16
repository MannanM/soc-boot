package com.mannanlive.settlers.core.model.game.observer;

import com.mannanlive.settlers.core.model.board.Node;

public class SettlementGameEvent extends GameEvent {
    private Node node;

    public SettlementGameEvent(Node node) {
        this.node = node;
    }

    public Node getNode() {
        return node;
    }

    @Override
    public GameEventType getEvent() {
        return GameEventType.BUILT_SETTLEMENT;
    }

    @Override
    public String toString() {
        return getPlayer().getName() + " has built a settlement next to " + node.getAdjacentTiles();
    }
}
