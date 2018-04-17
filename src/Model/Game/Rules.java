package Model.Game;

import org.newdawn.slick.tiled.TiledMap;

/**
 * @author Matthieu Le Boucher
 */
public class Rules {
    private static final String ARRIVAL_LAYER_NAME = "Arrival";
    private static final String SLOW_LAYER_NAME = "Slow";
    private static final String OBSTACLE_LAYER_NAME = "Walls";

    private static final double WALL_SPEED_PENALTY_FACTOR = 9000d;
    private static final double SLOW_SPEED_PENALTY_FACTOR = 3d;

    static boolean isArrivalTile(TiledMap map, double x, double y) {
        return !isLayerNull(map, x, y, ARRIVAL_LAYER_NAME);
    }

    static boolean isSlowTile(TiledMap map, double x, double y) {
        return !isLayerNull(map, x, y, SLOW_LAYER_NAME);
    }

    static boolean isWallTile(TiledMap map, double x, double y) {
        return !isLayerNull(map, x, y, OBSTACLE_LAYER_NAME);
    }

    static double getSpeedPenaltyFactorForTile(TiledMap map, double x, double y) {
        try {
            if (isWallTile(map, x, y))
                return WALL_SPEED_PENALTY_FACTOR;
            else if (isSlowTile(map, x, y))
                return SLOW_SPEED_PENALTY_FACTOR;

            // If the tile is just road, no penalty is applied.
            return 1;
        } catch(IndexOutOfBoundsException e) {
            // Outside the map, consider it's a wall.
            return WALL_SPEED_PENALTY_FACTOR;
        }
    }

    private static boolean isLayerNull(TiledMap map, double x, double y, String layerName) {
        return map.getTileImage(
                (int) x / map.getTileWidth(),
                (int) y / map.getTileHeight(),
                map.getLayerIndex(layerName)) == null;
    }
}
