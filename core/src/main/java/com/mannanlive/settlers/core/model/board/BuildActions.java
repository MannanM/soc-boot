package com.mannanlive.settlers.core.model.board;

import java.util.HashMap;
import java.util.Map;

import static com.mannanlive.settlers.core.model.board.TileType.*;

public enum BuildActions {
    ROAD(1, TREE, 1, MUD),
    SETTLEMENT(1, MUD, 1, TREE, 1, WHEAT, 1, SHEEP),
    CITY(2, WHEAT, 3, IRON),
    DEVELOPMENT_CARD(1, SHEEP, 1, WHEAT, 1, IRON);

    private final Map<TileType, Long> resources = new HashMap<>();

    BuildActions(Object... params) {
        for (int i = 0; i < params.length - 1; i=i+2) {
            Integer cost = (Integer) params[i];
            TileType type = (TileType) params[i+1];
            resources.put(type, cost.longValue() * -1);
        }
    }

    public Map<TileType, Long> getResources() {
        return resources;
    }
}
