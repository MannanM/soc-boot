package com.mannanlive.settlers.boot.service.event;

import com.mannanlive.settlers.boot.model.GameEventsData;
import com.mannanlive.settlers.core.model.board.TileType;
import com.mannanlive.settlers.core.model.game.observer.DiscardExcessResourcesEvent;
import com.mannanlive.settlers.core.model.game.observer.GameEvent;
import com.mannanlive.settlers.core.model.game.observer.GameEventType;
import com.mannanlive.settlers.core.model.game.observer.StealResourceEvent;
import com.mannanlive.settlers.core.model.player.Player;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Order(600)
public class DiscardResourceEventTranslator implements EventTranslator {
    public boolean appliesTo(GameEventType eventType) {
        return eventType == GameEventType.DISCARD_EXCESS_RESOURCES;
    }

    public GameEventsData translate(GameEvent gameEvent, int player, List<Player> players, Player requester) {
        DiscardExcessResourcesEvent event = (DiscardExcessResourcesEvent) gameEvent;

        GameEventsData data = new GameEventsData("@p" + player + " discarded " +
                + event.getDiscardedResources().size() + " #RESOURCE", player);

        data.setResources(createMap(event, requester));
        return data;
    }

    private Map<String, Long> createMap(DiscardExcessResourcesEvent event, Player requester) {
        HashMap<String, Long> map = new HashMap<>();
        if (event.getPlayer() == requester) {
            for (TileType type : event.getDiscardedResources()) {
                if (!map.containsKey(type.name())) {
                    map.put(type.name(), -1L);
                } else {
                    map.put(type.name(), map.get(type.name()) - 1L);
                }
            }
        } else {
            Integer size = event.getDiscardedResources().size() * -1;
            map.put("RESOURCE", size.longValue());
        }
        return map;
    }
}
