package com.mannanlive.settlers.core.model.exception.road;

import com.mannanlive.settlers.core.model.board.Connector;
import com.mannanlive.settlers.core.model.exception.GameException;

public class RoadException extends GameException {
    private final Connector connector;
    public RoadException(Connector connector, String message) {
        super(message);
        this.connector = connector;
    }
}
