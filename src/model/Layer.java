/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 *
 * @author Sanuda Jayasinghe <Sanuda.Jayasinghe at selfmade>
 */
public class Layer {
    public String name;
    public BufferedImage image;
    public int x;
    public int y;
    public int width;
    public int height;
    public float opacity;

    public Layer(String name,BufferedImage image, int x, int y, int width, int height) {
        this.name=name;
        this.image = image;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.opacity=1;
    }
    
    public static void main(String[] args) {
        
    }
    
}
