package com.mannanlive.settlers.core.model.game.observer;

import com.mannanlive.settlers.core.model.Roll;

public class DiceRollEvent extends GameEvent {
    private Roll roll;

    public DiceRollEvent(Roll node) {
        this.roll = node;
    }

    public Roll getConnector() {
        return roll;
    }

    public boolean isRobber() {
        return roll.getValue() == 7;
    }

    @Override
    public GameEventType getEvent() {
        return GameEventType.ROLL;
    }

    @Override
    public String toString() {
        return String.format("%s has rolled a %d! (%d, %d)",
                getPlayer().getName(), roll.getValue(), roll.getDie1(), roll.getDie2());
    }

    public Roll getRoll() {
        return roll;
    }
}
