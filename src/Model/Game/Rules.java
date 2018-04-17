package Model.Game;

import org.newdawn.slick.tiled.TiledMap;

/**
 * @author Matthieu Le Boucher
 */
public class Rules {
    private static final String ARRIVAL_LAYER_NAME = "Arrival";
    private static final String SLOW_LAYER_NAME = "Slow";
    private static final String OBSTACLE_LAYER_NAME = "Wall";

    public static boolean isTileArrival(TiledMap map, double x, double y) {
        return map.getTileImage(
                (int) x / map.getTileWidth(),
                (int) y / map.getTileHeight(),
                map.getLayerIndex(ARRIVAL_LAYER_NAME)) != null;
    }
}
