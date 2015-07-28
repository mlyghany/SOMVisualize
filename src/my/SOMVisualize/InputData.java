/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package my.SOMVisualize;

import java.io.Serializable;

/**
 *
 * @author Mark Lester
 */
public class InputData implements Serializable{
 
    private int rows;
    private int cols;
    
    private double[][] inputData;
    private int[] inputDataBMUs;
    private String[] dataLabels;
    private String[] variableLabels;

    public InputData(int rows, int cols){
        this.rows = rows;
        this.cols = cols;
        
        this.inputData = new double[rows][cols];
        this.inputDataBMUs = new int[rows];
        this.dataLabels = new String[rows];
        this.variableLabels = new String[cols];
        
    }

    public int getCols() {
        return cols;
    }

    public int getRows() {
        return rows;
    }
    
    public String[] getDataLabels() {
        return dataLabels;
    }

    public void setDataLabels(String[] dataLabels) {
        System.arraycopy(dataLabels, 0, this.dataLabels, 0, rows);
    }

    public double[][] getInputData() {
        return inputData;
    }

    public void setInputData(double[][] inputData) {
        for(int i=0; i<inputData.length; i++){
            System.arraycopy(inputData[i], 0, this.inputData[i], 0, cols);
        }
    }

    public int[] getInputDataBMUs() {
        return inputDataBMUs;
    }

    public void setInputDataBMUs(int[] inputDataBMUs) {
        System.arraycopy(inputDataBMUs, 0, this.inputDataBMUs, 0, rows);
    }

    public String[] getVariableLabels() {
        return variableLabels;
    }

    public void setVariableLabels(String[] variableLabels) {
        System.arraycopy(variableLabels, 0, this.variableLabels, 0, cols);
    }
    
    public void setOneBMU(int dataIndex, int BMUindex){
        this.inputDataBMUs[dataIndex] = BMUindex;
    }
    
    
}
