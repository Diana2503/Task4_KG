/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.vsu.cs.draw;

import ru.vsu.cs.functions.DefaultFunction;
import ru.vsu.cs.models.*;
import ru.vsu.cs.CameraController;
import ru.vsu.cs.screen.ScreenConverter;
import ru.vsu.cs.third.Camera;
import ru.vsu.cs.third.Scene;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 *
 * @author Alexey
 */
public class DrawPanel extends JPanel
        implements CameraController.RepaintListener {
    private Scene scene;
    private ScreenConverter sc;
    private Camera cam;
    private CameraController camController;
    private HelixFunc helix;

    public void setHelix(HelixFunc helix) {
        scene.getModelsList().clear();
        scene.getModelsList().add(helix);
    }

    public DrawPanel() {
        super();
        sc = new ScreenConverter(-1, 1, 2, 2, 1, 1);
        cam = new Camera();
        camController = new CameraController(cam, sc);
        scene = new Scene(Color.WHITE.getRGB());
        scene.showAxes();

        helix = new HelixFunc(3,6,0.1f, 0.5f, 6, new DefaultFunction());


        scene.getModelsList().add(helix);

        camController.addRepaintListener(this);
        addMouseListener(camController);
        addMouseMotionListener(camController);
        addMouseWheelListener(camController);
    }
    
    @Override
    public void paint(Graphics g) {
        sc.setScreenSize(getWidth(), getHeight());
        BufferedImage bi = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = (Graphics2D)bi.getGraphics();
        IDrawer dr = new SimpleEdgeDrawer(sc, graphics);
        scene.drawScene(dr, cam);
        g.drawImage(bi, 0, 0, null);
        graphics.dispose();
    }

    @Override
    public void shouldRepaint() {
        repaint();
    }
}
