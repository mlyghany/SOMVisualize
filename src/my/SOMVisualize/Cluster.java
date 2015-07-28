/*
 * To change lattice template, choose Tools | Templates
 * and open the template in the editor.
 */
package my.SOMVisualize;

import java.awt.Color;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mark Lester
 */
public class Cluster extends SOMLattice implements Runnable, Serializable{

    private int numberOfClusters;
    private SOMLattice lattice;
    
    public Cluster(SOMLattice lattice, int numberOfClusters){
        
        this.numberOfClusters = numberOfClusters;
        this.lattice = new SOMLattice();
        
        
        
        
        initializeLattice(lattice);
        
    }

    public int getNumberOfClusters() {
        return numberOfClusters;
    }
    
    private void initializeLattice(SOMLattice lattice){
        this.lattice.setLatticeHeight(lattice.getLatticeHeight());
        this.lattice.setLatticeWidth(lattice.getLatticeWidth());
        this.lattice.setNumberOfNodeElements(lattice.getNumberOfNodeElements());
        
        this.lattice.initializeValues();
        
        for (int i=0; i<this.lattice.getTotalNumberOfNodes(); i++){
            this.lattice.getLatticeNode()[i] = new Node(lattice.getLatticeNode()[i]);
        }
        
        //this.lattice.setLatticeNode(lattice.getLatticeNode());
        this.lattice.setNodeHeight(lattice.getNodeHeight());
        this.lattice.setNodeWidth(lattice.getNodeWidth());
        this.lattice.setTotalNumberOfNodes(lattice.getTotalNumberOfNodes());
    }
    
    public SOMLattice getLattice() {
        return lattice;
    }
    
    private void initializeCluster(){
       
        double[][] centroids = new double[this.numberOfClusters][lattice.getNumberOfNodeElements()];
        for(int i=0; i<this.numberOfClusters; i++){
            for(int j=0; j<lattice.getNumberOfNodeElements(); j++){
                centroids[i][j] = Math.random();
            }
        }
        
        boolean changeFlag = true;
        
        while(changeFlag){
            changeFlag = false;
            
            for(int i=0; i<lattice.getTotalNumberOfNodes(); i++){
                double[] distance = new double[this.numberOfClusters];
                double minDistance = 0;
                int clusterIndex = 0;
                
                for(int j=0; j<this.numberOfClusters; j++){
                    distance[j] = lattice.getLatticeNode()[i].getDistance(centroids[j]);
                }
                
                minDistance = distance[0];
                for(int j=1; j<this.numberOfClusters; j++){
                    
                    if(distance[j] < minDistance){
                        minDistance = distance[j];
                        clusterIndex = j;
                    }
                    
                }
                
                if(lattice.getLatticeNode()[i].getCluster() != clusterIndex){
             //      System.out.println(i +": "+lattice.getLatticeNode()[i].getCluster() + " " + clusterIndex);
                    int clusterColor = 1024/this.numberOfClusters*clusterIndex;
                    Color nodeColor = new Color(0,0,0);
                    if(clusterColor < 256){
                        nodeColor = new Color(0,(clusterColor % 256),255);
                    }
                    else if(clusterColor < 512){
                        nodeColor = new Color(0,255,255 - (clusterColor % 256));
                    }
                    else if(clusterColor < 768){
                        nodeColor = new Color((clusterColor % 256),255,0);
                    }
                    else{
                        nodeColor = new Color(255,255 - (clusterColor % 256),0);
                    }
                    lattice.getLatticeNode()[i].setNodeColor(nodeColor);
                    lattice.getLatticeNode()[i].setCluster(clusterIndex);
                    //System.out.println("Change! " + i);
                    changeFlag = true;
                }
            }
            
            centroids = new double[this.numberOfClusters][lattice.getNumberOfNodeElements()];
            int clusterCounter[] = new int[this.numberOfClusters];
            for(int i=0; i<lattice.getTotalNumberOfNodes(); i++){
                for(int j=0; j<lattice.getNumberOfNodeElements(); j++){
                    centroids[lattice.getLatticeNode()[i].getCluster()][j] += lattice.getLatticeNode()[i].getDoubleElementAt(j);
                }
                clusterCounter[lattice.getLatticeNode()[i].getCluster()]++;
            }
            
           
            for(int i=0; i<this.numberOfClusters; i++){
                for(int j=0; j<lattice.getNumberOfNodeElements(); j++){
                    centroids[i][j] = centroids[i][j]/clusterCounter[i];
                }
            }
        }
    }

    @Override
    public void run() {
        this.initializeCluster();
        try {
            Thread.sleep((int)Math.random()*10);
        } catch (InterruptedException ex) {
            Logger.getLogger(Cluster.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}