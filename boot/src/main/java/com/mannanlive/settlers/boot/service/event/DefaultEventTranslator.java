package com.mannanlive.settlers.boot.service.event;

import com.mannanlive.settlers.boot.model.GameEventsData;
import com.mannanlive.settlers.core.model.game.observer.GameEvent;
import com.mannanlive.settlers.core.model.game.observer.GameEventType;
import com.mannanlive.settlers.core.model.player.Player;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.util.List;

@Order
@Service
public class DefaultEventTranslator implements EventTranslator {
    @Override
    public boolean appliesTo(GameEventType eventType) {
        return true;
    }

    @Override
    public GameEventsData translate(GameEvent gameEvent, int player, List<Player> players, Player requester) {
        return new GameEventsData(gameEvent.toString(), player);
    }
}
