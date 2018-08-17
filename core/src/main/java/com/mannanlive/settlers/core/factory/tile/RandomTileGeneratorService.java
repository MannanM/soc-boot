package com.mannanlive.settlers.core.factory.tile;

import com.mannanlive.settlers.core.model.board.Tile;
import com.mannanlive.settlers.core.model.board.TileType;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;

@Service
public class RandomTileGeneratorService implements TileGeneratorService {
    private List<TileType> types = new ArrayList<>();
    private List<Integer> numbers = asList(2, 3, 3, 4, 4, 5, 5, 6, 6, 8, 8, 9, 9, 10, 10, 11, 11, 12);

    public RandomTileGeneratorService() {
        addCommonTypes();
    }

    @Override
    public List<Tile> generate() {
        //todo: copy these to new array
        Collections.shuffle(types);
        Collections.shuffle(numbers);
        List<Tile> result = new ArrayList<>(types.size());
        int numberIndex = 0;
        for (TileType tileType : types) {
            if (tileType.equals(TileType.DESERT)) {
                Tile tile = new Tile(tileType, 7);
                result.add(tile);
            } else {
                int number = numbers.get(numberIndex++);
                Tile tile = new Tile(tileType, number);
                result.add(tile);
            }
        }
        return result;
    }

    @Override
    public boolean appliesTo(TileGenerationStrategies strategy) {
        return strategy == TileGenerationStrategies.RANDOM;
    }

    private void addCommonTypes() {
        addNumberOfTiles(TileType.SHEEP, 4);
        addNumberOfTiles(TileType.WHEAT, 4);
        addNumberOfTiles(TileType.TREE, 4);
        addNumberOfTiles(TileType.MUD, 3);
        addNumberOfTiles(TileType.IRON, 3);
        addNumberOfTiles(TileType.DESERT, 1);
    }

    private void addNumberOfTiles(TileType type, int number) {
        for (int i = 0; i < number; i++) {
            types.add(type);
        }
    }
}
