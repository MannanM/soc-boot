package com.mannanlive.settlers.core.model.board;

import com.mannanlive.settlers.core.model.exception.city.BuildOnSettlementOnlyException;
import com.mannanlive.settlers.core.model.exception.city.OtherPlayersSettlementException;
import com.mannanlive.settlers.core.model.exception.settlement.BuildingAlreadyExistsException;
import com.mannanlive.settlers.core.model.exception.settlement.NearbySettlementException;
import com.mannanlive.settlers.core.model.exception.settlement.NeedARoadException;
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

    private void tryBuildSettlement(Player player, boolean setupPhase) {
        if (building != Building.NOTHING) {
            throw new BuildingAlreadyExistsException(this);
        }
        boolean hasNearbyRoad = false;
        for (Connector adjacentConnector : adjacentConnectors) {
            if (adjacentConnector.getOwner() == player) {
                hasNearbyRoad = true;
            }
            Node adjacentNode = adjacentConnector.getOtherNode(this);
            if (adjacentNode.getBuilding() != Building.NOTHING) {
                throw new NearbySettlementException(adjacentNode);
            }
        }
        if (!setupPhase && !hasNearbyRoad) {
            throw new NeedARoadException(this);
        }
    }

    public void buildCity(Player player) {
        tryBuildCity(player);
        building = Building.CITY;
    }

    private void tryBuildCity(Player player) {
        if (building != Building.SETTLEMENT) {
            throw new BuildOnSettlementOnlyException(this);
        }
        if (ownedBy != player) {
            throw new OtherPlayersSettlementException(this);
        }
    }


    public boolean hasRoads() {
        return adjacentConnectors.stream().anyMatch(connector -> connector.getOwner() != null);
    }

    public boolean isOwnedByOtherPlayer(Player player) {
        return ownedBy != null && ownedBy != player;
    }

    public List<Connector> getAdjacentConnectors() {
        return adjacentConnectors;
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
