package com.mannanlive.settlers.boot.model.lobby;

import com.mannanlive.settlers.core.model.game.Game;
import com.mannanlive.settlers.core.model.player.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class Games {
    private Collection<GameData> data;

    public Games(Map<Long, Game> data) {
        List<GameData> result = new ArrayList<>();
        for (Map.Entry<Long, Game> entry : data.entrySet()) {
            List<Player> players = entry.getValue().getPlayers();
            String name = getGameName(players);
            List<Long> playerIds = getPlayerIds(players);
            result.add(new GameData(entry.getKey(), name, playerIds));
        }
        this.data = result;
    }

    private List<Long> getPlayerIds(List<Player> players) {
        List<Long> playerIds = new ArrayList<>();
        for (Player player : players) {
            if (player.isHuman()) {
                playerIds.add(player.getId());
            }
        }
        return playerIds;
    }

    private String getGameName(List<Player> players) {
        List<String> playerNames = new ArrayList<>();
        int ai = 0;
        for (Player player : players) {
            if (player.isHuman()) {
                playerNames.add(player.getName());
            } else {
                ai++;
            }
        }
        String name = String.join(", ", playerNames);
        if (ai > 0) {
            name += " and " + ai + " AI";
        }
        return name;
    }

    public Collection<GameData> getData() {
        return data;
    }
}
