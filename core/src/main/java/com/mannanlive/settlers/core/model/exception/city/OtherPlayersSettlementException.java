package com.mannanlive.settlers.core.model.exception.city;

import com.mannanlive.settlers.core.model.board.Node;
import com.mannanlive.settlers.core.model.exception.settlement.SettlementException;

public class OtherPlayersSettlementException extends SettlementException {
    public OtherPlayersSettlementException(Node node) {
        super(node, "You can only build on your own settlement, this is owned by " + node.getOwner().getName() + ".");
    }
}
