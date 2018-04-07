package Model.Game;

import org.dyn4j.geometry.Vector2;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * @author Matthieu Le Boucher
 */
public abstract class RenderableObject {
    protected Image image;

    protected Vector2 position;


    void init(GameContainer container) throws SlickException {

    }

    void render(GameContainer container, Graphics g) throws SlickException {
        g.drawImage(image, (float) position.x, (float) position.y);
    }
}
