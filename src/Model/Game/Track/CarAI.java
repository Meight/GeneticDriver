package Model.Game.Track;

import Model.Game.Car;
import Model.NeuralNetwork.Net;
import org.dyn4j.geometry.Vector2;
import org.newdawn.slick.tiled.TiledMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CarAI extends Car implements Comparable<CarAI>{
    private Net neuralNetwork;
    private List<Double> inputVals;
    private List<Double> targetVals;
    private List<Double> resultVals;
    private double score = 0.0;
    private double fitness = 0.0;
    private boolean isWinner;
    private boolean isAlive;

    public CarAI(TiledMap map, int x, int y) {
        super(map, x, y);
        List<Integer> topology = new ArrayList<>();
        topology.add(2);
        topology.add(4);
        topology.add(2);

        this.neuralNetwork = new Net(topology);
        //Set all the lists use by the net
        this.inputVals = new ArrayList<>();
        this.targetVals = new ArrayList<>();
        this.resultVals = new ArrayList<>();
        this.score = 0;
        this.fitness = 0;
        this.isWinner = false;
        this.isAlive=true;
    }

    public List<Double> getResultVals() {
        return resultVals;
    }

    //use wall detectors
    private void UpdateNetInput(){
        inputVals.add(new Random().nextDouble()*3.0-1.5);
        inputVals.add(new Random().nextDouble()*3.0-1.5);
    }

    public void ClearNet(){
        inputVals.clear();
        targetVals.clear();
        resultVals.clear();
    }

    public void ProcessNet(){
        ClearNet();
        UpdateNetInput();
        neuralNetwork.FeedForward(inputVals);
        resultVals = neuralNetwork.GetResult();
    }



    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    public boolean isWinner() {
        return isWinner;
    }

    public void setWinner(boolean winner) {
        isWinner = winner;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public Net getNeuralNetwork() {
        return neuralNetwork;
    }

    public void setNeuralNetwork(Net neuralNetwork) {
        this.neuralNetwork = neuralNetwork;
    }

    public void ResetStats(){
        score=0;
        fitness=0;
        isWinner=false;
        isAlive=true;
        this.position = new Vector2(150, 150);
        this.turn = 0;
        this.speed = 0;
        this.angle = 0;
        this.laps = 0;
        this.forward = new Vector2(1, 0);
    }

    @Override
    public int compareTo(CarAI other) {
        if(this.fitness > other.fitness){
            return 1;
        }else if(this.fitness < other.fitness){
            return -1;
        }else{
            return 0;
        }
    }
}
