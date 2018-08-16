package com.mannanlive.settlers.core.model.board;

import com.mannanlive.settlers.core.model.exception.settlement.BuildingAlreadyExistsException;
import com.mannanlive.settlers.core.model.exception.settlement.NearbySettlementException;
import com.mannanlive.settlers.core.model.exception.settlement.SettlementException;
import com.mannanlive.settlers.core.model.player.Player;

import java.util.ArrayList;
import java.util.List;

public class Node {
    private final List<Tile> adjacentTiles = new ArrayList<>();
    private final List<Connector> adjacentConnectors = new ArrayList<>();
    private final int tileId;
    private final int nodeId;
    private Building building = Building.NOTHING;
    private Player ownedBy;

    public Node(Tile tile, int tileId, int nodeId) {
        adjacentTiles.add(tile);
        this.tileId = tileId;
        this.nodeId = nodeId;
    }

    public boolean canBuildSettlement(Player player, boolean setupPhase) {
        try {
            tryBuildSettlement(player, setupPhase);
            return true;
        } catch (SettlementException se) {
            return false;
        }
    }

    public void buildSettlement(Player player, boolean setupPhase) {
        tryBuildSettlement(player, setupPhase);
        ownedBy = player;
        building = Building.SETTLEMENT;
    }

    public void tryBuildSettlement(Player player, boolean setupPhase) {
        if (building != Building.NOTHING) {
            throw new BuildingAlreadyExistsException(this);
        }
        for (Connector adjacentConnector : adjacentConnectors) {
            Node adjacentNode = adjacentConnector.getOtherNode(this);
            if (adjacentNode.getBuilding() != Building.NOTHING) {
                throw new NearbySettlementException(adjacentNode);
            }
        }
    }

    public boolean hasRoads() {
        return adjacentConnectors.stream().anyMatch(connector -> connector.getOwnedBy() != null);
    }

    public boolean isOwnedByOtherPlayer(Player player) {
        return ownedBy != null && ownedBy != player;
    }

    public void addAdjacentTile(Tile tile) {
        adjacentTiles.add(tile);
    }

    public List<Tile> getAdjacentTiles() {
        return adjacentTiles;
    }

    @Override
    public String toString() {
        return String.format("%d", hashCode());
    }

    public Building getBuilding() {
        return building;
    }

    public Player getOwner() {
        return ownedBy;
    }

    public int getTileId() {
        return tileId;
    }

    public int getNodeId() {
        return nodeId;
    }

    public void addConnector(Connector connector) {
        adjacentConnectors.add(connector);
    }
}
