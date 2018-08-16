package com.mannanlive.settlers.boot.service.event;

import com.mannanlive.settlers.boot.model.GameEventsData;
import com.mannanlive.settlers.core.model.board.TileType;
import com.mannanlive.settlers.core.model.game.observer.CollectResourcesEvent;
import com.mannanlive.settlers.core.model.game.observer.GameEvent;
import com.mannanlive.settlers.core.model.game.observer.GameEventType;
import com.mannanlive.settlers.core.model.player.Player;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Order(200)
public class CollectResourceEventTranslator implements EventTranslator {
    public boolean appliesTo(GameEventType eventType) {
        return eventType == GameEventType.COLLECT_RESOURCES;
    }

    public GameEventsData translate(GameEvent gameEvent, int player, List<Player> players, Player requester) {
        Map<TileType, Long> resources = ((CollectResourcesEvent) gameEvent).getResources();
        GameEventsData data = new GameEventsData("@p" + player + " has has collected " +
                getText(resources), player);
        Map<String, Long> actual = new HashMap<>();
        for (Map.Entry<TileType, Long> entry : resources.entrySet()) {
            actual.put(entry.getKey().name(), entry.getValue());
        }
        data.setResources(actual);
        return data;
    }

    //todo: make this better
    private String getText(Map<TileType, Long> tiles) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<TileType, Long> entry : tiles.entrySet()) {
            stringBuilder.append(entry.getValue()).append(" #").append(entry.getKey()).append(", ");
        }
        return stringBuilder.toString();
    }
}
