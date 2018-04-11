package Model.NeuralNetwork;

import Model.NeuralNetwork.Parser.ResultParser;

/**
 * @author Matthieu Le Boucher
 */
public class NeuralNetwork {
    private int dimension;

    private float[][] inputs;

    private int[] outputs;

    private float[] bias;

    private int neurons;

    //private TransferFunction transferFunction;

    private Learner learner;

    private ResultParser resultParser;

    private Analyzer analyzer;

    public void start() {

    }


}
