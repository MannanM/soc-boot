package com.mannanlive.settlers.boot.service.event;

import com.mannanlive.settlers.boot.model.GameEventsData;
import com.mannanlive.settlers.core.model.board.Connector;
import com.mannanlive.settlers.core.model.game.observer.GameEvent;
import com.mannanlive.settlers.core.model.game.observer.GameEventType;
import com.mannanlive.settlers.core.model.game.observer.RoadGameEvent;
import com.mannanlive.settlers.core.model.player.Player;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Order(100)
public class RoadEventTranslator implements EventTranslator {
    public boolean appliesTo(GameEventType eventType) {
        return eventType == GameEventType.BUILT_ROAD;
    }

    public GameEventsData translate(GameEvent gameEvent, int player, List<Player> players, Player requester) {
        Connector connector = ((RoadGameEvent) gameEvent).getConnector();
        GameEventsData data = new GameEventsData("@p" + player + " has built a road", player);
        data.setTileId(connector.getTileId());
        data.setPosition(connector.getConnectorId());
        data.setBuilding("ROAD");
        return data;
    }
}
