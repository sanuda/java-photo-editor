/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.Transparency;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import model.Layer;
import views.MainFrame;

/**
 *
 * @author Sanuda Jayasinghe <Sanuda.Jayasinghe at selfmade>
 */
public class RenderController {

    public BufferedImage previewImg;
    private final GraphicsConfiguration config;
    public ArrayList<Layer> layerList = new ArrayList<>();

    int layerCount = 0;//to determine new layer name. Layer number

    public int selectedLayer;

    public String mode;

    public static final String MODE_TRANSFORM = "Transform";
    public static final String MODE_POLY_LASSO = "Polygon Lasso";

    public ImageController imgC;

    public RenderController() {
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice device = env.getDefaultScreenDevice();
        config = device.getDefaultConfiguration();
    }

    public void updatePreviewImage(int canvasWidth, int canvasHeight) {
        //previewImg = config.createCompatibleImage(canvasWidth, canvasHeight, Transparency.TRANSLUCENT);
        previewImg = new BufferedImage(canvasWidth, canvasHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = getGraphics(previewImg);
        g.setColor(Color.GRAY);
        g.fillRect(0, 0, canvasWidth, canvasHeight);
        int pos = 0;
        for (Layer layer : layerList) {
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, layer.opacity));
            g.drawImage(layer.image, layer.x, layer.y, null);
            if (mode != null) {
                if (mode.equals(MODE_TRANSFORM)) {
                    if (selectedLayer == pos) {
                        g.setColor(Color.BLACK);

                        g.drawRect(layer.x, layer.y, layer.width, layer.height);
                        //
                        g.drawRect(layer.x - 3, layer.y - 3, 6, 6);
                        g.drawRect(layer.x + layer.width - 3, layer.y - 3, 6, 6);
                        g.drawRect(layer.x - 3, layer.y + layer.height - 3, 6, 6);
                        g.drawRect(layer.x + layer.width - 3, layer.y + layer.height - 3, 6, 6);
                    }
                }
            }
            pos++;
        }
        if (mode != null) {
            if (mode.equals(MODE_POLY_LASSO)) {
                //imgC.lassoPoly.ge
                if (imgC.lassoComplete) {
                    g.setColor(Color.BLACK);
                    g.draw(imgC.lassoPoly);
                }
            }
        }
    }

    public void drawPreview(JPanel canvas) {
        new Thread() {
            public void run() {
                Graphics2D g = getGraphics(canvas);
                g.drawImage(previewImg, 0, 0, null);
            }
        }.start();
    }

    public Graphics2D getGraphics(JPanel canvas) {
        Graphics2D g = (Graphics2D) canvas.getGraphics();
        RenderingHints rh = new RenderingHints(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHints(rh);
        return g;
    }

    public Graphics2D getGraphics(BufferedImage img) {
        Graphics2D g = (Graphics2D) img.getGraphics();
        RenderingHints rh = new RenderingHints(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHints(rh);
        return g;
    }

    public void newProjectImage(int canvasWidth, int canvasHeight) {
        BufferedImage img = new BufferedImage(canvasWidth, canvasHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = getGraphics(img);
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, canvasWidth, canvasHeight);

        Layer layer = new Layer("Background", img, 0, 0, canvasWidth, canvasHeight);
        layerList.add(layer);
    }

    public void pasteImage(JPanel canvas) {
        int canvasHeight = canvas.getHeight();
        int canvasWidth = canvas.getWidth();
        try {
            //get 
            Object data = Toolkit.getDefaultToolkit()
                    .getSystemClipboard()
                    .getData(DataFlavor.imageFlavor);
            BufferedImage img = (BufferedImage) data;
            Layer layer = new Layer("Layer " + (layerCount++), img, 0, 0, img.getWidth(), img.getHeight());
            layerList.add(layer);

            BufferedImage buffy = config.createCompatibleImage(img.getWidth(), img.getHeight(), Transparency.TRANSLUCENT);
            Graphics2D g = getGraphics(buffy);
            g.drawImage(img, 0, 0, null);

            updatePreviewImage(canvasWidth, canvasHeight);
            drawPreview(canvas);

//            Graphics g = jPanelCanvas.getGraphics();
//            g.drawImage(img, 0, 0, null);
        }
        catch (UnsupportedFlavorException ex) {
            JOptionPane.showMessageDialog(canvas, "The copied data is not an image");
            //Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IOException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addWhiteLayer(int canvasWidth, int canvasHeight) {
        BufferedImage b = new BufferedImage(canvasWidth, canvasHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = getGraphics(b);
        g.setColor(Color.white);
        g.fillRect(0, 0, canvasWidth, canvasHeight);
        Layer layer = new Layer("Layer " + (layerCount++), b, 0, 0, canvasWidth, canvasHeight);
        layerList.add(layer);
    }

    public Layer getLayer(int selectedLayerNum) {
        return layerList.get(selectedLayerNum);
    }

    public Layer getCurrentLayer() {
        return layerList.get(selectedLayer);
    }

    /**
     *
     * @param side -1 to move down and 1 for up
     */
    public synchronized int moveCurrentLayer(int side) {
        side = -side;
        if (selectedLayer + side > layerList.size() - 1 || selectedLayer + side < 0) {
            Toolkit.getDefaultToolkit().beep();
            return selectedLayer;
        }
        Layer l = layerList.remove(selectedLayer);
        selectedLayer += side;
        layerList.add(selectedLayer, l);
        return selectedLayer;
    }

    public void deleteSelectedLayer() {
        layerList.remove(selectedLayer);
    }
}
