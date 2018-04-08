package Model.Network;

/**
 * @author Matthieu Le Boucher
 */
public interface NetworkBehavior {
    void processInput(Input input, double time);

    void processState(State state, double time);
}
