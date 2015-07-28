/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package my.SOMVisualize;

import java.awt.Color;
import java.io.Serializable;

/**
 *
 * @author Mark Lester
 */
public class SOMLattice implements Serializable{
    
    private int latticeWidth;
    private int latticeHeight;
    
    private int numberOfNodeElements; // number of elements inside a Node
    
    private int nodeWidth;
    private int nodeHeight;
    private int totalNumberOfNodes;
    
    private Node[] latticeNode;
    
    private final int MAP_RADIUS = 225;
    
    public SOMLattice(int latticeWidth, int latticeHeight, int numberOfNodeElements){
   
        this.latticeWidth = latticeWidth;
        this.latticeHeight = latticeHeight;
        this.numberOfNodeElements = numberOfNodeElements;
        
        initializeLattice();
    }
    
    public SOMLattice(){
       this(10,10,3);
    }
    
    public void initializeValues(){
        totalNumberOfNodes = this.latticeWidth*this.latticeHeight;
           
        latticeNode = new Node[totalNumberOfNodes]; //specify the array of nodes     
        
        nodeWidth = (int)Math.floor(450/this.latticeWidth);   //compute for the height and width of the node throughout  
        nodeHeight = (int)Math.floor(450/this.latticeHeight); // the training. this should be constant.
    }
    protected void initializeLattice(){
        /*This part should create the Nodes*/
        /*Its for the lattice*/
        
        totalNumberOfNodes = this.latticeWidth*this.latticeHeight;
           
        latticeNode = new Node[totalNumberOfNodes]; //specify the array of nodes     
        
        nodeWidth = (int)Math.floor(450/this.latticeWidth);   //compute for the height and width of the node throughout  
        nodeHeight = (int)Math.floor(450/this.latticeHeight); // the training. this should be constant.
        
        
        
        for(int i=0; i<totalNumberOfNodes; i++){
            latticeNode[i] = new Node(((i%this.latticeWidth)*nodeWidth) + nodeWidth/2,((i/this.latticeWidth)*nodeHeight) + nodeHeight/2, numberOfNodeElements, i);
            latticeNode[i].setNodeColor(new Color((int)(latticeNode[i].getDoubleElementAt(0)*255),
                                                    (int)(latticeNode[i].getDoubleElementAt(1)*255),
                                                       (int)(latticeNode[i].getDoubleElementAt(2)*255)));
        }
        
        
        
    }
    
        
    
    /*public int getLatticeSize(){
        return totalNumberOfNodes;
    }*/
    
    public Node getBMU(double[] inputVector){
        
        double minDistance = 0;
        Node BMU = null;
        
        for(int i=0; i<this.getTotalNumberOfNodes(); i++){
            double distance = latticeNode[i].getDistance(inputVector);
                   
            if(i==0){
                minDistance = distance;
                
            }
            
            if(distance <= minDistance){
                minDistance = distance;
                BMU = latticeNode[i];
            }
            
            //System.out.println(i);
            
        }
        
        return BMU;
    }
    
    public double getExponentialNeighborhood(int currIter, int totalIter){
        double timeConstant = totalIter/Math.log(MAP_RADIUS);
        
        return (MAP_RADIUS * Math.exp(-(double)currIter/timeConstant));
    }
    
    public double getExponentialLearning(double initialLearningRate, int currIter, int totalIter){
        double timeConstant = totalIter/Math.log(MAP_RADIUS);
        
        return (initialLearningRate * Math.exp(-(double)currIter/timeConstant));
    }
    public double getPowerSeriesLearning(double initialLearningRate, double finalLearningRate, int currIter, int totalIter){
        return (initialLearningRate * Math.pow((finalLearningRate/initialLearningRate), (currIter/totalIter)));
    }
    
   public void adjustNeighborhood(Node winningNode, double neighborhoodRadius, double currLearningRate, double[] inputVector,int nodeInfluenceFunction){
       for(int i=0; i<this.getTotalNumberOfNodes(); i++){
           /*
            * Calculate Euclidean Distance (squared) to this node from the BMU
            * 
            */
           
           double distanceToNodeSquared = Math.pow((latticeNode[i].getXPos() - winningNode.getXPos()), 2) +
                                          Math.pow((latticeNode[i].getYPos() - winningNode.getYPos()), 2);
           
           double neighborhoodSquared = Math.pow(neighborhoodRadius, 2);
           double bmuInfluence = 0.000000000;
           
           if(distanceToNodeSquared < neighborhoodSquared){
               if(nodeInfluenceFunction == 1){
                   bmuInfluence = Math.exp(-(distanceToNodeSquared)/(2*neighborhoodSquared));
                   
               }
               else{
                   //TODO : custom parsing here
               }
               
               //System.out.println(distanceToNodeSquared + " " + neighborhoodSquared + " " + bmuInfluence);
               latticeNode[i].adjustWeights(inputVector, currLearningRate, bmuInfluence);
               
               
               
              // System.out.println(latticeNode[i].getDoubleElementAt(0) + " " + latticeNode[i].getDoubleElementAt(1) + " " + latticeNode[i].getDoubleElementAt(2));
           }
       }
   }
   
   public int getLatticeHeight() {
        return latticeHeight;
    }

    public void setLatticeHeight(int latticeHeight) {
        this.latticeHeight = latticeHeight;
    }

    public Node[] getLatticeNode() {
        return latticeNode;
    }

    public void setLatticeNode(Node[] latticeNode) {
        this.latticeNode = latticeNode;
    }

    public int getLatticeWidth() {
        return latticeWidth;
    }

    public void setLatticeWidth(int latticeWidth) {
        this.latticeWidth = latticeWidth;
    }

    public int getNodeHeight() {
        return nodeHeight;
    }

    public void setNodeHeight(int nodeHeight) {
        this.nodeHeight = nodeHeight;
    }

    public int getNodeWidth() {
        return nodeWidth;
    }

    public void setNodeWidth(int nodeWidth) {
        this.nodeWidth = nodeWidth;
    }

    public int getNumberOfNodeElements() {
        return numberOfNodeElements;
    }

    public void setNumberOfNodeElements(int numberOfNodeElements) {
        this.numberOfNodeElements = numberOfNodeElements;
    }

    public int getTotalNumberOfNodes() {
        return totalNumberOfNodes;
    }

    public void setTotalNumberOfNodes(int totalNumberOfNodes) {
        this.totalNumberOfNodes = totalNumberOfNodes;
    }
    
    
}
