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


    public String serial(){
        return turnLeft+"/"+turnRight+"/"+accelerate;
    }

    public Input (String serie){
        String[] res = serie.split("/");
        this.turnLeft = Boolean.valueOf(res[0]);
        this.turnRight = Boolean.valueOf(res[1]);
        this.accelerate = Boolean.valueOf(res[2]);
    }

}
