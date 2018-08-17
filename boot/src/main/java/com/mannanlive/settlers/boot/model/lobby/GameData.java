package com.mannanlive.settlers.boot.model.lobby;

import java.util.List;

public class GameData {
    private Long id;
    private String name;
    private List<Long> playerIds;

    public GameData(Long id, String name, List<Long> playerIds) {
        this.id = id;
        this.name = name;
        this.playerIds = playerIds;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Long> getPlayerIds() {
        return playerIds;
    }
}
