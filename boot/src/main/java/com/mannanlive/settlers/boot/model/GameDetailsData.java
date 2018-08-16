package com.mannanlive.settlers.boot.model;

import com.mannanlive.settlers.core.model.board.Tile;
import com.mannanlive.settlers.core.model.player.Player;

import java.util.List;

public class GameDetailsData {
    private final List<Tile> tiles;
    private final List<Player> players;

    public GameDetailsData(List<Tile> tiles, List<Player> players) {
        this.tiles = tiles;
        this.players = players;
    }

    public List<Tile> getTiles() {
        return tiles;
    }

    public List<Player> getPlayers() {
        return players;
    }
}
