package com.mannanlive.settlers.core.model.exception;

public class RobberMustBeMovedException extends GameException {
    public RobberMustBeMovedException() {
        super("You must place the robber on a new tile.");
    }
}
