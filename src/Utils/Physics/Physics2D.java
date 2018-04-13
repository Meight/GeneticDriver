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

    public static RaycastHit raycast(Vector2 position, Vector2 direction, TiledMap map) {
        RaycastHit hit = null;

        double rayLength = 1000;

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

    private static boolean isPointingUp(Vector2 vector) {
        return vector.y < 0;
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

    private static <T> void Swap(T a, T b) {
        T c = a;
        a = b;
        b = c;
    }

    private static List<Vector2> BresenhamLine(Vector2 p0, Vector2 p1) {
        return BresenhamLine((int) p0.x, (int) p0.y, (int) p1.x, (int) p1.y);
    }

    private static List<Vector2> BresenhamLine(int x0, int y0, int x1, int y1) {
        List<Vector2> result = new ArrayList<>();

        boolean steep = Math.abs(y1 - y0) > Math.abs(x1 - x0);
        if (steep) {
            Swap(x0, y0);
            Swap(x1, y1);
        }
        if (x0 > x1) {
            Swap(x0, x1);
            Swap(y0, y1);
        }

        int deltaX = x1 - x0;
        int deltaY = Math.abs(y1 - y0);
        int error = 0;
        int yStep;
        int y = y0;
        if (y0 < y1) yStep = 1; else yStep = -1;
        for (int x = x0; x <= x1; x++) {
            if (steep) result.add(new Vector2(y, x));
            else result.add(new Vector2(x, y));
            error += deltaY;
            if (2 * error >= deltaX) {
                y += yStep;
                error -= deltaX;
            }
        }

        return result;
    }
}
