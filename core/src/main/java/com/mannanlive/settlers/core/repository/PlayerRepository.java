package com.mannanlive.settlers.core.repository;

import com.mannanlive.settlers.core.model.player.Player;

import java.util.Collection;

public interface PlayerRepository {
    Collection<Player> get();

    Player add(String name);

    Player get(Long playerId);
}
