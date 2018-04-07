package Model.Game;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/**
 * @author Matthieu Le Boucher
 */
public interface Renderable {
    void render(GameContainer container, Graphics g) throws SlickException;
}
