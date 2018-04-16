package Model.NeuralNetwork.Transfer;

/*8
 * @author Matthieu Le Boucher
 */
public class SigmoidFunction{
    public static double Evaluate(double value) {
        return  Math.tanh(value);
    }

    public static double evaluateDerivative(double value) {
        //tanh approximated derivative
        return 1.0 - value * value;
    }
}
