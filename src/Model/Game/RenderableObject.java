package Model.Game;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * @author Matthieu Le Boucher
 */
public abstract class RenderableObject {
    protected Image image;

    void init(GameContainer container) throws SlickException {

    }

    void render(GameContainer container, Graphics g) throws SlickException {

    }
}
