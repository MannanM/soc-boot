package com.mannanlive.settlers.core.model.game.observer;

import com.mannanlive.settlers.core.model.board.BuildActions;
import com.mannanlive.settlers.core.model.game.GameStage;

public class BuildEvent extends GameEvent {
    private BuildActions action;

    public BuildEvent() {
    }

    public BuildEvent(BuildActions action) {
        this.action = action;
    }

    public GameStage getStage() {
        return action == null ? GameStage.ROLL : action.getStage();
    }

    public BuildActions getAction() {
        return action;
    }

    @Override
    public GameEventType getEvent() {
        return GameEventType.BUILD_STAGE;
    }

    @Override
    public String toString() {
        return getPlayer().getName() + " " + (action == null ? "ended their turn" : "purchased a " + action.name());
    }
}
