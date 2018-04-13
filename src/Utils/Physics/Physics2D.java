package Utils.Physics;

import org.dyn4j.geometry.Vector2;
import org.newdawn.slick.tiled.TiledMap;

/**
 * @author Matthieu Le Boucher
 */
public class Physics2D {
    private static final int MAXIMAL_LOOPS = 100;

    public static RaycastHit raycast(Vector2 position, Vector2 direction, TiledMap map) {
        Vector2 mapPosition = new Vector2((int) (position.x / map.getTileWidth()), (int) (position.y / map.getTileHeight()));

        //length of ray from current position to next x or y-side
        double sideDistX;
        double sideDistY;

        //length of ray from one x or y-side to next x or y-side
        double deltaDistX = Math.abs(map.getTileWidth() / direction.x);
        double deltaDistY = Math.abs(map.getTileHeight() / direction.y);

        double perpWallDist;

        //what direction to step in x or y-direction (either +1 or -1)
        int stepX;
        int stepY;

        RaycastHit hit = null; //was there a wall hit?
        int side; //was a NS or a EW wall hit?


        //calculate step and initial sideDist
        if (direction.x < 0)
        {
            stepX = -1;
            sideDistX = Math.abs(position.x - mapPosition.x) * deltaDistX;
        }
        else
        {
            stepX = 1;
            sideDistX = Math.abs(mapPosition.x + 1.0 - position.x) * deltaDistX;
        }

        if (direction.y < 0)
        {
            stepY = -1;
            sideDistY = Math.abs(position.y - mapPosition.y) * deltaDistY;
        }
        else
        {
            stepY = 1;
            sideDistY = Math.abs(mapPosition.y + 1.0 - position.y) * deltaDistY;
        }

        int i = 0;
        while (hit == null && i < MAXIMAL_LOOPS)
        {
            //jump to next map square, OR in x-direction, OR in y-direction
            if (sideDistX < sideDistY)
            {
                sideDistX += deltaDistX;
                mapPosition.x += stepX;
                side = 0;
            }
            else
            {
                sideDistY += deltaDistY;
                mapPosition.y += stepY;
                side = 1;
            }

            if(mapPosition.x < 0 || mapPosition.y < 0 || mapPosition.x >= map.getWidth() || mapPosition.y >= map.getHeight())
                return null;

            //Check if ray has hit a wall
            if (map.getTileImage((int) mapPosition.x, (int) mapPosition.y, map.getLayerIndex("Slow")) != null) {
                //System.out.println("Obstacle found at (" + mapPosition.x + ", " + mapPosition.y + ")");
                if(side == 1)
                    mapPosition.add(1, 1);
                mapPosition.multiply(map.getTileWidth());
                hit = new RaycastHit(position, direction, new Vector2(mapPosition), mapPosition.subtract(position).getMagnitude());
            }

            i++;
        }

        return hit;
    }
}
