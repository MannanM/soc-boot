package com.mannanlive.settlers.core.model.exception.city;

import com.mannanlive.settlers.core.model.board.Building;
import com.mannanlive.settlers.core.model.board.Node;
import com.mannanlive.settlers.core.model.exception.settlement.SettlementException;
import com.mannanlive.settlers.core.model.player.Player;

public class BuildOnSettlementOnlyException extends SettlementException {
    public BuildOnSettlementOnlyException(Node node) {
        super(node, getMessage(node));
    }

    private static String getMessage(Node node) {
        if (node.getBuilding() == Building.NOTHING) {
            return "You must build a city on an existing settlement.";
        }
        return "There is already a city there.";
    }
}
