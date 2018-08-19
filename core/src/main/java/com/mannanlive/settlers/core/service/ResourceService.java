package com.mannanlive.settlers.core.service;

import com.mannanlive.settlers.core.model.Roll;
import com.mannanlive.settlers.core.model.board.Board;
import com.mannanlive.settlers.core.model.board.BuildActions;
import com.mannanlive.settlers.core.model.board.Building;
import com.mannanlive.settlers.core.model.board.Node;
import com.mannanlive.settlers.core.model.board.Tile;
import com.mannanlive.settlers.core.model.board.TileType;
import com.mannanlive.settlers.core.model.exception.GameException;
import com.mannanlive.settlers.core.model.exception.IncorrectDiscardSizeException;
import com.mannanlive.settlers.core.model.exception.NotEnoughResourceException;
import com.mannanlive.settlers.core.model.game.GameTile;
import com.mannanlive.settlers.core.model.game.observer.CollectResourcesEvent;
import com.mannanlive.settlers.core.model.game.observer.DiceRollEvent;
import com.mannanlive.settlers.core.model.game.observer.DiscardExcessResourcesEvent;
import com.mannanlive.settlers.core.model.game.observer.GameEvent;
import com.mannanlive.settlers.core.model.game.observer.StealResourceEvent;
import com.mannanlive.settlers.core.model.player.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingLong;
import static java.util.stream.Collectors.toList;

public class ResourceService {
    private static final List<TileType> RESOURCES =
            Arrays.asList(TileType.IRON, TileType.MUD, TileType.SHEEP, TileType.TREE, TileType.WHEAT);
    private static final int EXCESS_RESOURCES = 7;
    private Map<Player, Map<TileType, Long>> resources = new HashMap<>();

    public ResourceService(List<Player> players) {
        for (Player player : players) {
            HashMap<TileType, Long> value = new HashMap<>();
            for (TileType type : RESOURCES) {
                value.put(type, 0L);
            }
            resources.put(player, value);
        }
    }

    public CollectResourcesEvent collectResources(Node node) {
        Map<TileType, Long> newResources =
                node.getAdjacentTiles().stream()
                        .map(Tile::getType)
                        .filter(RESOURCES::contains)
                        .collect(groupingBy(Function.identity(), Collectors.counting()));

        return mergeResources(node.getOwner(), newResources);
    }

    public GameEvent[] collectResources(Board board, Roll roll) {
        List<GameEvent> results = new ArrayList<>();
        results.add(new DiceRollEvent(roll));
        Map<Player, Map<TileType, Long>> resourceMap = getResourcesFromBoard(board, roll.getValue());
        results.addAll(mergeResults(resourceMap));
        return results.toArray(new GameEvent[results.size()]);
    }

    public StealResourceEvent stealResource(List<Player> possibleVictim, Player victim, Player theif) {
        if (!possibleVictim.contains(victim)) {
            throw new GameException(String.format("You can not steal resources from %s", victim.getName()));
        }
        List<TileType> availableResources = getResourcesFromPlayer(victim);
        if (availableResources.isEmpty()) {
            return new StealResourceEvent(victim);
        }
        int random = ThreadLocalRandom.current().nextInt(0, availableResources.size());
        TileType stolenResource = availableResources.get(random);

        Map<TileType, Long> delta = new HashMap<>();
        delta.put(stolenResource, -1L);
        mergeResources(victim, delta);

        delta.put(stolenResource, 1L);
        mergeResources(theif, delta);

        return new StealResourceEvent(victim, stolenResource);
    }

    public boolean hasExcessResources(Player currentPlayer) {
        return getResourcesFromPlayer(currentPlayer).size() > EXCESS_RESOURCES;
    }

    public List<TileType> getResourcesFromPlayer(Player victim) {
        return resources.get(victim).entrySet().stream()
                .flatMap(ResourceService::expand)
                .collect(toList());
    }

    public GameEvent discardResources(Player player, List<TileType> discardList) {
        int size = getResourcesFromPlayer(player).size() / 2;
        Map<TileType, Long> discardMap = discardList.stream()
                .filter(RESOURCES::contains)
                .collect(groupingBy(Function.identity(), Collectors.counting()));

        Map<TileType, Long> playersResource = resources.get(player);
        int actualSize = 0;
        for (Map.Entry<TileType, Long> entry : discardMap.entrySet()) {
            Long actual = playersResource.get(entry.getKey());
            if (actual < entry.getValue()) {
                throw new NotEnoughResourceException(actual, entry.getValue(), entry.getKey());
            }
            actualSize += entry.getValue();
        }

        if (size != actualSize) {
            throw new IncorrectDiscardSizeException(size, discardList);
        }

        for (Map.Entry<TileType, Long> entry : discardMap.entrySet()) {
            playersResource.put(entry.getKey(), playersResource.get(entry.getKey()) - entry.getValue());
        }
        return new DiscardExcessResourcesEvent(discardList);
    }

    private static Stream<TileType> expand(Map.Entry<TileType, Long> entry) {
        List<TileType> results = new ArrayList<>();
        for (long i = 0; i< entry.getValue(); i++) {
            results.add(entry.getKey());
        }
        return results.stream();
    }

    private Map<Player, Map<TileType, Long>> getResourcesFromBoard(Board board, int rollValue) {
        return board.getGameTiles().stream()
                    .filter(gameTile -> gameTile.getTile().getNumber() == rollValue &&
                                        RESOURCES.contains(gameTile.getTile().getType()) &&
                                        !gameTile.isRobber())
                    .flatMap(Tuple::merge)
                    .collect(groupingBy(Tuple::getPlayer,
                             groupingBy(Tuple::getTileType, summingLong(Tuple::getCount))));
    }

    private List<GameEvent> mergeResults(Map<Player, Map<TileType, Long>> resourceMap) {
        List<GameEvent> result = new ArrayList<>();
        for (Player player : resourceMap.keySet()) {
            Map<TileType, Long> newResources = resourceMap.get(player);
            CollectResourcesEvent resourcesEvent = mergeResources(player, newResources);
            result.add(resourcesEvent);
        }

        return result;
    }

    private CollectResourcesEvent mergeResources(Player player, Map<TileType, Long> newResources) {
        Map<TileType, Long> playersResource = resources.get(player);
        newResources.forEach((k, v) -> playersResource.merge(k, v, Long::sum));
        return new CollectResourcesEvent(newResources, player);
    }

    public void hasSufficientResources(Player player, BuildActions action) {
        Map<TileType, Long> playerResources = resources.get(player);
        for (Map.Entry<TileType, Long> entry : action.getResources().entrySet()) {
            TileType type = entry.getKey();
            Long required = entry.getValue() * -1L;
            Long playerResourceCount = playerResources.get(type);
            if (playerResourceCount < required) {
                throw new NotEnoughResourceException(required, playerResourceCount, type);
            }
        }
    }

    public void buy(Player player, BuildActions action) {
        mergeResources(player, action.getResources());
    }

    private static class Tuple {
        private TileType type;
        private Node node;

        Tuple(TileType type, Node node) {
            this.type = type;
            this.node = node;
        }

        static Stream<Tuple> merge(GameTile gameTile) {
            List<Tuple> result = new ArrayList<>();
            for (Node node : gameTile.getPopulatedNodes()) {
                result.add(new Tuple(gameTile.getTileType(), node));
            }
            return result.stream();
        }

        TileType getTileType() {
            return type;
        }

        Player getPlayer() {
            return node.getOwner();
        }

        Long getCount() {
            return node.getBuilding() == Building.SETTLEMENT ? 1L : 2L;
        }
    }
}
