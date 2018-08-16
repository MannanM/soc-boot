package com.mannanlive.settlers.core.service;

import com.mannanlive.settlers.core.model.Roll;

import java.util.concurrent.ThreadLocalRandom;

public class DiceService {
    public static final int SIDES = 6;

    public Roll roll() {
        return new Roll(dieRoll(), dieRoll());
    }

    public int dieRoll() {
        return ThreadLocalRandom.current().nextInt(1, SIDES + 1);
    }
}
