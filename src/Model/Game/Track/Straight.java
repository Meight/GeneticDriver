package Model.Game.Track;

/**
 * @author : Matthieu Le Boucher
 */
public class Straight implements Segment {
    private int length;

    public Straight(int length) {
        this.length = length;
    }

    public int getLength() {
        return length;
    }
}
