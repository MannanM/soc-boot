package com.mannanlive.settlers.core.service;

import com.mannanlive.settlers.core.model.board.Building;
import com.mannanlive.settlers.core.model.board.Node;
import com.mannanlive.settlers.core.model.board.Piece;
import com.mannanlive.settlers.core.model.board.Tile;
import com.mannanlive.settlers.core.model.game.Game;
import com.mannanlive.settlers.core.model.game.GameTile;
import com.mannanlive.settlers.core.model.player.Player;
import com.mannanlive.settlers.core.model.player.PlayerState;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Deprecated
public class GameStateExtractor {
    public List<Tile> getTiles(Game game) {
        List<Tile> tiles = new ArrayList<>();
        for (GameTile gameTile : game.getBoard().getGameTiles()) {
            tiles.add(gameTile.getTile());
        }
        return tiles;
    }

    public List<PlayerState> getState(Game game) {
        List<PlayerState> states = new ArrayList<>();
        for (Player player : game.getPlayers()) {
            states.add(new PlayerState());
        }

        Set<Node> processedNodes = new HashSet<>();
        List<GameTile> gameTiles = game.getBoard().getGameTiles();
        for (int tileId = 0; tileId < gameTiles.size(); tileId++) {
            GameTile tile = gameTiles.get(tileId);
            for (int nodeId = 0; nodeId < 6; nodeId++) {
                Node node = tile.getNode(nodeId);
                if (node.getBuilding() != Building.NOTHING) {
                    if (!processedNodes.contains(node)) {
                        PlayerState state = states.get(game.getPlayers().indexOf(node.getOwner()));
                        state.addPiece(new Piece(tileId, nodeId, node.getBuilding()));
                        processedNodes.add(node);;
                    }
                }
            }
        }
        return states;
    }
}
