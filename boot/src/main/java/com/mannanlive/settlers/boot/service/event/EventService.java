package com.mannanlive.settlers.boot.service.event;

import com.mannanlive.settlers.boot.model.GameEvents;
import com.mannanlive.settlers.boot.model.GameEventsData;
import com.mannanlive.settlers.boot.model.GameEventsMeta;
import com.mannanlive.settlers.core.model.game.Game;
import com.mannanlive.settlers.core.model.game.observer.EventGameObserver;
import com.mannanlive.settlers.core.model.game.observer.GameEvent;
import com.mannanlive.settlers.core.model.player.Player;
import com.mannanlive.settlers.core.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EventService {
    @Autowired
    private GameRepository repository;

    @Autowired
    private EventGameObserver eventGameObserver;

    @Autowired
    private List<EventTranslator> eventTranslators;

    public GameEvents getEvents(int gameId, int playerId, int version) {
        Game game = repository.get(gameId);
        int player = game.getPlayers().indexOf(game.getCurrentPlayer());
        GameEventsMeta meta = new GameEventsMeta(player, game.getVersion(), game.getStage());

        Player requester = game.getPlayer(playerId);
        List<GameEvent> events = eventGameObserver.getEvents(gameId, version);

        List<GameEventsData> list = new ArrayList<>();
        for (GameEvent event : events) {
            int eventPlayer = game.getPlayers().indexOf(event.getPlayer());
            for (EventTranslator translator : eventTranslators) {
                if (translator.appliesTo(event.getEvent())) {
                    list.add(translator.translate(event, eventPlayer, game.getPlayers(), requester));
                    break;
                }
            }
        }
        return new GameEvents(meta, list);
    }
}
