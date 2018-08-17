package com.mannanlive.settlers.boot.controller;

import com.mannanlive.settlers.boot.model.lobby.CreateGameRequest;
import com.mannanlive.settlers.boot.model.lobby.Games;
import com.mannanlive.settlers.boot.model.lobby.Players;
import com.mannanlive.settlers.core.factory.game.GameFactory;
import com.mannanlive.settlers.core.model.game.Game;
import com.mannanlive.settlers.core.model.player.Player;
import com.mannanlive.settlers.core.repository.GameRepository;
import com.mannanlive.settlers.core.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class LobbyController {
    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private GameFactory gameFactory;

    @RequestMapping("/v1/players")
    public Players getAllPlayers() {
        return new Players(playerRepository.get());
    }

    @RequestMapping(value = "/v1/players/{name}", method = RequestMethod.POST)
    public Player addPlayer(@PathVariable String name) {
        return playerRepository.add(name);
    }

    @RequestMapping("/v1/games")
    public Games getAllGames() {
        return new Games(gameRepository.get());
    }

    @RequestMapping(value = "/v1/games", method = RequestMethod.POST)
    public void addGame(@RequestBody CreateGameRequest data) {
        List<Player> players =  data.getPlayerIds().stream()
                .distinct()
                .map(id -> playerRepository.get(id))
                .collect(Collectors.toList());
        Game newGame = gameFactory.createDefault(data.getSize(), players, data.getTileStrategy());
        gameRepository.add(newGame);
    }
}