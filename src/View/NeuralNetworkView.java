package View;

import Model.NeuralNetwork.Layer;
import Model.NeuralNetwork.Net;

import javax.swing.*;
import java.awt.*;

/**
 * @author Matthieu Le Boucher
 */
public class NeuralNetworkView extends JFrame {
    private JFrame frame;

    private Net neuralNetwork;

    private static final int LAYER_WIDTH = 50;
    private static final int LAYER_MARGIN = 100;
    private static final int WINDOW_HEIGHT = 500;

    public NeuralNetworkView(Net neuralNetwork) throws HeadlessException {
        this.neuralNetwork = neuralNetwork;
        frame = new JFrame("Neural network view");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(neuralNetwork.getLayers().size() * (LAYER_WIDTH + LAYER_MARGIN), WINDOW_HEIGHT));
        frame.pack();
        frame.setVisible(true);
    }

    public void render() {
        Graphics graphics = frame.getGraphics();

        int x = 0;
        int y = 0;

        graphics.setColor(Color.blue);
        for(Layer layer : neuralNetwork.getLayers()) {
            graphics.drawRect(x, y, LAYER_WIDTH, WINDOW_HEIGHT);
            x += LAYER_WIDTH + LAYER_MARGIN;
        }
    }
}
