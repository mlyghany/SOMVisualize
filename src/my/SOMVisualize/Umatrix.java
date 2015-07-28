/*
 * To change lattice template, choose Tools | Templates
 * and open the template in the editor.
 */
package my.SOMVisualize;

import java.awt.*;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;

/**
 *
 * @author Mark Lester
 */
public class Umatrix extends JPanel implements Runnable, Serializable{

    private int neighborhoodSize;
    private SOMLattice lattice;
    private double maxDistance;
    private double minDistance;
    
    private double origMaxValue;
    private double origMinValue;
    
    private InputData inputData;
    
    
    public Umatrix(SOMLattice lattice, int neighborhoodSize, InputData inputData){
        
        this.neighborhoodSize = neighborhoodSize;
        this.lattice = new SOMLattice();
        this.inputData = inputData;
        
        
        
        
        initializeLattice(lattice);
    }

    public double getMaxDistance() {
        return maxDistance;
    }

    public double getMinDistance() {
        return minDistance;
    }
    
    public void setOrigMaxMin(double maxValue, double minValue){
        this.origMaxValue = maxDistance * (maxValue-minValue);
        this.origMinValue = minDistance * (maxValue-minValue);
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

    private void initializeUmatrix(){
        
        double[] averageDistances = new double[lattice.getTotalNumberOfNodes()];
        double neighborhoodRadiusSquared = Math.pow((neighborhoodSize*Math.max(lattice.getNodeWidth(), lattice.getNodeHeight())), 2);
        maxDistance = 0;
        minDistance = 0;
        
        for(int i=0; i<lattice.getTotalNumberOfNodes(); i++){
            
            double distance = 0;
            double neighbors = 0;
            
            for(int j=0; j<lattice.getTotalNumberOfNodes(); j++){
                double distanceToNodeSquared = Math.pow((lattice.getLatticeNode()[i].getXPos() - lattice.getLatticeNode()[j].getXPos()), 2) +
                                                Math.pow((lattice.getLatticeNode()[i].getYPos() - lattice.getLatticeNode()[j].getYPos()), 2);
            
                if(distanceToNodeSquared <= neighborhoodRadiusSquared){
                   distance += lattice.getLatticeNode()[i].getDistance(lattice.getLatticeNode()[j]);
                   neighbors++;
                }
            }
            
            
            averageDistances[i] = distance/(neighbors-1);
           
            if(i == 0){
                
                maxDistance = averageDistances[i];
                minDistance = averageDistances[i];
            }
            else{
                if(averageDistances[i]> maxDistance){
                    maxDistance = averageDistances[i];
                }
                
                if(averageDistances[i] < minDistance){
                    minDistance = averageDistances[i];
                }
            }
        }
        
        for(int i=0; i<lattice.getTotalNumberOfNodes(); i++){
            lattice.getLatticeNode()[i].setNodeColor(new Color((int)(255-(averageDistances[i]-minDistance)/(maxDistance-minDistance)*255),
                                                    (int)(255-(averageDistances[i]-minDistance)/(maxDistance-minDistance)*255),
                                                       (int)(255-(averageDistances[i]-minDistance)/(maxDistance-minDistance)*255)));
                   
        }
        
        
        
    }
    
    @Override
    public void run() {
        //throw new UnsupportedOperationException("Not supported yet.");
        this.initializeUmatrix();
        try {
            Thread.sleep((int)Math.random()*10);
        } catch (InterruptedException ex) {
            Logger.getLogger(SOMTrainer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void paintComponent(Graphics g){
//        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        
        g.translate(this.getWidth()/2 - 225, this.getHeight()/2 - 240);
        for(int i=0; i<lattice.getTotalNumberOfNodes(); i++){
            
            g2.setColor(lattice.getLatticeNode()[i].getNodeColor());
            g2.fillRect(lattice.getLatticeNode()[i].getXPos() - lattice.getNodeWidth()/2 ,lattice.getLatticeNode()[i].getYPos() - lattice.getNodeHeight()/2 ,lattice.getNodeWidth(),lattice.getNodeHeight());
            g2.setColor(Color.BLACK);
            g2.drawRect(lattice.getLatticeNode()[i].getXPos() - lattice.getNodeWidth()/2 ,lattice.getLatticeNode()[i].getYPos() - lattice.getNodeHeight()/2 ,lattice.getNodeWidth(),lattice.getNodeHeight());
            
        }
        
        g.setColor(Color.BLACK);
        
        for(int i=0; i<inputData.getRows(); i++){
            g.drawString(inputData.getDataLabels()[i], lattice.getLatticeNode()[inputData.getInputDataBMUs()[i]].getXPos(), lattice.getLatticeNode()[inputData.getInputDataBMUs()[i]].getYPos());
        }
        
            double hund = 100;
            int intMaxValue = (int)(origMaxValue*hund);
            int intMinValue = (int)(origMinValue*hund);
            
            g.setColor(Color.BLACK);
            g.drawString(Double.toString(intMaxValue/hund), 0, 470);
            for(int i=0; i<256; i++){
                g.setColor(new Color(i,i,i));
                double width = 350;
                g.fillRect((int)Math.ceil(i*width/255) + 50, 455, (int)Math.ceil(width/255), 15);
            }
            g.setColor(Color.BLACK);
            g.drawRect(50, 455, 350, 15);
            g.drawString(Double.toString(intMinValue/hund), 405, 470);
        

    }
    

}
