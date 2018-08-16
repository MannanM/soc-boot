package com.mannanlive.settlers.core.service;

import com.mannanlive.settlers.core.model.board.Connector;
import com.mannanlive.settlers.core.model.board.Node;
import com.mannanlive.settlers.core.model.board.TileType;
import com.mannanlive.settlers.core.model.game.Game;
import com.mannanlive.settlers.core.model.player.Player;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class AiService {
    public void buildSetupSettlement(Player player,  Game game) {
        List<Node> nodes = game.getBoard().getAvailableSettlement(player);
        Node node = nodes.get(random(nodes));
        game.buildOnNode(player, node.getTileId(), node.getNodeId());
    }

    public void buildSetupRoad(Player player, Game game) {
        List<Connector> connectors = game.getBoard().getAvailableRoads(player);
        Connector connector = connectors.get(random(connectors));
        game.buildOnConnector(player, connector.getTileId(), connector.getConnectorId());
    }

    public void placeRobber(Player player, Game game) {
        boolean found = false;
        do {
            int random = random(game.getBoard().getGameTiles());
            if (!game.getBoard().getGameTiles().get(random).isRobber()) {
                game.placeRobber(player, random);
                found = true;
            }
        } while (!found);
    }

    public void stealResource(Player player, Game game) {
        List<Player> otherPlayers = game.getBoard().getRobberPlayers(player);
        game.stealResource(player, otherPlayers.get(random(otherPlayers)));
    }

    public void discardExcessCards(Player player, Game game) {
        List<TileType> resources = game.getResourceService().getResourcesFromPlayer(player);
        Collections.shuffle(resources);
        int discard = resources.size() / 2;
        List<TileType> discardList = resources.subList(0, discard);
        game.discardResources(player, discardList);
    }

    private int random(List list) {
        return ThreadLocalRandom.current().nextInt(0, list.size());
    }
}