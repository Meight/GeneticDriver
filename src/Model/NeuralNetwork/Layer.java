package Model.NeuralNetwork;
import java.util.ArrayList;
import java.util.List;

public class Layer {

    List<Neuron> layer;

    public Layer(List<Neuron> layer) {
        this.layer = layer;
    }

    public Layer() {
        this.layer = new ArrayList<>();
    }

    public List<Neuron> getLayer() {
        return layer;
    }

    public void setLayer(List<Neuron> layer) {
        this.layer = layer;
    }

    public void addNeuron(Neuron n) {
        this.layer.add(n);
    }

    public Neuron getBiasNeuron() {
        return this.layer.get(this.layer.size() - 1);
    }

    public void setBiasNeuron(Neuron neuron){this.layer.set(this.layer.size() - 1, neuron);}
}

