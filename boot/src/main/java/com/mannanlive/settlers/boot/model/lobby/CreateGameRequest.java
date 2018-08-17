package com.mannanlive.settlers.boot.model.lobby;

import com.mannanlive.settlers.core.factory.tile.TileGenerationStrategies;

import java.util.List;

public class CreateGameRequest {
    private List<Long> playerIds;
    private int size;
    private TileGenerationStrategies tileStrategy;

    public List<Long> getPlayerIds() {
        return playerIds;
    }

    public void setPlayerIds(List<Long> playerIds) {
        this.playerIds = playerIds;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public TileGenerationStrategies getTileStrategy() {
        return tileStrategy;
    }

    public void setTileStrategy(TileGenerationStrategies tileStrategy) {
        this.tileStrategy = tileStrategy;
    }
}
