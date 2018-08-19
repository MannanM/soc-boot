package com.mannanlive.settlers.core.model.game.observer;

import com.mannanlive.settlers.core.model.board.BuildActions;
import com.mannanlive.settlers.core.model.game.GameStage;

public class BuildEvent extends GameEvent {
    private GameStage stage;

    public BuildEvent() {
        stage = GameStage.ROLL;
    }

    public BuildEvent(GameStage action) {
        this.stage = action;
    }

    public GameStage getStage() {
        return stage;
    }

    public BuildActions getAction() {
        switch (stage) {
            case BUILD_ROAD:
                return BuildActions.ROAD;
            default:
                return null;
        }
    }

    @Override
    public GameEventType getEvent() {
        return GameEventType.BUILD_STAGE;
    }

    @Override
    public String toString() {
        return getPlayer().getName() + " finished their build stage";
    }
}
