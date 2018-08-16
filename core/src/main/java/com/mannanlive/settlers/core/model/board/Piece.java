package com.mannanlive.settlers.core.model.board;

public class Piece {
    private int tileId;
    private int nodeId;
    private Building building;

    public Piece(int tileId, int nodeId, Building building) {
        this.tileId = tileId;
        this.nodeId = nodeId;
        this.building = building;
    }

    public int getTileId() {
        return tileId;
    }

    public void setTileId(int tileId) {
        this.tileId = tileId;
    }

    public int getNodeId() {
        return nodeId;
    }

    public void setNodeId(int nodeId) {
        this.nodeId = nodeId;
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }
}
