package Model.Game.Track.Encoding;

/**
 * @author Matthieu Le Boucher
 */
public class ControlPoint {
    /**
     * Radial coordinate (distance from origin) of the control point.
     */
    private float r;

    /**
     * Angular coordinate of the control point.
     */
    private float theta;

    /**
     * Slope of the track's tangent line at the control point.
     */
    private float tangentSlope;

    public ControlPoint(float r, float theta, float tangentSlope) {
        this.r = r;
        this.theta = theta;
        this.tangentSlope = tangentSlope;
    }
}
