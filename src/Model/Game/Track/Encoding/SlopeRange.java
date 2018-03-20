package Model.Game.Track.Encoding;

/**
 * @author Matthieu Le Boucher
 */
public class SlopeRange {
    /**
     * The lower bound of the interval.
     */
    private float lowerBound;

    /**
     * The upper bound of the interval.
     */
    private float upperBound;

    /**
     * Whether or not the interval is empty.
     */
    private boolean isEmpty = false;

    public SlopeRange(float lowerBound, float upperBound) {
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }

    public float getLowerBound() {
        return lowerBound;
    }

    public float getUpperBound() {
        return upperBound;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public void setEmpty(boolean empty) {
        isEmpty = empty;
    }
}
