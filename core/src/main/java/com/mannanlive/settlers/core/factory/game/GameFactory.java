package com.mannanlive.settlers.core.factory.game;

import com.mannanlive.settlers.core.factory.tile.RandomTileGeneratorService;
import com.mannanlive.settlers.core.model.board.Board;
import com.mannanlive.settlers.core.model.board.TileType;
import com.mannanlive.settlers.core.model.game.Game;
import com.mannanlive.settlers.core.model.game.GameTile;
import com.mannanlive.settlers.core.model.board.Tile;
import com.mannanlive.settlers.core.model.game.observer.CreateGameEvent;
import com.mannanlive.settlers.core.model.game.observer.EventGameObserver;
import com.mannanlive.settlers.core.model.player.Player;
import com.mannanlive.settlers.core.service.BoardFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class GameFactory {
    @Autowired
    private RandomTileGeneratorService random;

    @Autowired
    private EventGameObserver observer;

    public Game createDefault(long gameId) {
        List<Tile> tiles = random.generate();
        Board board = new BoardFactory(tiles).create();

        for (int i = 0; i < tiles.size(); i++) {
            if (tiles.get(i).getType() == TileType.DESERT) {
                board.placeRobber(i);
            }
        }

        Player human = new Player(true, 200, "Human");
        List<Player> players = Arrays.asList(human,
                new Player(false, -1, "Sarah"),
                new Player(false, -1, "Michael"),
                new Player(false, -1, "Priyanka"));
        Collections.shuffle(players);
        Game game = new Game(gameId, board, players);
        game.addObserver(observer);
        game.nextStage(new CreateGameEvent(human));
        return game;
    }
}
