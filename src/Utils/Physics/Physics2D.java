package Utils.Physics;

import Model.Game.MapRules;
import org.dyn4j.geometry.Vector2;
import org.newdawn.slick.tiled.TiledMap;

import java.util.ArrayList;
import java.util.List;

/**
 * A small custom library to cast rays around the map. Optimized using Bresenham's algorithm.
 *
 * @author Matthieu Le Boucher
 */
public class Physics2D {
    private static final int MAXIMAL_LOOPS = 100;

    /**
     * Casts a ray from a given origin into a given direction with an arbitrary length.
     *
     * @param position  The origin of the ray.
     * @param direction The direction of the ray.
     * @param map       The map constraining the ray.
     * @param rayLength The maximal length of the ray.
     * @return          A @see Utils.Physics.RaycastHit containing data about the hit if there was a hit,
     *                  null otherwise.
     */
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

    /**
     * Tells whether or not a pixel is reachable based on map's constraints.
     *
     * @param x     The x-coordinate of the pixel.
     * @param y     The y-coordinate of the pixel.
     * @param map   The map constraining the pixels.
     */
    private static boolean isPixelAccessible(double x, double y, TiledMap map) {
        try {
            return MapRules.isRoadTile(map, x, y);
        } catch(ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }

    /**
     * Returns a list of points being part of the line between two points.
     *
     * @param p0 The first point of the line.
     * @param p1 The second point of the line.
     * @return A list of all points situated on that line.
     */
    private static List<Vector2> BresenhamLine(Vector2 p0, Vector2 p1) {
        return BresenhamLine((int) p0.x, (int) p0.y, (int) p1.x, (int) p1.y);
    }

    /**
     * Returns a list of points being part of the line between two points (x1, y1) and (x2, y2).
     *
     * @param x1 The x-coordinate of the first point of the line.
     * @param y1 The y-coordinate of the first point of the line.
     * @param x2 The x-coordinate of the second point of the line.
     * @param y2 The y-coordinate of the second point of the line.
     * @return A list of all points situated on that line.
     */
    private static List<Vector2> BresenhamLine(int x1, int y1, int x2, int y2) {
        List<Vector2> result = new ArrayList<>();

        // Delta of exact value and rounded value of the dependent variable.
        int d = 0;

        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);

        // Slope scaling factors to avoid floating point.
        int dx2 = 2 * dx;
        int dy2 = 2 * dy;

        // Increment direction.
        int ix = x1 < x2 ? 1 : -1;
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
