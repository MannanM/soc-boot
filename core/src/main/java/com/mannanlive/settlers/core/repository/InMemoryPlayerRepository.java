package com.mannanlive.settlers.core.repository;

import com.mannanlive.settlers.core.model.player.Player;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class InMemoryPlayerRepository implements PlayerRepository {
    private Map<Long, Player> inMemory = new HashMap<>();

    @Override
    public Collection<Player> get() {
        return inMemory.values();
    }

    @Override
    public Player add(String name) {
        validateUniqueNames(name);
        Long maxId = getNewId();
        Player newPlayer = new Player(true, maxId, name);
        inMemory.put(maxId, newPlayer);
        return newPlayer;
    }

    @Override
    public Player get(Long playerId) {
        return inMemory.get(playerId);
    }

    private Long getNewId() {
        Long maxId = 1L;
        Optional<Long> max = inMemory.keySet().stream().reduce(Long::max);
        if (max.isPresent()) {
            maxId = max.get() + 1L;
        }
        return maxId;
    }

    private void validateUniqueNames(String name) {
        for (Player player : inMemory.values()) {
            if (player.getName().equalsIgnoreCase(name)) {
                throw new RuntimeException(String.format("Name '%s' is already taken.", name));
            }
        }
    }
}
