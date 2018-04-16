package Model.NeuralNetwork;

import Model.Game.CarAI;
import Model.Game.Player;
import Model.Game.RenderableObject;
import Model.Network.InputFactory;
import View.ScoreView;
import org.newdawn.slick.tiled.TiledMap;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GeneticSystem {
    List<Player> players;
    int topUnitsToKeep;
    int iteration;
    int carNumber;
    TiledMap map;
    float mutationRate;
    int bestPopulation;
    double bestFitness;
    double bestScore;
    SaveNetSystem saveSystem;
    ScoreView scoreView;

    public GeneticSystem(int carNumber, TiledMap map, int topUnitsToKeep, ScoreView scoreView) {
        this.topUnitsToKeep=topUnitsToKeep;
        this.carNumber=carNumber;
        this.map = map;
        this.scoreView = scoreView;

        players = new ArrayList<>();
        players = CreateNewPopulation();
        this.iteration = 0;
        this.mutationRate = 1.0f;
        this.bestPopulation = 0;
        this.bestFitness = 0;
        this.bestScore = 0;

        try {
            saveSystem = new SaveNetSystem("save.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
    TODO:
    HANDLE DEATH
    HANDLE SCORE
    HANDLE RAYCAST DISTANCE AS INPUT
     */
    public void Update(int delta){
        //if not all car dead
        if(AreAllCarNotDead()){
            ActivatesBrain(delta);
        } else {
            players = EvolvePopulation();
            iteration++;
            DisplayGeneticSystem();
        }
        //if all car dead
        //Select
        //Evolve
        //iteration++
        //Let's GO
    }

    public void DisplayGeneticSystem(){
        System.out.flush();
        System.out.println("Iteration "+iteration);
        System.out.println("Number of cars "+players.size());
        System.out.println("Best Population "+bestPopulation);
        System.out.println("Best Fitness "+bestFitness);
        System.out.println("Best Score "+bestScore);
    }

    public void ActivatesBrain(int delta){
        for (Player player : players) {
            if(player.getCar().isAlive()){
                RenderableObject car = player.getCar();
                ((CarAI)car).processNet();
                car.processInput(InputFactory.generateInputFromAI((CarAI)car), delta);
            }
        }
    }

    public List<Player> CreateNewPopulation(){
        List<Player> p = new ArrayList<>();

        //players.clear();
        for(int i =0;i<carNumber;i++){
            Player newAI = new Player("AI " + iteration + "-" + i, map, true);
            p.add(newAI);
            scoreView.addPlayer(newAI);
        }
        return p;
    }

    public List<Player> EvolvePopulation(){
        scoreView.clearPlayerList();

        // select the top units of the current population to get an array of winners
        // (they will be copied to the next population)
        List<Player> winners = Selection();
        List<Player> offsprings = new ArrayList<>();
        if (this.mutationRate == 1 && ((CarAI)players.get(0).getCar()).getFitness() < 400){
            // If the best unit from the initial population has a negative fitness
            // then it means there is no any bird which reached the first barrier!
            // Playing as the God, we can destroy this bad population and try with another one.
            return CreateNewPopulation();
        } else {
            this.mutationRate = 0.2f; // else set the mutation rate to the real value
            // fill the rest of the next population with new units using crossover and mutation
            for(int i = topUnitsToKeep; i<carNumber;i++){
                Player offspring = new Player("AI " + iteration + "-" + topUnitsToKeep, map, true);
                scoreView.addPlayer(offspring);
                if (i == topUnitsToKeep){
                    // offspring is made by a crossover of two best winners
                    Player parentA = winners.get(0);
                    Player parentB = winners.get(1);
                    offspring = CrossOver(parentA, parentB, offspring.getName());

                } else if (i < carNumber-2){
                    // offspring is made by a crossover of two random winners
                    Player parentA = GetRandomPlayer(winners);
                    Player parentB = GetRandomPlayer(winners);
                    offspring = CrossOver(parentA, parentB, offspring.getName());

                } else {
                    // offspring is a random winner
                    ((CarAI)offspring.getCar()).setNeuralNetwork(((CarAI)GetRandomPlayer(winners).getCar()).getCleanNeuralNetwork());
                }
                // mutate the new population
                offspring = Mutate(offspring);
                ((CarAI)offspring.getCar()).ResetStats();
                offsprings.add(offspring);
            }


            for(int j = 0; j<winners.size();j++) {
                ((CarAI)winners.get(j).getCar()).ResetStats();
            }
            //The new players list is the concatenation of the winners and offsprings generated previously
            //players.clear();
            //players.addAll(winners);
            //players.addAll(offsprings);
            winners.addAll(offsprings);
        }
        return winners;
    }





    public List<Player> Selection(){
        Collections.sort(players);
        // if the top winner has the best fitness in the history, store its achievement!
        if (players.get(0).getCar().getFitness() > bestFitness){
            bestPopulation = this.iteration;
            bestFitness = players.get(0).getCar().getFitness();
            bestScore = players.get(0).getCar().getScore();
            if(bestFitness>saveSystem.getBestScoreEver()){
                try {
                    saveSystem.SavePlayer(players.get(0));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
        for(int i =0;i<this.topUnitsToKeep;i++){
            ((CarAI)players.get(i).getCar()).setWinner(true);
        }
        return players.subList(0, topUnitsToKeep);
        /*
        // sort the units of the current population	in descending order by their fitness
		var sortedPopulation = this.Population.sort(
			function(unitA, unitB){
				return unitB.fitness - unitA.fitness;
			}
		);

		// mark the top units as the winners!
		for (var i=0; i<this.top_units; i++) this.Population[i].isWinner = true;

		// return an array of the top units from the current population
		return sortedPopulation.slice(0, this.top_units);
         */
    }

    public Player CrossOver(Player parentA, Player parentB, String name){
        Net netA = ((CarAI)parentA.getCar()).getCleanNeuralNetwork();
        Net netB = ((CarAI)parentB.getCar()).getCleanNeuralNetwork();
        Player offspringA = new Player(name, map, true);
        Player offspringB = new Player(name, map, true);


        //setup for the cross over
        //cutting the bias weights of parentA in two lists
        Neuron neuronA = netA.GetNet().get(1).GetBiasNeuron();
        int cutPoint = new Random().nextInt(neuronA.GetOutputWeights().size());
        List<Connection> aBeforeCut = neuronA.GetOutputWeights().subList(0,cutPoint);
        List<Connection> aAfterCut = neuronA.GetOutputWeights().subList(cutPoint,neuronA.GetOutputWeights().size());

        //Same thing for B
        Neuron neuronB = netB.GetNet().get(1).GetBiasNeuron();
        List<Connection> bBeforeCut = neuronB.GetOutputWeights().subList(0,cutPoint);
        List<Connection> bAfterCut = neuronB.GetOutputWeights().subList(cutPoint,neuronB.GetOutputWeights().size());

        //merge for parentA net
        List<Connection> finalA = new ArrayList<>();
        finalA.addAll(aBeforeCut);
        finalA.addAll(bAfterCut);
        neuronA.setOutputWeights(finalA);
        netA.GetNet().get(1).SetBiasNeuron(neuronA);
        ((CarAI)offspringA.getCar()).setNeuralNetwork(netA);

        //merge for parentB net
        List<Connection> finalB = new ArrayList<>();
        finalB.addAll(bBeforeCut);
        finalB.addAll(aAfterCut);
        neuronB.setOutputWeights(finalB);
        netB.GetNet().get(1).SetBiasNeuron(neuronB);
        ((CarAI)offspringB.getCar()).setNeuralNetwork(netB);

        return new Random().nextInt(2) == 1 ? offspringA : offspringB;
        //Merge both layer
        /*
        // performs a single point crossover between two parents
        crossOver : function(parentA, parentB) {
            // get a cross over cutting point
            var cutPoint = this.random(0, parentA.neurons.length-1);

            // swap 'bias' information between both parents:
            // 1. left side to the crossover point is copied from one parent
            // 2. right side after the crossover point is copied from the second parent
            for (var i = cutPoint; i < parentA.neurons.length; i++){
                var biasFromParentA = parentA.neurons[i]['bias'];
                parentA.neurons[i]['bias'] = parentB.neurons[i]['bias'];
                parentB.neurons[i]['bias'] = biasFromParentA;
            }

            return this.random(0, 1) == 1 ? parentA : parentB;
        },
        */
    }

    //We choose to mutate the hidden layer only
    public Player Mutate(Player player){

        Layer finalLayer = ((CarAI)player.getCar()).getNeuralNetwork().GetNet().get(1);

        for(int i=0;i<finalLayer.getLayer().size();i++){
            finalLayer.getLayer().get(i).SetWeights(MutateNeuron(finalLayer.getLayer().get(i)));
        }
        ((CarAI)player.getCar()).getNeuralNetwork().GetNet().set(1,finalLayer);
        return player;
        /*
        // performs random mutations on the offspring
        mutation : function (offspring){
            // mutate some 'bias' information of the offspring neurons
            for (var i = 0; i < offspring.neurons.length; i++){
                offspring.neurons[i]['bias'] = this.mutate(offspring.neurons[i]['bias']);
            }

            // mutate some 'weights' information of the offspring connections
            for (var i = 0; i < offspring.connections.length; i++){
                offspring.connections[i]['weight'] = this.mutate(offspring.connections[i]['weight']);
            }

            return offspring;
        },
        */
    }

    public List<Double> MutateNeuron(Neuron neuron){
        List<Double> weights = new ArrayList<>();
        double mutatedWeight;

        for (int i =0;i<neuron.GetOutputWeights().size();i++){
            mutatedWeight = neuron.GetOutputWeights().get(i).getWeight();
            if(new Random().nextFloat()< mutationRate){
                double mutateFactor = (new Random().nextFloat()+0.5);
                mutatedWeight *= mutateFactor;
            }
            weights.add(mutatedWeight);
        }
        return weights;
        /*
        // mutates a gene
        mutate : function (gene){
            if (Math.random() < this.mutateRate) {
                var mutateFactor = 1 + ((Math.random() - 0.5) * 3 + (Math.random() - 0.5));
                gene *= mutateFactor;
            }

            return gene;
        },
        */
    }

    /*
    random : function(min, max){
		return Math.floor(Math.random()*(max-min+1) + min);
	},

     */

    private Player GetRandomPlayer(List<Player> list){
        return list.get(new Random().nextInt(list.size()-1));
    }

    private boolean AreAllCarNotDead(){
        boolean res = false;
        for (Player p: players) {
            res |= p.getCar().isAlive();
        }
        return res;
    }

    public void KillAllCarAI(){
        for (Player p: players) {
            p.getCar().setAlive(false);
        }
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public int getTopUnitsToKeep() {
        return topUnitsToKeep;
    }

    public int getIteration() {
        return iteration;
    }

    public float getMutationRate() {
        return mutationRate;
    }

    public int getBestPopulation() {
        return bestPopulation;
    }

    public double getBestFitness() {
        return bestFitness;
    }

    public double getBestScore() {
        return bestScore;
    }
}


/*
    public Player CrossOver(Player parentA, Player parentB){
        Net netA = ((CarAI)parentA.getCar()).getNeuralNetwork();
        Net netB = ((CarAI)parentB.getCar()).getNeuralNetwork();
        int cutPoint = new Random().nextInt(netA.neuronsHiddenNumber());

        //setup for the cross over
        //cutting the hidden layer of parentA in two lists
        Layer layerA = (Layer) netA.GetNet().get(1).getLayer();
        Layer aBeforeCut = (Layer)layerA.getLayer().subList(0,cutPoint);
        Layer aAfterCut = (Layer)layerA.getLayer().subList(cutPoint,layerA.getLayer().size());

        //Same thing for B
        Layer layerB = (Layer) netB.GetNet().get(1).getLayer();
        Layer bBeforeCut = (Layer)layerB.getLayer().subList(0,cutPoint);
        Layer bAfterCut = (Layer)layerB.getLayer().subList(cutPoint,layerB.getLayer().size());

        //merge for parentA net
        List<Neuron> finalA = new ArrayList<>();
        finalA.addAll((Collection<? extends Neuron>) aBeforeCut);
        finalA.addAll((Collection<? extends Neuron>) bAfterCut);
        netA.GetNet().get(1).setLayer(finalA);
        ((CarAI)parentA.getCar()).setNeuralNetwork(netA);
        //merge for parentB net
        List<Neuron> finalB = new ArrayList<>();
        finalA.addAll((Collection<? extends Neuron>) bBeforeCut);
        finalA.addAll((Collection<? extends Neuron>) aAfterCut);
        netB.GetNet().get(1).setLayer(finalB);
        ((CarAI)parentA.getCar()).setNeuralNetwork(netB);

        return new Random().nextInt(2) == 1 ? parentA : parentB;
     }
 */