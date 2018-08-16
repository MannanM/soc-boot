package com.mannanlive.settlers.core.service;

import com.mannanlive.settlers.core.factory.tile.StandardTileGeneratorService;
import com.mannanlive.settlers.core.factory.tile.TileGeneratorService;
import com.mannanlive.settlers.core.model.board.Connector;
import com.mannanlive.settlers.core.model.board.Node;
import com.mannanlive.settlers.core.model.game.GameTile;
import com.mannanlive.settlers.core.model.board.Tile;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class BoardFactoryTest {
    @Test
    public void create() throws Exception {
        TileGeneratorService generatorService = new StandardTileGeneratorService();
        List<Tile> tiles = generatorService.generate();
        BoardFactory factory = new BoardFactory(tiles);
        List<GameTile> gameTiles = factory.create().getGameTiles();
        System.out.println(gameTiles);
    }

    @Test
    public void create2() throws Exception {
        TileGeneratorService generatorService = new StandardTileGeneratorService();
        List<Tile> tiles = generatorService.generate();
        BoardFactory factory = new BoardFactory(tiles);
        List<GameTile> gameTiles = factory.create().getGameTiles();
        for (int i = 0; i < gameTiles.size(); i++) {
            for (int j = 0; j < 6; j++) {
                Node node = gameTiles.get(i).getNode(j);
//                if (i == node.getTileId() && j == node.getNodeId()) {
//                    System.out.println(i+","+j);
//                }
                System.out.println(i +"," + j + "="
                        + node.getTileId() + "," + node.getNodeId());
            }
        }
    }

    @Test
    public void testConnectivity() {
        TileGeneratorService generatorService = new StandardTileGeneratorService();
        List<Tile> tiles = generatorService.generate();
        BoardFactory factory = new BoardFactory(tiles);
        List<GameTile> gameTiles = factory.create().getGameTiles();
        Connector connector = gameTiles.get(12).getConnector(2);
        Node node0 = connector.getNode(0);
        assertThat(node0.getTileId(), equalTo(8));
        assertThat(node0.getNodeId(), equalTo(4));

        Node node1 = connector.getNode(1);
        assertThat(node1.getTileId(), equalTo(12));
        assertThat(node1.getNodeId(), equalTo(3));
    }
}