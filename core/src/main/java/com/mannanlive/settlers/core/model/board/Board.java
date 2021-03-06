package com.mannanlive.settlers.core.model.board;

import com.mannanlive.settlers.core.model.exception.GameException;
import com.mannanlive.settlers.core.model.exception.RobberMustBeMovedException;
import com.mannanlive.settlers.core.model.game.GameTile;
import com.mannanlive.settlers.core.model.game.observer.GameEvent;
import com.mannanlive.settlers.core.model.game.observer.RoadGameEvent;
import com.mannanlive.settlers.core.model.game.observer.RobberGameEvent;
import com.mannanlive.settlers.core.model.game.observer.SettlementGameEvent;
import com.mannanlive.settlers.core.model.player.Player;

import java.util.List;
import java.util.stream.Collectors;

public class Board {
    private final List<GameTile> gameTiles;

    public Board(List<GameTile> gameTiles) {
        this.gameTiles = gameTiles;
    }

    public List<GameTile> getGameTiles() {
        return gameTiles;
    }

    public SettlementGameEvent buildSettlement(Player player, int tileId, int nodeId) {
        Node node = gameTiles.get(tileId).getNode(nodeId);
        node.buildSettlement(player, isSetupPhase(getPlayerBuildings(player)));
        return new SettlementGameEvent(node);
    }

    public RoadGameEvent buildRoad(Player player, int tileId, int connectorId) {
        Connector connector = gameTiles.get(tileId).getConnector(connectorId);
        connector.buildRoad(player, isSetupPhase(getPlayerRoads(player)));
        return new RoadGameEvent(connector);
    }

    public GameEvent buildCity(Player player, int tileId, int nodeId) {
        Node node = gameTiles.get(tileId).getNode(nodeId);
        node.buildCity(player);
        return new SettlementGameEvent(node);
    }

    public List<Node> getPlayerBuildings(Player player) {
        return gameTiles.stream()
                .flatMap(GameTile::getNodes)
                .filter(node -> node.getOwner() == player)
                .distinct()
                .collect(Collectors.toList());
    }


    public List<Connector> getPlayerRoads(Player player) {
        return gameTiles.stream()
                .flatMap(GameTile::getConnectors)
                .filter(connector -> connector.getOwner() == player)
                .distinct()
                .collect(Collectors.toList());
    }

    public List getAvailable(Player player, BuildActions action) {
        switch (action) {
            case ROAD: return getAvailableRoads(player);
            case SETTLEMENT: return getAvailableSettlements(player);
            case CITY: return getAvailableCities(player);
            default:
                throw new IllegalArgumentException("Invalid action: " + action);
        }
    }

    public List<Connector> getAvailableRoads(Player player) {
        boolean setupPhase = isSetupPhase(getPlayerRoads(player));
        return gameTiles.stream()
                .flatMap(GameTile::getConnectors)
                .filter(connector -> connector.canBuildRoad(player, setupPhase))
                .distinct()
                .collect(Collectors.toList());
    }

    public List<Node> getAvailableSettlements(Player player) {
        boolean setupPhase = isSetupPhase(getPlayerBuildings(player));
        return gameTiles.stream()
                .flatMap(GameTile::getNodes)
                .filter(node -> node.canBuildSettlement(player, setupPhase))
                .distinct()
                .collect(Collectors.toList());
    }

    public List<Node> getAvailableCities(Player player) {
        return gameTiles.stream()
                .flatMap(GameTile::getNodes)
                .filter(node -> node.getOwner() == player && node.getBuilding() == Building.SETTLEMENT)
                .distinct()
                .collect(Collectors.toList());
    }

    private boolean isSetupPhase(List count) {
        return count.size() < 2;
    }

    public RobberGameEvent placeRobber(int tileId) {
        GameTile tile = gameTiles.get(tileId);
        if (tile.isRobber()) {
            throw new RobberMustBeMovedException();
        }
        for (int i = 0; i < gameTiles.size(); i++) {
            gameTiles.get(i).setRobber(i == tileId);
        }
        return new RobberGameEvent(tileId, tile);
    }

    public List<Player> getRobberPlayers(Player player) {
        return getRobberTile().getNodes()
                //in theory you could select yourself as a robbed player
                .filter(node -> node.isOwnedByOtherPlayer(player))
                .map(Node::getOwner)
                .distinct()
                .collect(Collectors.toList());
    }

    private GameTile getRobberTile() {
        for (GameTile tile : gameTiles) {
            if (tile.isRobber()) {
                return tile;
            }
        }
        throw new GameException("Board does not have a robber");
    }
}
