package Model.Game.Track;

import Model.Game.Car;
import Model.NeuralNetwork.Net;
import org.newdawn.slick.tiled.TiledMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CarAI extends Car {
    private Net neuralNetwork;
    private List<Double> inputVals;
    private List<Double> targetVals;
    private List<Double> resultVals;

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
}
