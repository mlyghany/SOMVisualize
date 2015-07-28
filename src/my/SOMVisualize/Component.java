/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package my.SOMVisualize;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.Serializable;
import javax.swing.*;

/**
 *
 * @author Mark Lester
 */
public class Component extends JPanel implements Serializable{
    
    private SOMLattice lattice;
    private int componentNumber;
    private double minValue;
    private double maxValue;
    
    private double origMaxValue;
    private double origMinValue;
    
    public Component(SOMLattice lattice, int componentNumber){
        this.lattice = new SOMLattice();
        this.componentNumber = componentNumber;
        
        initializeLattice(lattice);
        initializeComponentPlane();
        
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
    
    private void initializeComponentPlane(){
        
        maxValue = lattice.getLatticeNode()[0].getDoubleElementAt(componentNumber);
        minValue = lattice.getLatticeNode()[0].getDoubleElementAt(componentNumber);
        
        for(int i=1; i<lattice.getTotalNumberOfNodes(); i++){
            
            if(lattice.getLatticeNode()[i].getDoubleElementAt(componentNumber) < minValue){
                minValue = lattice.getLatticeNode()[i].getDoubleElementAt(componentNumber);
            }
            if(lattice.getLatticeNode()[i].getDoubleElementAt(componentNumber) > maxValue){
                maxValue = lattice.getLatticeNode()[i].getDoubleElementAt(componentNumber);
            }
            
        }
        
        for(int i=0; i<lattice.getTotalNumberOfNodes(); i++){
            Node currNode = lattice.getLatticeNode()[i];
            int colorValue =  (int)Math.round((currNode.getDoubleElementAt(componentNumber) - minValue)/(maxValue-minValue)* 1020);
            Color nodeColor =  new Color(0);
            int caseValue = colorValue/256;
            
            //if(componentNumber == 4)
            //System.out.println(caseValue + " " + currNode.getDoubleElementAt(4));
            
            if(caseValue == 0){
                nodeColor = new Color(0,(colorValue % 256),255);
            }
            else if(caseValue == 1){
                nodeColor = new Color(0,255,255 - (colorValue % 256));
            }
            else if(caseValue == 2){
                nodeColor = new Color((colorValue % 256),255,0);
            }
            else{
                nodeColor = new Color(255,255 - (colorValue % 256),0);
            }
            
            lattice.getLatticeNode()[i].setNodeColor(nodeColor);
            
        }
    }

    public double getMaxValue() {
        return maxValue;
    }

    public double getMinValue() {
        return minValue;
    }
    
    public void setOrigMaxMin(double maxValue, double minValue){
        this.origMaxValue = this.maxValue * (maxValue-minValue) + minValue;
        this.origMinValue = this.minValue * (maxValue-minValue) + minValue;
    }
    
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        
        g2.scale(0.5, 0.5);
        
        for(int i=0; i<lattice.getTotalNumberOfNodes(); i++){
            
            g.setColor(lattice.getLatticeNode()[i].getNodeColor());
            g.fillRect(lattice.getLatticeNode()[i].getXPos() - lattice.getNodeWidth()/2 ,lattice.getLatticeNode()[i].getYPos() - lattice.getNodeHeight()/2 ,lattice.getNodeWidth(),lattice.getNodeHeight());
            g.setColor(Color.BLACK);
            g.drawRect(lattice.getLatticeNode()[i].getXPos() - lattice.getNodeWidth()/2 ,lattice.getLatticeNode()[i].getYPos() - lattice.getNodeHeight()/2 ,lattice.getNodeWidth(),lattice.getNodeHeight());
            
        }
        
        g.setColor(Color.BLACK);
        double hund = 100;
        int intMaxValue = (int)(origMaxValue*hund);
        int intMinValue = (int)(origMinValue*hund);
        g.setFont(new Font("Arial", Font.PLAIN, 18));
        g.drawString(Double.toString(intMinValue/hund), 0, 470);
        g.drawString(Double.toString(intMaxValue/hund), 405, 470);

        for(int i=0; i<1024; i++){
            if(i < 256){
                g.setColor(new Color(0,(i % 256),255));
            }
            else if(i < 512){
                g.setColor(new Color(0,255,255 - (i % 256)));
            }
            else if(i < 768){
                g.setColor(new Color((i % 256),255,0));
            }
            else{
                g.setColor(new Color(255,255 - (i % 256),0));
            }
            double width = 350;
            g.fillRect((int)Math.ceil(i*width/1024) + 50, 455, (int)Math.ceil(width/1024), 15);

        }
        g.setColor(Color.BLACK);
        g.drawRect(50, 455, 350, 15);
    }
}
