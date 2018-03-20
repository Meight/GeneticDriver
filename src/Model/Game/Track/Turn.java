package Model.Game.Track;

/**
 * @author Matthieu Le Boucher
 */
public class Turn implements Segment {
    public enum Direction {
        LEFT, RIGHT
    }

    /**
     * Arc covered by the turn, in radians.
     */
    private float arc;

    /**
     * Start radius of the turn.
     */
    private float startRadius;

    /**
     * End radius of the turn.
     */
    private float endRadius;
}
