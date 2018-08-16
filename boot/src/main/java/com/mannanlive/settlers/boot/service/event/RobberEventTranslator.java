package com.mannanlive.settlers.boot.service.event;

import com.mannanlive.settlers.boot.model.GameEventsData;
import com.mannanlive.settlers.core.model.game.GameTile;
import com.mannanlive.settlers.core.model.game.observer.GameEvent;
import com.mannanlive.settlers.core.model.game.observer.GameEventType;
import com.mannanlive.settlers.core.model.game.observer.RobberGameEvent;
import com.mannanlive.settlers.core.model.player.Player;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Order(400)
public class RobberEventTranslator implements EventTranslator {
    public boolean appliesTo(GameEventType eventType) {
        return eventType == GameEventType.PLACE_ROBBER;
    }

    public GameEventsData translate(GameEvent gameEvent, int player, List<Player> players, Player requester) {
        RobberGameEvent event = (RobberGameEvent) gameEvent;
        GameTile tile = event.getTile();
        GameEventsData data = new GameEventsData("@p" + player + " placed the robber on #" +
                tile.getTileType().name(), player);
        data.setTileId(event.getTileId());
        data.setBuilding("ROBBER");
        return data;
    }
}
