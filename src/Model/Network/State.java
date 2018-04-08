package Model.Network;

import org.dyn4j.geometry.Vector2;

/**
 * @author Matthieu Le Boucher
 *
 * The physical state of the car, sent across the network.
 */
public class State {
    private Vector2 position;
    private Vector2 velocity;
    private Vector2 acceleration;

    public State(Vector2 position, Vector2 velocity, Vector2 acceleration) {
        this.position = position;
        this.velocity = velocity;
        this.acceleration = acceleration;
    }

    public Vector2 getPosition() {
        return position;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public Vector2 getAcceleration() {
        return acceleration;
    }
}
