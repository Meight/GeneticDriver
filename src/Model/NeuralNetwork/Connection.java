package Model.NeuralNetwork;

public class Connection {
    double weight;
    double deltaWeight;

    public Connection(double weight, double deltaWeight) {
        this.weight = weight;
        this.deltaWeight = deltaWeight;
        System.out.println(this.toString());
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getDeltaWeight() {
        return deltaWeight;
    }

    public void setDeltaWeight(double deltaWeight) {
        this.deltaWeight = deltaWeight;
    }

    @Override
    public String toString(){
        return "Weight Value : "+weight + " And delta weight "+deltaWeight+"\n\n";
    }
}
