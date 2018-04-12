package Model.Network;

import org.newdawn.slick.GameContainer;

/**
 * @author : Matthieu
 */
public class InputFactory {
    private static final int TURNING_LEFT_KEY = org.newdawn.slick.Input.KEY_LEFT;
    private static final int TURNING_RIGHT_KEY = org.newdawn.slick.Input.KEY_RIGHT;
    private static final int ACCELERATING_KEY = org.newdawn.slick.Input.KEY_SPACE;

    public static Input generateInput(GameContainer container) {
        boolean turningLeft = container.getInput().isKeyDown(TURNING_LEFT_KEY);
        boolean turningRight = container.getInput().isKeyDown(TURNING_RIGHT_KEY);
        boolean isAccelerating = container.getInput().isKeyDown(ACCELERATING_KEY);

        return new Input(turningLeft, turningRight, isAccelerating);
    }
}
