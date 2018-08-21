package com.mannanlive.settlers.core.model.board;

import com.mannanlive.settlers.core.model.game.GameStage;

import java.util.HashMap;
import java.util.Map;

import static com.mannanlive.settlers.core.model.board.TileType.*;

public enum BuildActions {
    ROAD(GameStage.BUILD_ROAD, 1, TREE, 1, MUD),
    SETTLEMENT(GameStage.BUILD_SETTLEMENT, 1, MUD, 1, TREE, 1, WHEAT, 1, SHEEP),
    CITY(GameStage.BUILD_ROAD, 2, WHEAT, 3, IRON),
    DEVELOPMENT_CARD(GameStage.BUILD_ROAD, 1, SHEEP, 1, WHEAT, 1, IRON);

    private final Map<TileType, Long> resources = new HashMap<>();
    private final GameStage stage;

    BuildActions(GameStage stage, Object... params) {
        for (int i = 0; i < params.length - 1; i=i+2) {
            Integer cost = (Integer) params[i];
            TileType type = (TileType) params[i+1];
            resources.put(type, cost.longValue() * -1);
        }
        this.stage = stage;
    }

    public Map<TileType, Long> getResources() {
        return resources;
    }

    public GameStage getStage() {
        return stage;
    }
}
