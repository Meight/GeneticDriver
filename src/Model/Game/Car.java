package Model.Game;

import org.dyn4j.geometry.Vector2;

/**
 * @author Matthieu Boucher
 */
class Car extends RenderableObject {
    /**
     * Current velocity vector of the car.
     */
    private Vector2 velocity;

    /**
     * Target velocity vector of the car, i.e. the next wanted velocity.
     */
    private Vector2 targetVelocity;

    /**
     * Steer force currently applied to the car.
     */
    private Vector2 steerForce;


}
