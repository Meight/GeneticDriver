package Model.Game.Track;

import Model.Game.Car;
import Model.NeuralNetwork.Net;
import org.newdawn.slick.tiled.TiledMap;

import java.util.ArrayList;
import java.util.List;

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
        System.out.println("Je suis une IA");
    }

    public List<Double> getResultVals() {
        return resultVals;
    }

    //use wall detectors
    private void UpdateNetInput(){
        inputVals.add(1.0);
        inputVals.add(1.0);
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
