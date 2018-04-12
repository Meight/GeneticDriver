package Model.NeuralNetwork;

import Model.Game.Player;
import Model.Game.RenderableObject;
import Model.Game.Track.CarAI;
import Model.Network.InputFactory;
import org.newdawn.slick.tiled.TiledMap;

import java.util.ArrayList;
import java.util.List;

public class GeneticSystem {
    List<Player> players;
    int topUnitsToKeep;
    int iteration;
    float mutationRate;
    int bestPopulation;
    double bestFitness;
    double bestScore;

    public GeneticSystem(int carNumber, TiledMap map,int topUnitsToKeep) {
        this.topUnitsToKeep=topUnitsToKeep;
        players = new ArrayList<>();
        for(int i =0;i<carNumber;i++){
            players.add(new Player("AI", map, true));
        }
        this.iteration = 0;
        this.mutationRate = 1.0f;
        this.bestPopulation = 0;
        this.bestFitness = 0;
        this.bestScore = 0;
    }

    public void Update(int delta){
        ActivatesBrain(delta);
    }

    public void ActivatesBrain(int delta){
        for (Player player : players) {
            RenderableObject car = player.getCar();
            ((CarAI)car).ProcessNet();
            car.processInput(InputFactory.generateInputFromAI((CarAI)car), delta);
        }
    }



    public void EvolvePopulation(){
        // select the top units of the current population to get an array of winners
        // (they will be copied to the next population)

        // If the best unit from the initial population has a negative fitness
        // then it means there is no any bird which reached the first barrier!
        // Playing as the God, we can destroy this bad population and try with another one.
        // else set the mutatation rate to the real value

        // fill the rest of the next population with new units using crossover and mutation
        // 1 offspring is made by a crossover of two best winners
        // Max - 3 offspring is made by a crossover of two random winners
        //2 offsprings is a random winner
        //for a total of 10 new Cars

        // mutate the offspring

        // create a new unit using the neural network from the offspring

        // if the top winner has the best fitness in the history, store its achievement!
    }

    public void Select(){
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

    public void CrossOver(){
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

    public void Mutation(){
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

    public void MutateNeuron(){
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
