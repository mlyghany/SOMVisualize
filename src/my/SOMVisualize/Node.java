/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package my.SOMVisualize;

/**
 *
 * @author Mark Lester
 */

import java.awt.Color;

public class Node extends java.util.Vector{
    
    private int xPos; /* X and Y positions */
    private int yPos; /* of the node in the map */
    
    private int numberOfElements;
    
    private int cluster;
    private Color nodeColor; //used for visualization purposes
    
    private int nodeIndex;
    
    public Node(int xPos, int yPos, int numberOfElements, int nodeIndex){
        this.xPos = xPos;
        this.yPos = yPos;
        this.numberOfElements = numberOfElements;
        this.cluster = -1;
        this.nodeIndex = nodeIndex;
        
        for(int i=0; i<this.numberOfElements; i++){ //this is a dummy initializer, should change later on.
            this.addElement(Math.random());
        }
    }
    
    public Node(Node node){
        this.xPos = node.xPos;
        this.yPos = node.yPos;
        this.numberOfElements = node.numberOfElements;
        this.cluster = -1;
        this.nodeIndex = node.getNodeIndex();
        for(int i=0; i<this.numberOfElements; i++){ //this is a dummy initializer, should change later on.
            this.addElement(node.getDoubleElementAt(i));
        }
    }

    public int getNodeIndex() {
        return nodeIndex;
    }
    
    
    public int getXPos(){
        return xPos;
    }
    
    public int getYPos(){
        return yPos;
    }
    
    public void setNodeColor(Color nodeColor){
        this.nodeColor = nodeColor;
    }
    
    public Color getNodeColor(){
        return nodeColor;
    }
    
    public double getDoubleElementAt(int index){
        
        double element = (Double)this.elementAt(index);
        
        return element;
    }
    
    public void setDoubleElementAt(double value,int index){
        this.setElementAt(value, index);
    }
    
    public double getDistance(double[] inputVector){
       
        double distance = 0;
        
        for(int i=0; i<inputVector.length; i++){
           distance += Math.pow((this.getDoubleElementAt(i) - inputVector[i]), 2);
           
        }
        
        return Math.sqrt(distance);
    }
    
    public double getDistance(Node inputVector){
       
        double distance = 0;
        
        for(int i=0; i<inputVector.size(); i++){
           distance += Math.pow((this.getDoubleElementAt(i) - inputVector.getDoubleElementAt(i)), 2);
           
        }
        
        return Math.sqrt(distance);
    }
    
    public void adjustWeights(double[] inputVector, double learningRate, double bmuInfluence){
        for(int i=0; i<this.numberOfElements; i++){
            this.setDoubleElementAt((this.getDoubleElementAt(i) + bmuInfluence*learningRate*(inputVector[i] - this.getDoubleElementAt(i))), i);
            
            //these are partial codes
            this.setNodeColor(new Color((int)(this.getDoubleElementAt(0)*255),
                                                    (int)(this.getDoubleElementAt(1)*255),
                                                       (int)(this.getDoubleElementAt(2)*255)));
            
        }
    }

    public int getCluster() {
        return cluster;
    }

    public void setCluster(int cluster) {
        this.cluster = cluster;
    }
    
    
    
}
