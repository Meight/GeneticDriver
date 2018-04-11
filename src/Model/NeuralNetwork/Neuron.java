package Model.NeuralNetwork;

import Model.NeuralNetwork.Transfer.SigmoidFunction;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;

public class Neuron {

    static double RandomWeight() { return new Random().nextFloat(); }

    double outputVal;
    List<Connection> outputWeights = new ArrayList<>();
    int myIndex;
    double gradient;

    static double eta = 0.15;    // [0.0..1.0] overall net training rate
    static double alpha = 0.5;  // [0.0..n] multiplier of last weight change (momentum)

    public Neuron(int numOutputs, int index) {
        for (int c = 0; c < numOutputs; ++c)
        {
            outputWeights.add(new Connection(RandomWeight(),0f));
        }
        myIndex = index;
    }

    void SetOutputVal(double val) { outputVal = val; }
    double GetOutputVal() { return outputVal; }
    List<Connection> GetOutputWeights() { return outputWeights; }

    public void SetWeights(List<Double> weights) {

        for (int i = 0; i < (int)weights.size(); i++)
        {
            outputWeights.get(i).weight = weights.get(i);
        }
    }

    void UpdateInputWeights(Layer prevLayer)
    {
        // The weights to be updated are in the connection container
        // in the neurons in the preceding layer

        for (int n = 0; n < prevLayer.getLayer().size(); ++n)
        {
            Neuron neuron = prevLayer.getLayer().get(n);
            double oldDeltaWeight = neuron.outputWeights.get(myIndex).deltaWeight;

            double newDeltaWeight =
                    //Individual input, magnified by the gradient and train rate
                    eta // learning rate (0.0 - slow learner, 0.2 - medium learner, 1.0 - reckless learner
                            * neuron.GetOutputVal()
                            * gradient
                            // Also add momentum = a fraction of the previous delta weight
                            + alpha
                            * oldDeltaWeight;

            neuron.outputWeights.get(myIndex).deltaWeight = newDeltaWeight;
            neuron.outputWeights.get(myIndex).weight += newDeltaWeight;
        }
    }

    //make the sum of the product of all the weight by their input value
    void FeedForward(Layer prevLayer)
    {
        double sum = 0.0;
        //sum the previous layer's output (which become inputs of the next layer)
        //include the bias node from the previous layer
        for (int n = 0; n < prevLayer.getLayer().size(); ++n)
        {
            sum += prevLayer.getLayer().get(n).GetOutputVal() *
                    prevLayer.getLayer().get(n).outputWeights.get(myIndex).weight;
        }

        outputVal = SigmoidFunction.Evaluate(sum);
    }

    /*private static double TransferFunction(double x){
        //hyperbolic transfer function
        // tanh - output range [-1.0..1.0]
        return Math.tanh(x);
    }
    private static double TransferFunctionDerivative(double x){
        //tanh approximated derivative
        return 1.0 - x * x;
    }*/

    void CalcOutputGradients(double targetVal)
    {
        double delta = targetVal - outputVal;
        gradient = delta * SigmoidFunction.EvaluateDerivative(outputVal);
    }

    void CalcHiddenGradients(Layer nextLayer)
    {
        double dow = SumDOW(nextLayer);
        gradient = dow * SigmoidFunction.EvaluateDerivative(outputVal);
    }

    private double SumDOW(Layer nextLayer){
        double sum = 0.0;

        //Sum our contributions of the error at the nodes we feed

        for (int n = 0; n < nextLayer.getLayer().size() - 1; ++n)
        {
            sum += outputWeights.get(n).weight * nextLayer.getLayer().get(n).gradient;
        }

        return sum;
    }


    @Override
    public String toString(){
        return "Ouput Value : "+outputVal + "\nAnd all connexions"+outputWeights.toString()+"\n\n";
    }
}

