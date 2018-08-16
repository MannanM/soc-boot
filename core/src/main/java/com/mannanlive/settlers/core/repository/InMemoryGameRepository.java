package com.mannanlive.settlers.core.repository;

import com.mannanlive.settlers.core.factory.game.GameFactory;
import com.mannanlive.settlers.core.model.game.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class InMemoryGameRepository implements GameRepository {
    public Map<Long, Game> inMemory = new HashMap<>();

    @Autowired
    private GameFactory gameFactory;

    public Game get(long gameId) {
        if (!inMemory.containsKey(gameId)) {
            inMemory.put(gameId, gameFactory.createDefault(gameId));
        }
        return inMemory.get(gameId);
    }
}
