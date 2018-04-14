package Model.NeuralNetwork;

import Model.Game.CarAI;
import Model.Game.Player;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SaveNetSystem {

    double bestScoreEver;
    String filename = "save.txt";

    public SaveNetSystem(String filename) throws IOException {
        this.filename = filename;
        this.bestScoreEver = LoadBestScoreEver();
    }

    public void SavePlayer(Player player) throws FileNotFoundException, UnsupportedEncodingException {
        Net net = ((CarAI)player.getCar()).getNeuralNetwork();
        bestScoreEver=player.getCar().getScore();
        PrintWriter writer = new PrintWriter(filename, "UTF-8");
        writer.println(bestScoreEver);//Best Score ever
        int numLayers = net.getTopology().size();
        writer.println(numLayers);//topology informations
        for (int layerNum = 0; layerNum < numLayers; ++layerNum){
            if(layerNum == numLayers-1){
                writer.println(net.getTopology().get(layerNum));//topology informations
            }else{
                writer.print(net.getTopology().get(layerNum)+",");//topology informations
            }
        }

        //Whole net
        for (int layerNum = 0; layerNum < numLayers-1; ++layerNum)
        {
            Layer layer = net.getLayers().get(layerNum);

            for (int neuronNum = 0; neuronNum <= net.getTopology().get(layerNum); ++neuronNum)
            {
                List<Double> weights = new ArrayList<>();
                Neuron neuron = layer.getLayer().get(neuronNum);
                for (int weightNum = 0; weightNum<neuron.GetOutputWeights().size();weightNum++){
                    if(weightNum==neuron.GetOutputWeights().size()-1){
                        writer.println(neuron.GetOutputWeights().get(weightNum).weight);
                    }else{
                        writer.print(neuron.GetOutputWeights().get(weightNum).weight+",");
                    }
                }
            }
        }
        writer.close();
    }

    public double LoadBestScoreEver() throws IOException {
        FileReader input = new FileReader(filename);
        BufferedReader bufRead = new BufferedReader(input);
        String scoreLine = null;
        double res = 0.0;
        if((scoreLine = bufRead.readLine()) != null) {
            res = Double.parseDouble(scoreLine);
        }
        input.close();
        return res;
    }

    public double getBestScoreEver() {
        return bestScoreEver;
    }

    public String getFilename() {
        return filename;
    }

}
