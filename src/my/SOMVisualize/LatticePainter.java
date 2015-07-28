/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package my.SOMVisualize;

import java.awt.image.*;
import java.awt.*;
import javax.swing.JPanel;
/**
 *
 * @author Mark Lester
 */
public class LatticePainter extends JPanel{
    
    private SOMLattice lattice;
    private InputData inputData;
    private int latticeType = 0;
    
    private int maxValue;
    private int minValue;
    
    
    
    public LatticePainter(SOMLattice lattice, InputData inputData){
        this.lattice = lattice;
        this.inputData = inputData;
    }
    
    public LatticePainter(SOMLattice lattice, InputData inputData, int latticeType){
        this.lattice = lattice;
        this.inputData = inputData;
        this.latticeType = latticeType;
    }
    
    
       
    public void setInputData(InputData inputData) {
        this.inputData = inputData;
    }

    public void setLattice(SOMLattice lattice) {
        this.lattice = lattice;
    }

    public void setLatticeType(int latticeType){
        this.latticeType = latticeType;
    }
    
    public void setMaxMin(int max, int min){
        this.maxValue = max;
        this.minValue = min;
    }
    
    
    public void paintComponent(Graphics g){
        
        if(latticeType == 3){
            Graphics2D g2 = (Graphics2D)g;

            g2.scale(0.5, 0.5);

        }
        else{
            g.translate(this.getWidth()/2 - 225, this.getHeight()/2 - 240);
        }
        for(int i=0; i<lattice.getTotalNumberOfNodes(); i++){
            
            g.setColor(lattice.getLatticeNode()[i].getNodeColor());
            g.fillRect(lattice.getLatticeNode()[i].getXPos() - lattice.getNodeWidth()/2, lattice.getLatticeNode()[i].getYPos() - lattice.getNodeHeight()/2, lattice.getNodeWidth(), lattice.getNodeHeight());
            g.setColor(java.awt.Color.BLACK); 
            g.drawRect(lattice.getLatticeNode()[i].getXPos() - lattice.getNodeWidth()/2, lattice.getLatticeNode()[i].getYPos() - lattice.getNodeHeight()/2, lattice.getNodeWidth(), lattice.getNodeHeight());
        }
        
            g.setColor(Color.BLACK);
            for(int i=0; i<inputData.getRows(); i++){
                g.drawString(inputData.getDataLabels()[i], lattice.getLatticeNode()[inputData.getInputDataBMUs()[i]].getXPos(), lattice.getLatticeNode()[inputData.getInputDataBMUs()[i]].getYPos());
            }
        
        
        if(latticeType == 3){
            for(int i=0; i<maxValue; i++){
                int color = 1024/(int)maxValue*i;
                if(color < 256){
                    g.setColor(new Color(0,(color % 256),255));
                }
                else if(color < 512){
                    g.setColor(new Color(0,255,255 - (color % 256)));
                }
                else if(color < 768){
                    g.setColor(new Color((color % 256),255,0));
                }
                else{
                    g.setColor(new Color(255,255 - (color % 256),0));
                }
                double width = 350;
                g.fillRect((int)Math.ceil(i*width/maxValue) + 50, 455, (int)Math.ceil(width/maxValue), 15);
                
            }
            g.setColor(Color.BLACK);
            g.drawRect(50,455, 350, 15);
            
        }
        
        
    }
    
}
