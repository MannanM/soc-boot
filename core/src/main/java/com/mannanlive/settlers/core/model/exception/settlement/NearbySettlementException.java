package com.mannanlive.settlers.core.model.exception.settlement;

import com.mannanlive.settlers.core.model.board.Node;

public class NearbySettlementException extends SettlementException {
    public NearbySettlementException(Node node) {
        super(node, String.format("%s has a nearby %s.",
                node.getOwner().getName(), node.getBuilding().name().toLowerCase()));
    }
}
