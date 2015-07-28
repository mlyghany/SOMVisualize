/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package my.SOMVisualize;

/**
 *
 * @author Mark Lester
 */
public class NewClass extends javax.swing.JPanel{
    
    public NewClass(){
        super();
    }
    
    protected void paintComponent(java.awt.Graphics g){
        super.paintComponent(g);
        
        g.drawRect(0, 0, 100, 100);
     
    }
}
