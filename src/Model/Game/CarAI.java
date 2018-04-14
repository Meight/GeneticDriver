package Model.Game;

import Model.NeuralNetwork.Net;
import Model.NeuralNetwork.SaveNetSystem;
import Utils.Physics.Physics2D;
import Utils.Physics.RaycastHit;
import org.dyn4j.geometry.Vector2;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CarAI extends Car implements Comparable<CarAI>{
    private Net neuralNetwork;
    private List<Double> inputVals;
    private List<Double> targetVals;
    private List<Double> resultVals;
    private boolean isWinner;

    /**
     * List of the directions the AI will constantly check for obstacles using raycasts.
     */
    private Vector2[] checkedDirections = { forward, right, left, diagLeft, diagRight };

    private double raycastsLength = 700;

    public CarAI(TiledMap map, int x, int y) {
        super(map, x, y);
        List<Integer> topology = new ArrayList<>();
        topology.add(5);
        topology.add(10);
        //topology.add(3);
        topology.add(2);

        this.neuralNetwork = new Net(topology);
        //Set all the lists use by the net
        this.inputVals = new ArrayList<>();
        this.targetVals = new ArrayList<>();
        this.resultVals = new ArrayList<>();
        this.score = 0;
        this.fitness = 0;
        this.isWinner = false;
    }

    public CarAI(TiledMap map, int x, int y, String filename) {
        super(map, x, y);

        try {
            this.neuralNetwork = SaveNetSystem.CreateNetFromFile(filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Set all the lists use by the net
        this.inputVals = new ArrayList<>();
        this.targetVals = new ArrayList<>();
        this.resultVals = new ArrayList<>();
        this.score = 0;
        this.fitness = 0;
        this.isWinner = false;
        System.out.println(neuralNetwork);
    }



    public List<Double> getResultVals() {
        return resultVals;
    }

    //use wall detectors
    private void UpdateNetInput(){
        List<Double> distances = new ArrayList<>();
        for(Vector2 direction : checkedDirections) {
            RaycastHit raycastHit = Physics2D.raycast(position, direction, map, raycastsLength);
            if (raycastHit != null) {
                distances.add(Math.floor(raycastHit.getDistance()/raycastsLength*100)/100 < 0.25 ? 1d:0d);
            }
        }
        if(distances.size()==5){
            inputVals.clear();
            inputVals.addAll(distances);
            //System.out.println(inputVals);
        }
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

    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {
        super.render(container, g);
        for(Vector2 direction : checkedDirections) {
            RaycastHit raycastHit = Physics2D.raycast(position, direction, map, raycastsLength);
            if (raycastHit != null) {
                g.drawLine((int) position.x, (int) position.y,
                        (float) raycastHit.getHitPoint().x,
                        (float) raycastHit.getHitPoint().y);
            }
        }
    }

    public boolean isWinner() {
        return isWinner;
    }

    public void setWinner(boolean winner) {
        isWinner = winner;
    }



    public Net getNeuralNetwork() {
        return neuralNetwork;
    }

    public Net getCleanNeuralNetwork() {
        return neuralNetwork.GetSameNetWithNewAdress();
    }

    public void setNeuralNetwork(Net neuralNetwork) {
        this.neuralNetwork = neuralNetwork;
    }

    public void ResetStats(){
        this.score=0;
        this.fitness=0;
        this.isWinner=false;
        this.currentRotation=0f;
        this.position = new Vector2(200, 320);
        this.turn = 0;
        this.speed = 0;
        this.angle = 0;
        this.lapsTime = new ArrayList<>();
        this.forward = new Vector2(1, 0);
        this.right = new Vector2(forward).rotate(Math.PI / 4);
        this.diagRight = new Vector2(forward).rotate(Math.PI / 8);
        this.left = new Vector2(forward).rotate(-Math.PI / 4);
        this.diagLeft = new Vector2(forward).rotate(-Math.PI / 8);
        checkedDirections = new Vector2[]{forward, right, left,diagLeft,diagRight};
        this.isAlive=true;
    }

    @Override
    public int compareTo(CarAI other) {
        if(this.fitness > other.fitness){
            return -1;
        }else if(this.fitness < other.fitness){
            return 1;
        }else{
            return 0;
        }
    }
}
