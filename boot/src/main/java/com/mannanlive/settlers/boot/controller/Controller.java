package com.mannanlive.settlers.boot.controller;

import com.mannanlive.settlers.boot.model.Action;
import com.mannanlive.settlers.boot.model.ActionType;
import com.mannanlive.settlers.boot.model.GameDetails;
import com.mannanlive.settlers.boot.model.GameEvents;
import com.mannanlive.settlers.boot.model.GameState;
import com.mannanlive.settlers.boot.service.event.EventService;
import com.mannanlive.settlers.core.model.game.Game;
import com.mannanlive.settlers.core.model.game.GameStage;
import com.mannanlive.settlers.core.model.player.Player;
import com.mannanlive.settlers.core.model.player.PlayerState;
import com.mannanlive.settlers.core.repository.InMemoryGameRepository;
import com.mannanlive.settlers.core.service.GameStateExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RestController
public class Controller {
    @Autowired
    private InMemoryGameRepository repository;

    @Autowired
    private EventService eventService;

    private GameStateExtractor extractor = new GameStateExtractor();

    @RequestMapping("/v1/games/{gameId}/state")
    public GameDetails index(@PathVariable long gameId) {
        Game game = repository.get(gameId);
        return new GameDetails(extractor.getTiles(game), game.getPlayers());
    }

    @RequestMapping(path="/v1/games/{gameId}/players/{playerId}/state")
    public GameState state(@PathVariable int gameId, @PathVariable int playerId) {
        Game game = repository.get(gameId);
        List<PlayerState> state = extractor.getState(game);
        return new GameState(state);
    }

    @RequestMapping(path="/v1/games/{gameId}/players/{playerId}/events")
    public GameEvents events(@PathVariable int gameId, @PathVariable int playerId, @RequestParam int version) {
        return eventService.getEvents(gameId, playerId, version);
    }

    @RequestMapping(method = RequestMethod.POST,
            path="/v1/games/{gameId}/players/{playerId}/action")
    public void action(@PathVariable int gameId, @PathVariable int playerId, @RequestBody Action data) {
        System.out.println(data);
        Game game = repository.get(gameId);
        Player player = game.getPlayer(playerId);

        if (data.getNode() != null) {
            if (game.getStage() == GameStage.STEAL_RESOURCE) {
                Player otherPlayer = game.getBoard().getGameTiles().get(data.getTile()).getNode(data.getNode()).getOwner();
                game.stealResource(player, otherPlayer);
            } else {
                game.buildOnNode(player, data.getTile(), data.getNode());
            }
        } else if (data.getConnector() != null) {
            game.buildOnConnector(player, data.getTile(), data.getConnector());
        } else if (data.getTile() != null) {
            game.placeRobber(player, data.getTile());
        } else {
            processAction(game, player, data.getAction());
        }
    }

    private void processAction(Game game, Player player, ActionType action) {
        switch (action) {
            case ROLL:
                game.roll(player);
                break;
            case END:
                game.endBuildStage(player);
                break;
            case ROAD:
                game.buildRoad(player);
                break;
            default:
                System.out.println(action);
        }
    }
}