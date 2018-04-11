import Model.NeuralNetwork.Net;
import com.sun.net.httpserver.Authenticator;
import sun.font.CreatedFontTracker;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class IAMain {

    static boolean IS_TRAINING = true;
    static int NUMBER_OF_TESTS = 6000;

    public static void CreateTestFile() throws FileNotFoundException, UnsupportedEncodingException {
        PrintWriter writer = new PrintWriter("testXOR.txt", "UTF-8");
        for(int i=0;i<=NUMBER_OF_TESTS;i++){
            int a = new Random().nextInt(2);
            int b = new Random().nextInt(2);
            int c = new Random().nextInt(2);
            int d = a ^ b ^ c;
            writer.println(a+","+b+","+c);
            writer.println(d);
            /*if(c==0){
                writer.println("1,1");
                writer.println("0");
            }*/
        }
        writer.close();
    }

    public static void Train() throws IOException {
        double success = 0;
        double total = 0;
        int run = 0;
        //creation of the net
        List<Integer> topology = new ArrayList<>();
        topology.add(3);
        topology.add(3);
        topology.add(1);
        Net myNet = new Net(topology);


        //Set all the lists use to train the net
        List<Double> inputVals = new ArrayList<>();
        List<Double> targetVals = new ArrayList<>();
        List<Double> resultVals = new ArrayList<>();

        //Fill inputVals and TargetsVals from a training data file for each iteration
        FileReader input = new FileReader("testXOR.txt");
        BufferedReader bufRead = new BufferedReader(input);
        String inputLine = null;
        String targetLine= null;
        while ( (inputLine = bufRead.readLine()) != null && run < NUMBER_OF_TESTS)
        {
            if((targetLine = bufRead.readLine()) != null){
                String[] inputs = inputLine.split(",");
                String[] targets = targetLine.split(",");
                for (int i = 0; i < inputs.length; i++) {
                    inputVals.add(Double.parseDouble(inputs[i]));
                }
                for (int i = 0; i < targets.length; i++) {
                    targetVals.add(Double.parseDouble(targets[i]));
                }
            }
            myNet.FeedForward(inputVals);

            //get actual result
            resultVals = myNet.GetResult();
            if(run<=NUMBER_OF_TESTS*2/3){
                myNet.BackProp(targetVals);
            }else{
                total++;
                if(Math.abs(targetVals.get(0)-resultVals.get(0)) <0.50){
                    success++;
                }
            }

            System.out.println("This is the iteration number : "+run);
            System.out.println("Inputs : "+inputVals);
            System.out.println("Target value : "+targetVals);
            System.out.println("Real Value : "+resultVals);
            System.out.println("Recent Average Error : "+myNet.GetRecentAverageError()+"\n\n\n");

            inputVals.clear();
            targetVals.clear();
            resultVals.clear();
            run++;
        }
        System.out.println("Success Ratio "+ success/total+"\n\n\n");
        //System.out.println(myNet.toString());
    }

    public static void main(String[] args) throws IOException {
        if (IS_TRAINING) {
            Train();
        } else {
            try {
                CreateTestFile();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }
}
