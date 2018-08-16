package com.mannanlive.settlers.core.model.exception.road;

import com.mannanlive.settlers.core.model.board.Connector;

public class CanNotBuildRoadException extends RoadException {
    public CanNotBuildRoadException(Connector connector) {
        super(connector, "You can only build a road when you have an adjoining settlement or road.");
    }
}
