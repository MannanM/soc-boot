package com.mannanlive.settlers.core.factory.game;

import com.mannanlive.settlers.core.factory.tile.TileGenerationStrategies;
import com.mannanlive.settlers.core.factory.tile.TileGeneratorService;
import com.mannanlive.settlers.core.model.board.Board;
import com.mannanlive.settlers.core.model.board.TileType;
import com.mannanlive.settlers.core.model.game.Game;
import com.mannanlive.settlers.core.model.board.Tile;
import com.mannanlive.settlers.core.model.game.observer.CreateGameEvent;
import com.mannanlive.settlers.core.model.game.observer.EventGameObserver;
import com.mannanlive.settlers.core.model.player.Player;
import com.mannanlive.settlers.core.service.BoardFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class GameFactory {
    @Autowired
    private List<TileGeneratorService> tileGeneratorServices;

    @Autowired
    private EventGameObserver observer;

    private List<Player> ai = Arrays.asList(
            new Player(false, -1, "Priyanka"),
            new Player(false, -1, "Sarah"),
            new Player(false, -1, "Michael"));

    public Game createDefault(int size, List<Player> players, TileGenerationStrategies strategy) {
        List<Tile> tiles = generateTiles(strategy);
        Board board = new BoardFactory(tiles).create();

        for (int i = 0; i < tiles.size(); i++) {
            if (tiles.get(i).getType() == TileType.DESERT) {
                board.placeRobber(i);
            }
        }

        List<Player> combinedList = addAiAndShuffle(size, players);

        Game game = new Game(board, combinedList);
        game.addObserver(observer);
        game.nextStage(new CreateGameEvent(players.get(0)));
        return game;
    }

    private List<Tile> generateTiles(TileGenerationStrategies strategy) {
        for (TileGeneratorService service : tileGeneratorServices) {
            if (service.appliesTo(strategy)) {
                return service.generate();
            }
        }
        throw new IllegalArgumentException("Tile generation strategy '" + strategy + "' not found");
    }

    private List<Player> addAiAndShuffle(int size, List<Player> players) {
        List<Player> aiList = ai.subList(0, size - players.size());
        List<Player> combinedList = new ArrayList<>();
        combinedList.addAll(aiList);
        combinedList.addAll(players);
        Collections.shuffle(combinedList);
        return combinedList;
    }
}
