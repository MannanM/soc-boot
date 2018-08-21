package com.mannanlive.settlers.core.model.exception.settlement;

import com.mannanlive.settlers.core.model.board.Node;

public class NeedARoadException extends SettlementException {
    public NeedARoadException(Node settlement) {
        super(settlement, "You need to have a connecting road.");
    }
}
