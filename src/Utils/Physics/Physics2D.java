package Utils.Physics;

import org.dyn4j.geometry.Vector2;
import org.newdawn.slick.tiled.TiledMap;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Matthieu Le Boucher
 */
public class Physics2D {
    private static final int MAXIMAL_LOOPS = 100;

    public static RaycastHit raycast(Vector2 position, Vector2 direction, TiledMap map, double rayLength) {
        RaycastHit hit = null;

        List<Vector2> rayLine = BresenhamLine(position,
                new Vector2(position).add(direction.getNormalized().multiply(rayLength)));

        for(Vector2 point : rayLine) {
            if (!isPixelAccessible(point.x, point.y, map)) {
                hit = new RaycastHit(position, direction, new Vector2(point), point.distance(position));
                break;
            }
        }

        return hit;
    }

    private static boolean isPixelAccessible(double x, double y, TiledMap map) {
        // Get the tile coordinates from the pixel coord.
        int tileX = (int) Math.floor(x / map.getTileWidth());
        int tileY = (int) Math.floor(y / map.getTileHeight());

        try {
            return tileX >= 0 && tileX < map.getWidth() && tileY >= 0 && tileY < map.getHeight()
                    && map.getTileImage(tileX, tileY, map.getLayerIndex("Environment")) != null;
        } catch(ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }

    private static List<Vector2> BresenhamLine(Vector2 p0, Vector2 p1) {
        return BresenhamLine((int) p0.x, (int) p0.y, (int) p1.x, (int) p1.y);
    }

    private static List<Vector2> BresenhamLine(int x1, int y1, int x2, int y2) {
        List<Vector2> result = new ArrayList<>();

        // delta of exact value and rounded value of the dependent variable
        int d = 0;

        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);

        int dx2 = 2 * dx; // slope scaling factors to
        int dy2 = 2 * dy; // avoid floating point

        int ix = x1 < x2 ? 1 : -1; // increment direction
        int iy = y1 < y2 ? 1 : -1;

        int x = x1;
        int y = y1;

        if (dx >= dy) {
            while (true) {
                result.add(new Vector2(x, y));
                if (x == x2)
                    break;
                x += ix;
                d += dy2;
                if (d > dx) {
                    y += iy;
                    d -= dx2;
                }
            }
        } else {
            while (true) {
                result.add(new Vector2(x, y));
                if (y == y2)
                    break;
                y += iy;
                d += dx2;
                if (d > dy) {
                    x += ix;
                    d -= dy2;
                }
            }
        }

        return result;
    }
}
