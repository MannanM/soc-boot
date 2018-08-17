package com.mannanlive.settlers.core.repository;

import com.mannanlive.settlers.core.factory.game.GameFactory;
import com.mannanlive.settlers.core.model.game.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class InMemoryGameRepository implements GameRepository {
    private Map<Long, Game> inMemory = new HashMap<>();

    @Override
    public Map<Long, Game> get() {
        return inMemory;
    }

    @Override
    public Game get(long gameId) {
        return inMemory.get(gameId);
    }

    @Override
    public void add(Game newGame) {
        inMemory.put(getNewId(), newGame);
    }

    private Long getNewId() {
        Long maxId = 1L;
        Optional<Long> max = inMemory.keySet().stream().reduce(Long::max);
        if (max.isPresent()) {
            maxId = max.get() + 1L;
        }
        return maxId;
    }
}
