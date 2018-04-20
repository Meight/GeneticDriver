package Utils.Physics;

import org.dyn4j.geometry.Vector2;

/**
 * Abstraction to store important information about a physical hit between a ray and an obstacle.
 *
 * @author Matthieu Le Boucher
 */
public class RaycastHit {
    /**
     * The origin of the ray.
     */
    private Vector2 origin;

    /**
     * The direction of the ray.
     */
    private Vector2 direction;

    /**
     * Coordinates of the hit point.
     */
    private Vector2 hitPoint;

    /**
     * Distance between the origin and the hit point.
     */
    private double distance;

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
}
