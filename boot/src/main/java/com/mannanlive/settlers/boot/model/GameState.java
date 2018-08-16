package com.mannanlive.settlers.boot.model;

import com.mannanlive.settlers.core.model.game.Game;
import com.mannanlive.settlers.core.model.player.PlayerState;

import java.util.List;

public class GameState {
    private GameStateData data;

    public GameState(List<PlayerState> game) {
        data = new GameStateData(game);
    }

    public GameStateData getData() {
        return data;
    }
}
