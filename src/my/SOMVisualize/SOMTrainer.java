/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package my.SOMVisualize;

import java.awt.*;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mark Lester
 */
public class SOMTrainer implements Runnable, Serializable{
    
    private InputData inputData;
    
    private int mapWidth;
    private int mapHeight;
    
    private double initialLearningRate;
    private double finalLearningRate;
    
    private int currIter = 0;
    private int iterations;
    
    private int prevValue = 0;
    
    
    private int learningRateFunction; // 1 for exponential, 2 for power series, 3 for custom
    private int neighborhoodFunction; // 1 for exponential, 2 for custom
    private int nodeInfluenceFunction; // 1 for Gaussian, 2 for custom
    
    private boolean doneTraining = false;
    private boolean stopTraining = false;
    private boolean pauseTraining = false;
    
    private SOMLattice lattice;
    
    private ConsoleLog cl;
    
    public SOMTrainer(InputData inputData, int mapWidth, int mapHeight, double initialLearningRate, double finalLearningRate, int iterations, 
                        int learningRateFunction, int neighborhoodFunction, int nodeInfluenceFunction, ConsoleLog cl){
        
        this.inputData = inputData;
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        this.initialLearningRate = initialLearningRate;
        this.finalLearningRate = finalLearningRate;
        this.iterations = iterations;
        
        this.learningRateFunction = learningRateFunction;
        this.neighborhoodFunction = neighborhoodFunction;
        this.nodeInfluenceFunction = nodeInfluenceFunction;
        this.cl = cl;
        
        
        //train();
        
    }
    
    public SOMTrainer(InputData inputData, ConsoleLog cl){
        this(inputData,40,40,0.7,0.001,500,1,1,1, cl);
    }
    
    private boolean train(){
        
        
        if(doneTraining){   //do not allow
            return true;    //retraining
        }                   //of the map
        
        
        /*
         * Initialize the map and the nodes
         */
        
        if(currIter == 0){
            lattice = new SOMLattice(this.mapWidth, this.mapHeight, inputData.getCols()); /* original implementation*/
        }

        while(currIter < iterations){    
            /*
             * If stop button is pressed. Stop the thread and training!
             */
            if(stopTraining || pauseTraining){
                break;
            }
            
            /*
             * Choose an input vector randomly from the set.
             */
            
            int timesHundred = currIter*100;
            if(prevValue == (timesHundred/iterations)-10){
                
                System.out.println(prevValue);
                cl.throwNormalMessage("Training in progress: " + timesHundred/iterations + "% completed\n");
                prevValue = timesHundred/iterations;
            }
           
            int inputDataIndex = (int)Math.floor(Math.random()*(inputData.getRows()));

            /*
             * Find best matching unit of the chosen input vector
             */
            
            Node winningNode = lattice.getBMU(inputData.getInputData()[inputDataIndex]);
            inputData.setOneBMU(inputDataIndex, winningNode.getNodeIndex());
             /*
             * Calculate the width of the neighborhood at this timestep
             */

            double neighborhoodRadius = 0;

            if(neighborhoodFunction == 1){
                neighborhoodRadius = lattice.getExponentialNeighborhood(currIter, iterations);
            }
            else{
                //TODO: custom parsing here.
            }
            
            /*
             * Compute for the current learning rate. 
             */
            double currLearningRate = 0;
            if(learningRateFunction==1){
                currLearningRate = lattice.getExponentialLearning(initialLearningRate, currIter, iterations);
            }
            else if(learningRateFunction==2){
                currLearningRate = lattice.getPowerSeriesLearning(initialLearningRate, finalLearningRate, currIter, iterations);
            }
            else{
                //TODO: custom parsing here
            }
            
            /*
             * Adjust the lattice.
             */
            lattice.adjustNeighborhood(winningNode, neighborhoodRadius, currLearningRate, inputData.getInputData()[inputDataIndex], nodeInfluenceFunction);
            
            currIter++;
        }
        
        if(!stopTraining && !pauseTraining){
            doneTraining = true;
            cl.throwSuccessMessage("Training Completed! \n");
            cl.throwNormalMessage("Enabling Visualization Buttons. \n");
        }
        return false;
        
        
        
    }

    public SOMLattice getLattice() {
        return lattice;
    }

    public InputData getInputData() {
        return inputData;
    }

    public boolean isDoneTraining() {
        return doneTraining;
    }
    
    @Override
    public void run() {
        stopTraining = false;
        pauseTraining = false;
        //throw new UnsupportedOperationException("Not supported yet.");
        this.train();
        /*try {
            Thread.sleep((int)Math.random()*10);
        } catch (InterruptedException ex) {
            Logger.getLogger(SOMTrainer.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        
    }
    
    public void stop(){
        stopTraining = true;
    }
    
    public void pause(){
        pauseTraining = true;
    }

    public void setCl(ConsoleLog cl) {
        this.cl = cl;
    }
    
    
        
}
