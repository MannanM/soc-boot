package com.mannanlive.settlers.core.model.exception.road;

import com.mannanlive.settlers.core.model.board.Connector;

public class RoadMustBeAdjacentToSettlementException extends RoadException {
    public RoadMustBeAdjacentToSettlementException(Connector connector) {
        super(connector, "You must build your road next to your initial settlement.");
    }
}
