package com.mannanlive.settlers.core.model.game;

import com.mannanlive.settlers.core.model.board.Board;
import com.mannanlive.settlers.core.model.board.TileType;
import com.mannanlive.settlers.core.model.exception.player.InvalidActionException;
import com.mannanlive.settlers.core.model.exception.player.NotPlayersTurnException;
import com.mannanlive.settlers.core.model.exception.player.PlayerNotFoundException;
import com.mannanlive.settlers.core.model.game.observer.CollectResourcesEvent;
import com.mannanlive.settlers.core.model.game.observer.DiceRollEvent;
import com.mannanlive.settlers.core.model.game.observer.GameEvent;
import com.mannanlive.settlers.core.model.game.observer.GameEventType;
import com.mannanlive.settlers.core.model.game.observer.RoadGameEvent;
import com.mannanlive.settlers.core.model.game.observer.SettlementGameEvent;
import com.mannanlive.settlers.core.model.game.observer.StealResourceEvent;
import com.mannanlive.settlers.core.model.player.Player;
import com.mannanlive.settlers.core.service.AiService;
import com.mannanlive.settlers.core.service.DiceService;
import com.mannanlive.settlers.core.service.ResourceService;

import java.util.List;
import java.util.Observable;

import static com.mannanlive.settlers.core.model.game.GameStage.*;
import static java.util.Arrays.asList;

public class Game extends Observable {
    private final Board board;
    private final List<Player> players;
    private GameStage stage = SETUP;
    private Player currentPlayer;
    private Player triggeringPlayer;
    private int version = 0;

    private DiceService dice = new DiceService();
    private AiService ai = new AiService();
    private ResourceService resourceService;

    public Game(Board board, List<Player> players) {
        this.board = board;
        this.players = players;
        currentPlayer = players.get(0);
        resourceService = new ResourceService(players);
    }

    public void nextStage(GameEvent... events) {
        for (GameEvent event : events) {
            broadcastEvent(event);
        }

        switch (stage) {
            case SETUP:
                stage = SETUP_FIRST_SETTLEMENT;
                break;
            case SETUP_FIRST_SETTLEMENT:
                stage = SETUP_FIRST_ROAD;
                break;
            case SETUP_FIRST_ROAD:
                int index = players.indexOf(currentPlayer);
                if (index + 1 < players.size()) {
                    currentPlayer = players.get(index + 1);
                    stage = SETUP_FIRST_SETTLEMENT;
                } else {
                    stage = SETUP_SECOND_SETTLEMENT;
                }
                break;
            case SETUP_SECOND_SETTLEMENT:
                stage = SETUP_SECOND_ROAD;
                break;
            case SETUP_SECOND_ROAD:
                int index2 = players.indexOf(currentPlayer);
                if (index2 > 0) {
                    currentPlayer = players.get(index2 - 1);
                    stage = SETUP_SECOND_SETTLEMENT;
                } else {
                    stage = ROLL;
                }
                break;
            case ROLL:
                GameEvent lastEvent = events[events.length - 1];
                if (lastEvent.getEvent() == GameEventType.ROLL && ((DiceRollEvent)lastEvent).isRobber()) {
                    stage = PLACE_ROBBER;
                } else {
                    currentPlayer = players.get((players.indexOf(currentPlayer) + 1) % players.size());
                }
                break;
            case PLACE_ROBBER:
                stage = STEAL_RESOURCE;
                break;
            case STEAL_RESOURCE:
                stage = DISCARD_EXCESS_CARDS;
                triggeringPlayer = currentPlayer;
                break;
            case DISCARD_EXCESS_CARDS:
                currentPlayer = players.get((players.indexOf(currentPlayer) + 1) % players.size());
                if (triggeringPlayer == currentPlayer) {
                    stage = ROLL;
                    //remove this if you want to stay on triggered player
                    currentPlayer = players.get((players.indexOf(currentPlayer) + 1) % players.size());
                }
                break;
            default:
                throw new IllegalArgumentException("Error: " + stage);
        }
        processStep();
    }

