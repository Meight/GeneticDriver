package Utils.Physics;

import org.dyn4j.geometry.Vector2;

/**
 * @author Matthieu Le Boucher
 */
public class RaycastHit {
    private Vector2 origin;
    private Vector2 direction;
    private Vector2 hitPoint;

    private double distance;
    private int type;

    public RaycastHit(Vector2 origin, Vector2 direction, Vector2 hitPoint, double distance) {
        this.origin = origin;
        this.direction = direction;
        this.hitPoint = hitPoint;
        this.distance = distance;
    }

    public Vector2 getHitPoint() {
        return hitPoint;
    }

    public Vector2 getOrigin() {
        return origin;
    }

    public Vector2 getDirection() {
        return direction;
    }

    public double getDistance() {
        return distance;
    }

    public int getType() {
        return type;
    }
}
