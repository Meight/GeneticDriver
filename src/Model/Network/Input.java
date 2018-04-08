package Model.Network;

/**
 * @author Matthieu Le Boucher
 */
public class Input {
    private boolean turnLeft;
    private boolean turnRight;
    private boolean accelerate;

    public Input(boolean turnLeft, boolean turnRight, boolean accelerate) {
        this.turnLeft = turnLeft;
        this.turnRight = turnRight;
        this.accelerate = accelerate;
    }

    public boolean isTurningLeft() {
        return turnLeft;
    }

    public boolean isTurningRight() {
        return turnRight;
    }

    public boolean isAccelerating() {
        return accelerate;
    }
}
