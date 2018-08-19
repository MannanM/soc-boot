package com.mannanlive.settlers.boot.service.event;

import com.mannanlive.settlers.boot.model.GameEventsData;
import com.mannanlive.settlers.core.model.board.BuildActions;
import com.mannanlive.settlers.core.model.board.TileType;
import com.mannanlive.settlers.core.model.game.observer.BuildEvent;
import com.mannanlive.settlers.core.model.game.observer.GameEvent;
import com.mannanlive.settlers.core.model.game.observer.GameEventType;
import com.mannanlive.settlers.core.model.player.Player;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.String.format;

@Service
@Order(600)
public class BuildEventTranslator implements EventTranslator {
    public boolean appliesTo(GameEventType eventType) {
        return eventType == GameEventType.BUILD_STAGE;
    }

    public GameEventsData translate(GameEvent gameEvent, int player, List<Player> players, Player requester) {
        BuildActions action = ((BuildEvent) gameEvent).getAction();
        GameEventsData data = new GameEventsData(getText(player, action), player);
        if (action != null) {
            data.setResources(convert(action));
        }
        return data;
    }

    private Map<String, Long> convert(BuildActions actions) {
        Map<String, Long> result = new HashMap<>();
        for (Map.Entry<TileType, Long> entry : actions.getResources().entrySet()) {
            result.put(entry.getKey().name(), entry.getValue());
        }
        return result;
    }

    private String getText(int player, BuildActions action) {
        if (action == null) {
            return format("@p%d ended their turn", player);
        }
        return format("@p%d bought a %s", player, action.name().toLowerCase());
    }

}
