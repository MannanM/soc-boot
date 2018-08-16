package com.mannanlive.settlers.core.model.exception.settlement;

import com.mannanlive.settlers.core.model.board.Node;

public class BuildingAlreadyExistsException extends SettlementException {
    public BuildingAlreadyExistsException(Node node) {
        super(node, String.format("%s has a %s already here.",
                node.getOwner().getName(), node.getBuilding().name().toLowerCase()));
    }
}
