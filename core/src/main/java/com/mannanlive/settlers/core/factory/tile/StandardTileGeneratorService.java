package com.mannanlive.settlers.core.factory.tile;

import com.mannanlive.settlers.core.model.board.TileType;
import com.mannanlive.settlers.core.model.board.Tile;

import java.util.Arrays;
import java.util.List;

public class StandardTileGeneratorService implements TileGeneratorService {
    public List<Tile> generate() {
        return Arrays.asList(
                new Tile(TileType.IRON, 10),
                new Tile(TileType.SHEEP, 2),
                new Tile(TileType.TREE, 9),
                new Tile(TileType.WHEAT, 12),
                new Tile(TileType.MUD, 6),
                new Tile(TileType.SHEEP, 4),
                new Tile(TileType.MUD, 10),
                new Tile(TileType.WHEAT, 9),
                new Tile(TileType.TREE, 11),
                new Tile(TileType.DESERT, 7),
                new Tile(TileType.TREE, 3),
                new Tile(TileType.IRON, 8),
                new Tile(TileType.TREE, 8),
                new Tile(TileType.IRON, 3),
                new Tile(TileType.WHEAT, 4),
                new Tile(TileType.SHEEP, 5),
                new Tile(TileType.MUD, 5),
                new Tile(TileType.WHEAT, 6),
                new Tile(TileType.SHEEP, 11)
        );
    }
}
