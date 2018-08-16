package com.mannanlive.settlers.core.model.exception.road;

import com.mannanlive.settlers.core.model.board.Connector;

public class RoadAlreadyBuiltException extends RoadException {
    public RoadAlreadyBuiltException(Connector connector) {
        super(connector, String.format("Road already built by %s.", connector.getOwnedBy().getName()));
    }
}
