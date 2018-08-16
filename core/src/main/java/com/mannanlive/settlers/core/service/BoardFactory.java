package com.mannanlive.settlers.core.service;

import com.mannanlive.settlers.core.model.board.Board;
import com.mannanlive.settlers.core.model.board.Connector;
import com.mannanlive.settlers.core.model.board.TileType;
import com.mannanlive.settlers.core.model.game.GameTile;
import com.mannanlive.settlers.core.model.board.Node;
import com.mannanlive.settlers.core.model.board.Tile;

import java.util.ArrayList;
import java.util.List;

public class BoardFactory {
    private final List<Tile> tiles;
    private final List<GameTile> gameTiles = new ArrayList<>();
    private int tileId = 0;
    private Node[] adjacentNodes;
    private Connector[] adjacentConnector;

    public BoardFactory(List<Tile> tiles) {
        this.tiles = tiles;
    }

    public Board create() {
        firstTile();
        addTileToRight();
        addTileToRight();
        addTileToBelowLeft(0);
        addTileToRight(4);
        addTileToRight(4);
        addTileToRightOnly(4);
        addTileToBelowLeft(3);
        addTileToRight(5);
        addTileToRight(5);
        addTileToRight(5);
        addTileToRightOnly(5);
        addTileToBelow(5);
        addTileToRight(5);
        addTileToRight(5);
        addTileToRight(5);
        addTileToBelow(4);
        addTileToRight(4);
        addTileToRight(4);

        return new Board(gameTiles);
    }

    private void firstTile() {
        newNodes();
        createNode(0, 1, 2, 5);
        createConnectors(0, 1, 2, 3, 4, 5);
        addGameTile();
    }

    private void addTileToRight() {
        addLeft();
        createNode(1, 2);
        createConnectors(0, 1, 2, 3, 4);
        adjacentConnector[5] = addExistingConnector(tileId -1, 2);
        addGameTile();
    }

    private void addTileToRight(int offset) {
        addLeft();
        addExistingNode(1, tileId - offset, 3,
                        2, tileId - offset + 1, 4);
        adjacentConnector[0] = addExistingConnector(tileId -offset, 3);
        adjacentConnector[1] = addExistingConnector(tileId -offset+1, 4);
        createConnectors(2, 3, 4);
        adjacentConnector[5] = addExistingConnector(tileId -1, 2);
        addGameTile();
    }

    private void addTileToRightOnly(int topTileIndex) {
        addLeft();
        addExistingNode(1, tileId - topTileIndex, 3);
        createNode(2);
        adjacentConnector[0] = addExistingConnector(tileId - topTileIndex, 3);
        createConnectors(1, 2, 3, 4);
        adjacentConnector[5] = addExistingConnector(tileId -1, 2);
        addGameTile();
    }

    private void addTileToBelowLeft(int topTileIndex) {
        addExistingNode(1, topTileIndex, 5,
                        2, topTileIndex, 4);
        createNode(0, 5);
        createConnectors(0, 2, 3, 4, 5);
        adjacentConnector[1] = addExistingConnector(topTileIndex, 4);
        addGameTile();
    }

    private void addTileToBelow(int offset) {
        addExistingNode(0, tileId - offset, 4,
                        1, tileId - offset, 3,
                        2, tileId - offset + 1, 4);
        createNode(5);
        adjacentConnector[0] = addExistingConnector(tileId - offset, 3);
        adjacentConnector[1] = addExistingConnector(tileId - offset, 4);
        createConnectors(2, 3, 4, 5);
        addGameTile();
    }

    private void addLeft() {
        addExistingNode(0, tileId - 1, 2,
                        5, tileId - 1, 3);
    }

    private void addExistingNode(int... positions) {
        for (int pI = 0; pI < positions.length; pI = pI+3) {
            int position = positions[pI];
            int tileId = positions[pI + 1];
            int nodeId = positions[pI + 2];
            adjacentNodes[position] = gameTiles.get(tileId).getNode(nodeId);
            adjacentNodes[position].addAdjacentTile(tiles.get(this.tileId));
        }
    }

    private Connector addExistingConnector(int topTileIndex, int position) {
        return gameTiles.get(topTileIndex).getConnector(position);
    }

    private void createNode(int... positions) {
        Tile tile = tiles.get(tileId);
        for (int position : positions) {
            adjacentNodes[position] = new Node(tile, tileId, position);
        }
    }

    private void createConnectors(int... positions) {
        for (int position : positions) {
            adjacentConnector[position] = new Connector(tileId, position,
                    adjacentNodes[position], adjacentNodes[(position+1)%6]);
        }
    }

    private void addGameTile() {
        gameTiles.add(new GameTile(tiles.get(tileId), adjacentNodes, adjacentConnector));
        tileId++;
        newNodes();
    }

    private void newNodes() {
        if (gameTiles.size() < tiles.size()) {
            adjacentNodes = new Node[6];
            adjacentConnector = new Connector[6];
            createNode(3, 4);
        }
    }
}
