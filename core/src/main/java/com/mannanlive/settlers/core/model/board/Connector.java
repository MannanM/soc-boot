package com.mannanlive.settlers.core.model.board;

import com.mannanlive.settlers.core.model.exception.road.CanNotBuildRoadException;
import com.mannanlive.settlers.core.model.exception.road.RoadAlreadyBuiltException;
import com.mannanlive.settlers.core.model.exception.road.RoadException;
import com.mannanlive.settlers.core.model.exception.road.RoadMustBeAdjacentToSettlementException;
import com.mannanlive.settlers.core.model.player.Player;

public class Connector {
    private final int tileId;
    private final int connectorId;
    private Node[] adjacentNodes = new Node[2];
    private Player ownedBy;

    public Connector(int tileId, int connectorId, Node adjacentNode0, Node adjacentNode1) {
        this.tileId = tileId;
        this.connectorId = connectorId;
        adjacentNodes[0] = adjacentNode0;
        adjacentNodes[1] = adjacentNode1;
        adjacentNode0.addConnector(this);
        adjacentNode1.addConnector(this);
    }

    public void buildRoad(Player player, boolean initialSettlement) {
        tryBuildRoad(player, initialSettlement);
        ownedBy = player;
    }

    public boolean canBuildRoad(Player player, boolean initialSettlement) {
        try {
            tryBuildRoad(player, initialSettlement);
            return true;
        } catch (RoadException roadException) {
            return false;
        }
    }

    private void tryBuildRoad(Player player, boolean initialSettlement) {
        if (ownedBy != null) {
            throw new RoadAlreadyBuiltException(this);
        }
        if (!playerOwnsSettlement(player)) {
            throw new CanNotBuildRoadException(this);
        }
        if (initialSettlement && playerBuildingOnSettlementWithARoad(player)) {
            throw new RoadMustBeAdjacentToSettlementException(this);
        }
    }

    private boolean playerOwnsSettlement(Player player) {
        return playerOwnsSettlement(player, 0) || playerOwnsSettlement(player, 1);
    }

    private boolean playerBuildingOnSettlementWithARoad(Player player) {
        return playersSettlementAlreadyHasARoad(player, 0) || playersSettlementAlreadyHasARoad(player, 1);
    }

    private boolean playersSettlementAlreadyHasARoad(Player player, int nodeId) {
        return playerOwnsSettlement(player, nodeId) && adjacentNodes[nodeId].hasRoads();
    }

    private boolean playerOwnsSettlement(Player player, int nodeId) {
        return adjacentNodes[nodeId].getOwner() == player;
    }

    public Node getOtherNode(Node node) {
        return adjacentNodes[0] == node ? adjacentNodes[1] : adjacentNodes[0];
    }

    public Node getNode(int nodeId) {
        return adjacentNodes[nodeId];
    }

    public int getTileId() {
        return tileId;
    }

    public int getConnectorId() {
        return connectorId;
    }

    public Player getOwnedBy() {
        return ownedBy;
    }
}
