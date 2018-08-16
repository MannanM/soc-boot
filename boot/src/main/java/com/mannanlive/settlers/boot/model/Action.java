package com.mannanlive.settlers.boot.model;

public class Action {
    private Integer tile;
    private Integer node;
    private Integer connector;
    private ActionType action;

    public Integer getTile() {
        return tile;
    }

    public void setTile(Integer tile) {
        this.tile = tile;
    }

    public Integer getNode() {
        return node;
    }

    public void setNode(Integer node) {
        this.node = node;
    }

    public Integer getConnector() {
        return connector;
    }

    public void setConnector(Integer connector) {
        this.connector = connector;
    }

    public ActionType getAction() {
        return action;
    }

    public void setAction(ActionType action) {
        this.action = action;
    }

    @Override
    public String toString() {
        return "Action{" +
                "tile=" + tile +
                ", node=" + node +
                ", connector=" + connector +
                ", action=" + action +
                '}';
    }
}
