package Model.NeuralNetwork;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

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

    public void AddNeuron(Neuron n) {
        this.layer.add(n);
    }

    public Neuron GetBiasNeuron() {
        return this.layer.get(this.layer.size());
    }

    public void SetBiasNeuron(Neuron neuron){this.layer.set(this.layer.size(),neuron);}
}

