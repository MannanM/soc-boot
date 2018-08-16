package com.mannanlive.settlers.core.repository;

import com.mannanlive.settlers.core.model.game.Game;

public interface GameRepository {
    Game get(long gameId);
}
