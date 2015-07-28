/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package my.SOMVisualize;

/**
 *
 * @author Mark Lester
 */
public class Classifier {
    
    SOMLattice lattice;
    InputData dataSet;
    
    public Classifier(SOMLattice lattice, InputData dataSet){
        this.lattice = new SOMLattice();
        this.dataSet = dataSet;
        initializeLattice(lattice);
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
    
    public void runClassifier(){
        double quantizationError[] = new double[dataSet.getRows()];
        
        for(int i=0; i<dataSet.getRows(); i++){
            quantizationError[i] = (lattice.getLatticeNode()[dataSet.getInputDataBMUs()[i]]).getDistance(dataSet.getInputData()[i]);
        }
        
        
        
        
        
        
        
        
        
    }
    
    
    
}
