/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package my.SOMVisualize;

import java.awt.Color;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyleConstants;

/**
 *
 * @author Mark Lester
 */
public class ConsoleLog implements Serializable{
    public javax.swing.JTextPane log;
    private javax.swing.text.StyledDocument doc;
    
public ConsoleLog(javax.swing.JTextPane log, javax.swing.text.StyledDocument doc){
    this.log = log;
    
    this.doc = this.log.getStyledDocument();
    
    //Set different styles
    
    
    javax.swing.text.Style style = this.log.addStyle("Success", null);
    StyleConstants.setForeground(style ,new Color(101,157,50));
    
    style = this.log.addStyle("Error", null);
    StyleConstants.setForeground(style, Color.red);
    
    style = this.log.addStyle("Normal", null);
    StyleConstants.setForeground(style, Color.black);
}

public void throwSuccessMessage(String s){
    try {
        doc.insertString(doc.getLength(), s, doc.getStyle("Success"));
        log.setCaretPosition(doc.getLength());
    } 
    catch (BadLocationException ex) {
        Logger.getLogger(SOMVisualizeUI.class.getName()).log(Level.SEVERE, null, ex);
    }
}


public void throwErrorMessage(String s){
    try {
        doc.insertString(doc.getLength(), s, doc.getStyle("Error"));
        log.setCaretPosition(doc.getLength());
    } 
    catch (BadLocationException ex) {
        Logger.getLogger(SOMVisualizeUI.class.getName()).log(Level.SEVERE, null, ex);
    }
}

public void throwNormalMessage(String s){
    try {
        doc.insertString(doc.getLength(), s, doc.getStyle("Normal"));
        log.setCaretPosition(doc.getLength());
    } 
    catch (BadLocationException ex) {
        Logger.getLogger(SOMVisualizeUI.class.getName()).log(Level.SEVERE, null, ex);
    }
}
}
