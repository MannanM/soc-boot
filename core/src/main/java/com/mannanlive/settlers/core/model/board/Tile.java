package com.mannanlive.settlers.core.model.board;

public class Tile {
    private TileType type;
    private int number;

    public Tile(TileType type, int number) {
        this.type = type;
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public TileType getType() {
        return type;
    }

    @Override
    public String toString() {
        return type + "(" + number + ')';
    }
}
