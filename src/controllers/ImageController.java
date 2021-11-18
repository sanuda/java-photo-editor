/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import model.Layer;
import views.MainFrame;

/**
 *
 * @author Sanuda Jayasinghe <Sanuda.Jayasinghe at selfmade>
 */
public class ImageController {

    public MainFrame frame;

    public Polygon lassoPoly = new Polygon();
    public ArrayList<Point> lassoPoints = new ArrayList<>();
    public boolean lassoComplete = false;

    public BufferedImage getScaled(BufferedImage img, double sx, double sy, boolean shiftPress) {
        if (sx <= 0) {
            sx = 1;
        }
        if (sy <= 0) {
            sy = 1;
        }
        int w = img.getWidth();
        int h = img.getHeight();
        BufferedImage after;
        if (shiftPress) {
            double min = Math.min(sy, sx);
            after = new BufferedImage((int) (w * min), (int) (h * min), BufferedImage.TYPE_INT_ARGB);
        }
        else {
            after = new BufferedImage((int) (w * sx), (int) (h * sy), BufferedImage.TYPE_INT_ARGB);
        }
        AffineTransform at = new AffineTransform();
        at.scale(sx, sy);
        AffineTransformOp scaleOp
                = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
        after = scaleOp.filter(img, after);
        return after;
    }

    public void addPolygonLassoPoint(MouseEvent evt) {
        if (lassoComplete) {
            if (lassoPoly.contains(evt.getX(), evt.getY())) {
                return;
            }
            else {
                resetPolyTool();
            }
        }
        //
        if (lassoPoints.size() > 1) {
            Point first = lassoPoints.get(0);
            Rectangle rect = new Rectangle(first.x - 5, first.y - 5, 10, 10);
            if (rect.contains(evt.getX(), evt.getY())) {
                polygonLassoComplete();
            }
            else {
                lassoPoly.addPoint(evt.getX(), evt.getY());
                lassoPoints.add(new Point(evt.getX(), evt.getY()));
            }
        }
        else {
            lassoPoly.addPoint(evt.getX(), evt.getY());
            lassoPoints.add(new Point(evt.getX(), evt.getY()));
        }
    }

    public void polygonLassoComplete() {
        lassoComplete = true;
        frame.imgSelection = getSelection(frame.renderC.getCurrentLayer().image);
        frame.imgSelectionInverse = getSelectionInverse(frame.renderC.getCurrentLayer());
    }

    public BufferedImage cloneImage(BufferedImage r) {
        BufferedImage clone = new BufferedImage(r.getWidth(), r.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = frame.renderC.getGraphics(clone);
        g.drawImage(r, 0, 0, null);
        return clone;
    }

    public void removeArea(Layer layer) {
        BufferedImage img = layer.image;
        Graphics2D g = frame.renderC.getGraphics(img);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR));
        Polygon p = new Polygon();
        for (int i = 0; i < lassoPoints.size(); i++) {
            Point po = lassoPoints.get(i);
            p.addPoint(po.x - layer.x, po.y - layer.y);
        }
        g.fillPolygon(p);
        layer.image = img;
    }

    public void resetPolyTool() {
        lassoPoly = new Polygon();
        lassoPoints = new ArrayList<>();
        lassoComplete = false;

        frame.selectionMoveX = 0;
        frame.selectionMoveY = 0;
        frame.selectionPrevX = 0;
        frame.selectionPrevY = 0;
    }

    public BufferedImage getSelectionInverse(Layer layer) {
        BufferedImage img = new BufferedImage(layer.image.getWidth(), layer.image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = frame.renderC.getGraphics(img);
        g.drawImage(layer.image, 0, 0, null);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR));
        Polygon p = new Polygon();
        for (int i = 0; i < lassoPoints.size(); i++) {
            Point po = lassoPoints.get(i);
            p.addPoint(po.x - layer.x, po.y - layer.y);
        }
        g.fillPolygon(p);
        return img;
    }

    public BufferedImage getSelection(BufferedImage img) {
        BufferedImage finalImg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
        //Rectangle bounds = lassoPoly.getBounds();
        Layer layer = frame.renderC.getCurrentLayer();
        Graphics2D g = frame.renderC.getGraphics(finalImg);
        g.drawImage(img, 0, 0, null);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR));
        Polygon p = new Polygon();
        p.addPoint(0 - layer.x, 0 - layer.x);
        p.addPoint(0 - layer.x, img.getHeight() - layer.x);
        p.addPoint(img.getWidth() - layer.x, img.getHeight() - layer.x);
        p.addPoint(img.getWidth() - layer.x, 0 - layer.x);
        p.addPoint(0 - layer.x, 0 - layer.x);

        int pos = -1;
        int minX = img.getWidth();
        int minY = img.getHeight();

        //find the position of the top left most point from the lasso list
        for (int i = 0; i < lassoPoints.size(); i++) {
            Point point = lassoPoints.get(i);
            if (point.x < minX) {
                minX = point.x;
                minY = point.y;
                pos = i;
            }
            else if (point.x == minX && point.y < minY) {
                minY = point.y;
                pos = i;
            }
        }
        if (pos == -1) {
            System.out.println("no points in lasso array?");
            return null;
        }

        for (int i = pos; i < lassoPoints.size(); i++) {
            Point po = lassoPoints.get(i);
            p.addPoint(po.x - layer.x, po.y - layer.y);
        }
        for (int i = 0; i < pos; i++) {
            Point po = lassoPoints.get(i);
            p.addPoint(po.x - layer.x, po.y - layer.y);
        }
        Point po = lassoPoints.get(pos);
        p.addPoint(po.x - layer.x, po.y - layer.y);
        g.fillPolygon(p);

//        for (int i = 0; i < img.getWidth(); i++) {
//            for (int j = 0; j < img.getHeight(); j++) {
//                if (!lassoPoly.contains(i, j)) {
//                    finalImg.setRGB(i, j, new Color(0, 0, 0, 0).getRGB());
//                }
//            }
//        }
        return finalImg;
    }

    public void updateSelectionArray() {
        int[] xpoints = lassoPoly.xpoints;
        int[] ypoints = lassoPoly.ypoints;
        lassoPoints.clear();
        for (int i = 0; i < lassoPoly.npoints; i++) {
            lassoPoints.add(new Point(xpoints[i], ypoints[i]));
        }
    }
}
