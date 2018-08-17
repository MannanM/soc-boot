package com.mannanlive.settlers.core.repository;

import com.mannanlive.settlers.core.model.game.Game;

import java.util.Map;

public interface GameRepository {
    Map<Long, Game> get();

    Game get(long gameId);

    void add(Game newGame);
}
