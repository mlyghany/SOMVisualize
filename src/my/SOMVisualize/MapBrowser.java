/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package my.SOMVisualize;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;

import ru.atomation.jbrowser.impl.JBrowserComponent;
import ru.atomation.jbrowser.impl.JBrowserBuilder;
import ru.atomation.jbrowser.impl.JBrowserCanvas;
import ru.atomation.jbrowser.impl.JComponentFactory;
import ru.atomation.jbrowser.interfaces.BrowserManager;


/**
 *
 * @author Mark Lester
 */
public class MapBrowser {
   
    private String mapUrl;
    private boolean isInitialized;
    private BrowserManager browserManager;
    private String xmlText = "";
          
            
    public MapBrowser(String mapUrl){
        this.mapUrl = mapUrl;
        this.isInitialized = false;
        
    }
    
    public void runMap(){
        if(isInitialized){
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setSize((int) (screenSize.getWidth() * 0.75f),
                    (int) (screenSize.getHeight() * 0.75f));
            frame.setLocationRelativeTo(null);
            frame.setTitle("GIS Visualizer");
            
            if(browserManager == null){
            browserManager =
                    new JBrowserBuilder().buildBrowserManager();
            }
            
            browserManager.getBrowserConfig().setManualProxy("proxy.upm.edu.ph", 3128, null, -1, null, -1, null, -1, "localhost");
            JComponentFactory<Canvas> canvasFactory = browserManager.getComponentFactory(JBrowserCanvas.class);
            JBrowserComponent<?> browser = canvasFactory.createBrowser();
            
            browserManager.getBrowserConfig().enableJavascript();
            
            frame.getContentPane().add(browser.getComponent());
            frame.setVisible(true);
            
            browser.setText("<html><body><form id='fileInputForm' action='http://localhost/SOMVisualize/gisVisualizer.php' method='POST'><textarea id='fromJava' name='fromJava'>" + xmlText + "</textarea><input type='submit' value='Visualize GIS Now'/></form></body></html>");
            //browser.setUrl("http://localhost/SOMVisualize/gisVisualizer.php");
            
        }
    }
    
    public boolean initializeXML(Cluster clust, Component[] comp, InputData inputData){
        FileWriter fw = null;
        BufferedWriter bw = null;
        
        try {
          fw = new FileWriter("temp.xml");
          bw = new BufferedWriter(fw);

          bw.write("<document>");
          bw.newLine();
          
          xmlText = xmlText + "<document>\n";
          
          //write the labels
          bw.write("\t<labels>");
          bw.newLine();
          
          xmlText = xmlText + "\t<labels>\n";
          
          bw.write("\t\t<cluster>Clusters</cluster>");
          bw.newLine();
          
          xmlText = xmlText + "\t\t<cluster>Clusters</cluster>\n";
          
          for(int i=0; i<inputData.getCols(); i++){
              bw.write("\t\t<component" + i +">" + inputData.getVariableLabels()[i] + "</component" + i +">");
              bw.newLine();
              
              xmlText = xmlText + "\t\t<component" + i +">" + inputData.getVariableLabels()[i] + "</component" + i +">\n";
          }
          
          bw.write("\t</labels>");
          bw.newLine();
          
          xmlText = xmlText + "\t</labels>\n";
          
          for(int i=0; i<inputData.getRows(); i++){
              
              //start of a block
              
              bw.write("\t<data>");
              bw.newLine();
              
              xmlText = xmlText + "\t<data>\n";
              
              //write cluster name
              bw.write("\t\t<title>" + inputData.getDataLabels()[i] + "</title>");
              bw.newLine();
              
              xmlText = xmlText + "\t\t<title>" + inputData.getDataLabels()[i] + "</title>\n";
              
              //write cluster colors
              
              
              bw.write("\t\t<cluster>" + this.getHexRGB(clust.getLattice(), inputData, i) +"</cluster>");
              bw.newLine();
              
              xmlText = xmlText + "\t\t<cluster>" + this.getHexRGB(clust.getLattice(), inputData, i) +"</cluster>\n";
              
              //write component colors
              for(int j=0; j<inputData.getCols(); j++){
                  bw.write("\t\t<component" + j +">" + this.getHexRGB(comp[j].getLattice(), inputData, i) + "</component" + j +">");
                  bw.newLine();
                  
                  xmlText = xmlText + "\t\t<component" + j +">" + this.getHexRGB(comp[j].getLattice(), inputData, i) + "</component" + j +">\n";
              }
              
              //end block
              bw.write("\t</data>");
              bw.newLine();
              
              xmlText = xmlText + "\t</data>\n";
          }
          
          bw.write("</document>");
          
          xmlText = xmlText + "</document>";

          System.out.println(xmlText);
          bw.close();
          
          isInitialized = true;
          return true;
        } 
        catch (IOException e) {
            return false;
        }

    }
    
    private String getHexRGB(SOMLattice sl, InputData inputData, int i){
              String red = Integer.toHexString(sl.getLatticeNode()[inputData.getInputDataBMUs()[i]].getNodeColor().getRed());
              String green = Integer.toHexString(sl.getLatticeNode()[inputData.getInputDataBMUs()[i]].getNodeColor().getGreen());
              String blue  = Integer.toHexString(sl.getLatticeNode()[inputData.getInputDataBMUs()[i]].getNodeColor().getBlue());
              
              if(red.length() == 1){
                  red = 0 + "" + red;
              }
              if(green.length() == 1){
                  green = 0 + "" + green;
              }
              if(blue.length() == 1){
                  blue = 0 + "" + blue;
              }
              
              return red+""+green+""+blue;
    }
    
}
