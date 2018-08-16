package com.mannanlive.settlers.core.service;

import com.mannanlive.settlers.core.factory.tile.RandomTileGeneratorService;
import com.mannanlive.settlers.core.model.board.Tile;
import org.junit.Test;

import java.util.List;

public class RandomTileGeneratorServiceTest {
    private RandomTileGeneratorService service = new RandomTileGeneratorService();

    @Test
    public void generate() {
        List<Tile> result = service.generate();
        System.out.println(result);
    }

}