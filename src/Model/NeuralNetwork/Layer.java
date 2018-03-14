package Model.NeuralNetwork;

/**
 * @author Matthieu Le Boucher
 */
public class Layer {
    private float[] bias;
    private float[] weights;

    public int neurons;
    public int dimension;

    public Layer(int neurons, int dimension) {
        this.neurons = neurons;
        this.dimension = dimension;
    }

    private void initialize() {

    }
}
