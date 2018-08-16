package com.mannanlive.settlers.boot.model;

import com.mannanlive.settlers.core.model.game.GameStage;

public class GameEventsMeta {
    private int version;
    private GameStage stage;
    private int player;

    public GameEventsMeta(int player, int version, GameStage stage) {
        this.player = player;
        this.version = version;
        this.stage = stage;
    }

    public int getVersion() {
        return version;
    }

    public GameStage getStage() {
        return stage;
    }

    public int getPlayer() {
        return player;
    }
}
