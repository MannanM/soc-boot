package com.mannanlive.settlers.core.factory.tile;

import com.mannanlive.settlers.core.model.board.Tile;

import java.util.List;

public interface TileGeneratorService {
    List<Tile> generate();
    boolean appliesTo(TileGenerationStrategies strategy);
}
