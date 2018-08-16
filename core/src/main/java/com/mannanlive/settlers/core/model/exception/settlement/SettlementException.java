package com.mannanlive.settlers.core.model.exception.settlement;

import com.mannanlive.settlers.core.model.board.Node;
import com.mannanlive.settlers.core.model.exception.GameException;

public class SettlementException extends GameException {
    private final Node node;

    public SettlementException(Node node, String message) {
        super(message);
        this.node = node;
    }
}