    private void broadcastEvent(GameEvent event) {
        event.setPlayerIfNull(currentPlayer);
        event.setVersion(version++);
        setChanged();
        this.notifyObservers(event);
    }

    private void processStep() {
        if (stage == STEAL_RESOURCE) {
            if (board.getRobberPlayers(currentPlayer).isEmpty()) {
                nextStage(new StealResourceEvent());
                return;
            }
        }
        if (stage == DISCARD_EXCESS_CARDS) {
            if (resourceService.hasExcessResources(currentPlayer)) {
                ai.discardExcessCards(currentPlayer, this);
                return;
            } else {
                nextStage();
                return;
            }
        }
        if (currentPlayer.isHuman()) {
            return;
        }
        switch (stage) {
            case SETUP_SECOND_SETTLEMENT:
            case SETUP_FIRST_SETTLEMENT:
                ai.buildSetupSettlement(currentPlayer, this);
                break;
            case SETUP_FIRST_ROAD:
            case SETUP_SECOND_ROAD:
                ai.buildSetupRoad(currentPlayer, this);
                break;
            case ROLL:
                roll(currentPlayer);
                break;
            case PLACE_ROBBER:
                ai.placeRobber(currentPlayer, this);
                break;
            case STEAL_RESOURCE:
                ai.stealResource(currentPlayer, this);
                break;
            case DISCARD_EXCESS_CARDS:
                ai.discardExcessCards(currentPlayer, this);
                break;
        }
    }

    public void buildOnNode(Player player, int tileId, int nodeId) {
        checkStageAndPlayer(player, SETUP_FIRST_SETTLEMENT, SETUP_SECOND_SETTLEMENT);
        SettlementGameEvent event = board.buildSettlement(player, tileId, nodeId);
        if (stage == SETUP_SECOND_SETTLEMENT) {
            broadcastEvent(event);
            CollectResourcesEvent resourcesEvent = resourceService.collectResources(event.getNode());
            nextStage(resourcesEvent);
        } else {
            nextStage(event);
        }
    }

    public void buildOnConnector(Player player, int tileId, int connectorId) {
        checkStageAndPlayer(player, SETUP_FIRST_ROAD, SETUP_SECOND_ROAD);
        RoadGameEvent event = board.buildRoad(player, tileId, connectorId);
        nextStage(event);
    }

    public void roll(Player player) {
        checkStageAndPlayer(player, ROLL);
        nextStage(resourceService.collectResources(board, dice.roll()));
    }

    public void placeRobber(Player player, int tileId) {
        checkStageAndPlayer(player, PLACE_ROBBER);
        nextStage(board.placeRobber(tileId));
    }

    public void stealResource(Player player, Player otherPlayer) {
        checkStageAndPlayer(player, STEAL_RESOURCE);
        List<Player> validPlayersToRob = board.getRobberPlayers(player);
        nextStage(resourceService.stealResource(validPlayersToRob, otherPlayer, player));
    }

    public void discardResources(Player player, List<TileType> discardList) {
        checkStageAndPlayer(player, DISCARD_EXCESS_CARDS);
        nextStage(resourceService.discardResources(player, discardList));
    }

    private void checkStageAndPlayer(Player player, GameStage... stages) {
        if (!asList(stages).contains(stage)) {
            throw new InvalidActionException(stage);
        }
        if (currentPlayer != player) {
            throw new NotPlayersTurnException(currentPlayer, player);
        }
    }

    public Player getPlayer(long player) {
        //todo: check not ai player
        for (Player possiblePlayer : players) {
            if (possiblePlayer.getId() == player) {
                return possiblePlayer;
            }
        }
        throw new PlayerNotFoundException(player);
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Board getBoard() {
        return board;
    }

    public GameStage getStage() {
        return stage;
    }

    public int getVersion() {
        return version;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public ResourceService getResourceService() {
        return resourceService;
    }
}
