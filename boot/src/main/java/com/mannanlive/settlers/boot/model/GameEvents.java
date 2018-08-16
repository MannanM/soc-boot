package com.mannanlive.settlers.boot.model;
import java.util.List;

public class GameEvents {
    private GameEventsMeta meta;
    private List<GameEventsData> data;

    public GameEvents(GameEventsMeta meta, List<GameEventsData> data) {
        this.meta = meta;
        this.data = data;
    }

    public List<GameEventsData> getData() {
        return data;
    }

    public GameEventsMeta getMeta() {
        return meta;
    }
}
