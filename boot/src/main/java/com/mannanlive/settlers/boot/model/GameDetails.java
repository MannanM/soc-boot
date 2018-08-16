package com.mannanlive.settlers.boot.model;

import com.mannanlive.settlers.core.model.board.Tile;
import com.mannanlive.settlers.core.model.player.Player;

import java.util.List;

public class GameDetails {
    private GameDetailsData data;

    public GameDetails(List<Tile> tiles, List<Player> players) {
        this.data = new GameDetailsData(tiles, players);
    }

    public GameDetailsData getData() {
        return data;
    }

    public void setData(GameDetailsData data) {
        this.data = data;
    }
}
