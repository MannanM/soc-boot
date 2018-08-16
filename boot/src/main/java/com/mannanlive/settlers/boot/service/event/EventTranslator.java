package com.mannanlive.settlers.boot.service.event;

import com.mannanlive.settlers.boot.model.GameEventsData;
import com.mannanlive.settlers.core.model.game.observer.GameEvent;
import com.mannanlive.settlers.core.model.game.observer.GameEventType;
import com.mannanlive.settlers.core.model.player.Player;

import java.util.List;

public interface EventTranslator {
    boolean appliesTo(GameEventType eventType);

    GameEventsData translate(GameEvent gameEvent, int player, List<Player> players, Player requester);
}
