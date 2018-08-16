package com.mannanlive.settlers.boot.model;

import com.mannanlive.settlers.core.model.game.Game;
import com.mannanlive.settlers.core.model.player.PlayerState;

import java.util.ArrayList;
import java.util.List;

public class GameStateData {
    private List<PlayerState> states;

    public GameStateData(List<PlayerState> states) {
        this.states = states;
    }

    public List<PlayerState> getStates() {
        return states;
    }
}
