package com.mannanlive.settlers.boot.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mannanlive.settlers.core.model.board.TileType;

import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class GameEventsData {
    private String message;
    private Integer tileId;
    private Integer position;
    private Integer playerId;
    private Integer otherPlayerId;
    private String building;
    private Map<String, Long> resources;
    private Map<String, Long> otherResources;

    public GameEventsData(String message, Integer playerId) {
        this.message = message;
        this.playerId = playerId;
    }

    public GameEventsData(String message, Integer tileId, Integer position, Integer playerId, String building) {
        this.message = message;
        this.tileId = tileId;
        this.position = position;
        this.playerId = playerId;
        this.building = building;
    }

    public void setTileId(Integer tileId) {
        this.tileId = tileId;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public Integer getTileId() {
        return tileId;
    }

    public Integer getPosition() {
        return position;
    }

    public String getBuilding() {
        return building;
    }

    public Integer getPlayerId() {
        return playerId;
    }

    public void setResources(Map<String, Long> resources) {
        this.resources = resources;
    }

    public Map<String, Long> getResources() {
        return resources;
    }

    public Integer getOtherPlayerId() {
        return otherPlayerId;
    }

    public void setOtherPlayerId(Integer otherPlayerId) {
        this.otherPlayerId = otherPlayerId;
    }

    public Map<String, Long> getOtherResources() {
        return otherResources;
    }

    public void setOtherResources(Map<String, Long> otherResources) {
        this.otherResources = otherResources;
    }
}
