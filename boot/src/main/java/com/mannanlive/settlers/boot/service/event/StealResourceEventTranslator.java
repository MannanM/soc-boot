package com.mannanlive.settlers.boot.service.event;

import com.mannanlive.settlers.boot.model.GameEventsData;
import com.mannanlive.settlers.core.model.board.TileType;
import com.mannanlive.settlers.core.model.game.observer.GameEvent;
import com.mannanlive.settlers.core.model.game.observer.GameEventType;
import com.mannanlive.settlers.core.model.game.observer.StealResourceEvent;
import com.mannanlive.settlers.core.model.player.Player;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
@Order(500)
public class StealResourceEventTranslator implements EventTranslator {
    public boolean appliesTo(GameEventType eventType) {
        return eventType == GameEventType.STEAL_RESOURCE;
    }

    public GameEventsData translate(GameEvent gameEvent, int player, List<Player> players, Player requester) {
        StealResourceEvent event = (StealResourceEvent) gameEvent;
        int otherPlayerId = players.indexOf(event.getRobbedPlayer());

        GameEventsData data = new GameEventsData("@p" + player + " " +
                getMessage(event, otherPlayerId, requester),
                player);

        if (event.getRobbedPlayer() != null) {
            data.setOtherPlayerId(otherPlayerId);
            if (event.getResource() != null) {
                data.setResources(createMap(event, requester, 1));
                data.setOtherResources(createMap(event, requester, -1));
            }
        }
        return data;
    }

    private String getMessage(StealResourceEvent event, int otherPlayerId, Player requester) {
        if (event.getRobbedPlayer() == null) {
            return "could not steal any resources";
        }

        if (event.getResource() == null) {
            return "tried to steal from @p" + otherPlayerId + " but they had nothing";
        }

        if (isPlayerInvolved(event, requester)) {
            return "stole #" + event.getResource() + " from @p" + otherPlayerId;
        }
        return "stole #RESOURCE from @p" + otherPlayerId;
    }

    private boolean isPlayerInvolved(StealResourceEvent event, Player requester) {
        return event.getPlayer() == requester || event.getRobbedPlayer() == requester;
    }

    private HashMap<String, Long> createMap(StealResourceEvent event, Player requester, int modifier) {
        HashMap<String, Long> map = new HashMap<>();
        if (isPlayerInvolved(event, requester)) {
            map.put(event.getResource().name(), event.getAmount() * modifier);
        } else {
            map.put("RESOURCE", event.getAmount() * modifier);
        }
        return map;
    }
}
