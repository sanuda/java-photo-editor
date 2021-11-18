/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import controllers.RenderController;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 *
 * @author Sanuda Jayasinghe <Sanuda.Jayasinghe at selfmade>
 */
public class Canvas extends JPanel{

    RenderController renderC;
    public Canvas(RenderController c) {
        super();
        renderC=c;
    }
    
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); //To change body of generated methods, choose Tools | Templates.
        
        renderC.drawPreview(this);
    }
    
}
