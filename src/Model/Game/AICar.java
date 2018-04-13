package Model.Game;

import Utils.Physics.Physics2D;
import Utils.Physics.RaycastHit;
import org.dyn4j.geometry.Vector2;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

/**
 * @author Matthieu Le Boucher
 */
public class AICar extends Car {
    /**
     * List of the directions the AI will constantly check for obstacles using raycasts.
     */
    private Vector2[] checkedDirections = { forward, right, left };

    private double raycastsLength = 700;

    public AICar(TiledMap map, int x, int y) {
        super(map, x, y);
    }

    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {
        super.render(container, g);

        for(Vector2 direction : checkedDirections) {
            RaycastHit raycastHit = Physics2D.raycast(position, direction, map, raycastsLength);

            if (raycastHit != null) {
                g.drawLine((int) position.x, (int) position.y,
                        (float) raycastHit.getHitPoint().x,
                        (float) raycastHit.getHitPoint().y);
            }
        }
    }
}
