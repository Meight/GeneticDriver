package Model.Network;

import Model.Game.CarAI;
import org.newdawn.slick.GameContainer;

import java.util.List;

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

    public static Input generateInputFromAI(CarAI car) {
        //maybe the AI will ask for a double rotation
        // in this case maybe we should go straight forward ?
        List<Double> inputs = car.getResultVals();
        boolean turningLeft = inputs.get(0) > 0.5;
        boolean turningRight = inputs.get(1) > 0.5;
        boolean isAccelerating = true;
        if (turningLeft && turningRight)
            return new Input(false, false, isAccelerating);
        return new Input(turningLeft, turningRight, isAccelerating);
    }
}
