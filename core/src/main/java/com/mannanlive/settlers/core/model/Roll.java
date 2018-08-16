package com.mannanlive.settlers.core.model;

public class Roll {
    private final int die1;
    private final int die2;

    public Roll(int die1, int die2) {
        this.die1 = die1;
        this.die2 = die2;
    }

    public int getValue() {
        return die1 + die2;
    }

    public int getDie1() {
        return die1;
    }

    public int getDie2() {
        return die2;
    }
}
