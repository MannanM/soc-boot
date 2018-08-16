package com.mannanlive.settlers.core.model.game.observer;

import com.mannanlive.settlers.core.model.game.Game;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

@Service
public class EventGameObserver implements Observer {
    private Map<Long, Map<Integer, GameEvent>> inMemory = new HashMap<>();

    @Override
    public void update(Observable o, Object arg) {
        Game game = (Game) o;
        GameEvent event = (GameEvent) arg;
        Map<Integer, GameEvent> events = safeGetGameEvents(game);
        events.put(event.getVersion(), event);
        System.out.println(arg);
    }

    public List<GameEvent> getEvents(long gameId, int from) {
        List<GameEvent> result = new ArrayList<>();
        Map<Integer, GameEvent> events = inMemory.get(gameId);
        for (Map.Entry<Integer, GameEvent> eventEntry : events.entrySet()) {
            if (eventEntry.getKey() > from) {
                result.add(eventEntry.getValue());
            }
        }
        return result;
    }

    private Map<Integer, GameEvent> safeGetGameEvents(Game game) {
        if (!inMemory.containsKey(game.getGameId())) {
            inMemory.put(game.getGameId(), new HashMap<>());
        }
        return inMemory.get(game.getGameId());
    }
}
