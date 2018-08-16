package com.mannanlive.settlers.boot.service.event;

import com.mannanlive.settlers.boot.model.GameEventsData;
import com.mannanlive.settlers.core.model.board.Node;
import com.mannanlive.settlers.core.model.board.Tile;
import com.mannanlive.settlers.core.model.board.TileType;
import com.mannanlive.settlers.core.model.game.observer.GameEvent;
import com.mannanlive.settlers.core.model.game.observer.GameEventType;
import com.mannanlive.settlers.core.model.game.observer.SettlementGameEvent;
import com.mannanlive.settlers.core.model.player.Player;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Order(100)
public class SettlementEventTranslator implements EventTranslator {
    public boolean appliesTo(GameEventType eventType) {
        return eventType == GameEventType.BUILT_SETTLEMENT;
    }

    public GameEventsData translate(GameEvent gameEvent, int player, List<Player> players, Player requester) {
        Node node = ((SettlementGameEvent) gameEvent).getNode();
        GameEventsData data = new GameEventsData("@p" + player + " has built a settlement " +
                getTileText(node.getAdjacentTiles()), player);
        data.setTileId(node.getTileId());
        data.setPosition(node.getNodeId());
        data.setBuilding(node.getBuilding().toString());
        return data;
    }

    private String getTileText(List<Tile> tiles) {
        TileType firstType = tiles.get(0).getType();
        switch (tiles.size()) {
            case 1:
                return "on a #" + firstType + " resource";
            case 2:
                TileType secondType = tiles.get(1).getType();
                if (firstType == secondType) {
                    return "in between two #" + firstType + " resources";
                }
                return "in between a #" + firstType + " and #" + secondType + " resource";
            default:
                secondType = tiles.get(1).getType();
                TileType thirdType = tiles.get(2).getType();
                if (firstType == secondType && firstType == thirdType) {
                    return "between three #" + firstType + " resources";
                }
                if (firstType == secondType) {
                    return "between two #" + firstType + " and a #" + thirdType + " resource";
                }
                if (firstType == thirdType) {
                    return "between two #" + firstType + " and a #" + secondType + " resource";
                }
                if (secondType == thirdType) {
                    return "between two #" + secondType + " and a #" + firstType + " resource";
                }
                return "between a #" + firstType + ", #"+ secondType + " and #" + thirdType + " resource";
        }
    }
}
