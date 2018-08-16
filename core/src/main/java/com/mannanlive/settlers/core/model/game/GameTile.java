package com.mannanlive.settlers.core.model.game;

import com.mannanlive.settlers.core.model.board.Connector;
import com.mannanlive.settlers.core.model.board.Node;
import com.mannanlive.settlers.core.model.board.Tile;
import com.mannanlive.settlers.core.model.board.TileType;
import com.mannanlive.settlers.core.model.player.Player;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GameTile {
    private final Tile tile;
    private final Node[] adjacentNodes;
    private final Connector[] adjacentConnectors;
    private boolean robber;

    public GameTile(Tile tile, Node[] adjacentNodes, Connector[] adjacentConnector) {
        this.tile = tile;
        this.adjacentNodes = adjacentNodes;
        this.adjacentConnectors = adjacentConnector;
    }

    public List<Node> getPlayerNodes(Player player) {
        return Arrays.stream(adjacentNodes).filter(node -> node.getOwner() == player).collect(Collectors.toList());
    }

    public Tile getTile() {
        return tile;
    }

    public TileType getTileType() {
        return tile.getType();
    }

    public Node getNode(int i) {
        return adjacentNodes[i];
    }

    public Connector getConnector(int position) {
        return adjacentConnectors[position];
    }

    public boolean isRobber() {
        return robber;
    }

    public void setRobber(boolean robber) {
        this.robber = robber;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(tile.toString()).append("\n");
        for (int i = 0; i < 6; i++) {
            sb
                .append(i)
                .append("Tiles: ")
                .append(adjacentNodes[i].getAdjacentTiles())
                .append("\n");

        }
        return sb.toString();
    }

    public Stream<Connector> getConnectors() {
        return Stream.of(adjacentConnectors);
    }

    public Stream<Node> getNodes() {
        return Stream.of(adjacentNodes);
    }

    public List<Node> getPopulatedNodes() {
        return Stream.of(adjacentNodes).filter(node -> node.getOwner() != null).collect(Collectors.toList());
    }
}
