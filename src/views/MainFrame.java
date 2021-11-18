/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import model.Layer;
import com.sun.java.swing.plaf.windows.WindowsLookAndFeel;
import controllers.ImageController;
import controllers.ProjectController;
import controllers.RenderController;
import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Sanuda Jayasinghe <Sanuda.Jayasinghe at selfmade>
 */
public class MainFrame extends javax.swing.JFrame {

    public RenderController renderC = new RenderController();
    public ImageController imgC = new ImageController();
    public ProjectController projectC;

    int canvasWidth;
    int canvasHeight;

    int pressImgX = 0;
    int pressImgY = 0;
    int pressX = 0;
    int pressY = 0;

    boolean resizing = false;
    int clickType = -1;

    final int MOUSE_LEFT_CLICK = 1;
    final int MOUSE_RIGHT_CLICK = 3;

    String tool;

    BufferedImage tempImage;
    boolean imageTransforming;
    int pressTranformX = 0;
    int pressTranformY = 0;

    public BufferedImage imgSelection;
    public BufferedImage imgSelectionInverse;
    public int selectionMoveX = 0;
    public int selectionMoveY = 0;
    public int selectionPrevX = 0;
    public int selectionPrevY = 0;

    /**
     * Creates new form MainFrame
     */
    public MainFrame() {
        renderC.imgC = imgC;
        imgC.frame = this;
        projectC = new ProjectController(this);
        initComponents();
        canvasHeight = canvas.getHeight();
        canvasWidth = canvas.getWidth();

        renderC.newProjectImage(canvasWidth, canvasHeight);
        renderC.updatePreviewImage(canvasWidth, canvasHeight);
        renderC.drawPreview(canvas);
        updateLayer();
        int row = jTable1.getRowCount() - 1;
        jTable1.setRowSelectionInterval(row, row);
        jScrollPane1.getViewport().addChangeListener((ChangeEvent e) -> {
            if (!resizing) {
                renderC.drawPreview(canvas);
            }
        });

        jTable1.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent lse) {
                if (!lse.getValueIsAdjusting()) {
                    int selectedLayerNum = jTable1.getSelectedRow();
                    renderC.selectedLayer = selectedLayerNum;
                    renderC.updatePreviewImage(canvasWidth, canvasHeight);
                    renderC.drawPreview(canvas);
                    if (selectedLayerNum != -1) {

                        Layer l = renderC.getLayer(selectedLayerNum);
                        jSlider1.setValue((int) (l.opacity * 100));
                    }
                }
            }
        });

        Toolkit.getDefaultToolkit().addAWTEventListener(new AWTEventListener() {
            @Override
            public void eventDispatched(AWTEvent event) {
                if (!hasFocus()) {
                    return;
                }
                KeyEvent evt = (KeyEvent) event;
//                if (evt.getID() == KeyEvent.KEY_PRESSED && evt.getModifiers() == KeyEvent.CTRL_MASK && evt.getKeyCode() == KeyEvent.VK_F) {
//                    
//                }
                if (renderC.mode != null) {
                    if (renderC.mode.equals(RenderController.MODE_POLY_LASSO) && imgC.lassoComplete) {
                        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {

                        }
                    }
                }
                int selectedLayerNum = jTable1.getSelectedRow();
                if (selectedLayerNum == -1) {
                    return;
                }
                if (evt.getID() == KeyEvent.KEY_PRESSED && evt.getKeyCode() == KeyEvent.VK_UP) {

                    renderC.getLayer(selectedLayerNum).y--;
                    renderC.updatePreviewImage(canvasWidth, canvasHeight);
                    renderC.drawPreview(canvas);
                }
                else if (evt.getID() == KeyEvent.KEY_PRESSED && evt.getKeyCode() == KeyEvent.VK_DOWN) {

                    renderC.getLayer(selectedLayerNum).y++;
                    renderC.updatePreviewImage(canvasWidth, canvasHeight);
                    renderC.drawPreview(canvas);
                }
                else if (evt.getID() == KeyEvent.KEY_PRESSED && evt.getKeyCode() == KeyEvent.VK_RIGHT) {

                    renderC.getLayer(selectedLayerNum).x++;
                    renderC.updatePreviewImage(canvasWidth, canvasHeight);
                    renderC.drawPreview(canvas);
                }
                else if (evt.getID() == KeyEvent.KEY_PRESSED && evt.getKeyCode() == KeyEvent.VK_LEFT) {

                    renderC.getLayer(selectedLayerNum).x--;
                    renderC.updatePreviewImage(canvasWidth, canvasHeight);
                    renderC.drawPreview(canvas);
                }
            }
        }, AWTEvent.KEY_EVENT_MASK);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel4 = new javax.swing.JPanel();
        canvas = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jSlider1 = new javax.swing.JSlider();
        jButton5 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItemPaste = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItemDelete = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenu5 = new javax.swing.JMenu();
        jMenu4 = new javax.swing.JMenu();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                formFocusGained(evt);
            }
        });
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });
        addWindowStateListener(new java.awt.event.WindowStateListener() {
            public void windowStateChanged(java.awt.event.WindowEvent evt) {
                formWindowStateChanged(evt);
            }
        });
        addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                formWindowGainedFocus(evt);
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Tools"));

        jButton3.setText("Move");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-lasso-tool-24.png"))); // NOI18N
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(jButton4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel2.setText("x:0 y:0");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
        );

        jScrollPane1.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                jScrollPane1ComponentResized(evt);
            }
        });

        jPanel4.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jPanel4.setFocusable(false);
        jPanel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel4MouseClicked(evt);
            }
        });

        canvas.setBackground(new java.awt.Color(255, 255, 255));
        canvas.setCursor(new java.awt.Cursor(java.awt.Cursor.MOVE_CURSOR));
        canvas.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                canvasMouseDragged(evt);
            }
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                canvasMouseMoved(evt);
            }
        });
        canvas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                canvasMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                canvasMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                canvasMouseReleased(evt);
            }
        });
        canvas.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentMoved(java.awt.event.ComponentEvent evt) {
                canvasComponentMoved(evt);
            }
            public void componentResized(java.awt.event.ComponentEvent evt) {
                canvasComponentResized(evt);
            }
            public void componentShown(java.awt.event.ComponentEvent evt) {
                canvasComponentShown(evt);
            }
        });

        javax.swing.GroupLayout canvasLayout = new javax.swing.GroupLayout(canvas);
        canvas.setLayout(canvasLayout);
        canvasLayout.setHorizontalGroup(
            canvasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 451, Short.MAX_VALUE)
        );
        canvasLayout.setVerticalGroup(
            canvasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 314, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(canvas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(canvas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        jScrollPane1.setViewportView(jPanel4);

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setText("Layers");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                "Layer Name"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jTable1.setFocusable(false);
        jTable1.setRequestFocusEnabled(false);
        jTable1.setRowHeight(30);
        jTable1.setShowVerticalLines(false);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jTable1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTable1KeyReleased(evt);
            }
        });
        jScrollPane3.setViewportView(jTable1);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/baseline_arrow_upward_black_24dp.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/baseline_arrow_downward_black_24dp.png"))); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jSlider1.setValue(100);
        jSlider1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSlider1StateChanged(evt);
            }
        });

        jButton5.setText("Add White Layer");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton5))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jSlider1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jSlider1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(13, 13, 13)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jButton5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 222, Short.MAX_VALUE)
                .addContainerGap())
        );

        jMenu1.setText("File");

        jMenuItem3.setText("Export");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem1.setText("Copy");
        jMenu2.add(jMenuItem1);

        jMenuItemPaste.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItemPaste.setText("Paste");
        jMenuItemPaste.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemPasteActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItemPaste);

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem2.setText("Deselect");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem2);

        jMenuItemDelete.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_DELETE, 0));
        jMenuItemDelete.setText("Delete");
        jMenuItemDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemDeleteActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItemDelete);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("Image");

        jMenu5.setText("Mode");
        jMenu3.add(jMenu5);

        jMenu4.setText("Adjustments");
        jMenu3.add(jMenu4);
        jMenu3.add(jSeparator1);

        jMenuItem5.setText("Image Size");
        jMenu3.add(jMenuItem5);

        jMenuItem6.setText("Canvas Size");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem6);

        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 402, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 346, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItemPasteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemPasteActionPerformed
        new Thread() {
            public void run() {
                renderC.pasteImage(canvas);
                updateLayer();
                int row = jTable1.getRowCount() - 1;
                jTable1.setRowSelectionInterval(row, row);
            }

        }.start();
    }//GEN-LAST:event_jMenuItemPasteActionPerformed

    private void canvasMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_canvasMousePressed
        this.requestFocusInWindow();
        clickType = evt.getButton();

        int type = canvas.getCursor().getType();
        if (renderC.mode != null) {
            if (renderC.mode.equals(RenderController.MODE_POLY_LASSO)) {

            }
        }
        if (renderC.mode != null && type >= 4 && type <= 11) {
            if (renderC.mode.equals(RenderController.MODE_TRANSFORM)) {
                Layer layer = renderC.getLayer(renderC.selectedLayer);
                BufferedImage img = layer.image;
                tempImage = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
                Graphics2D g = renderC.getGraphics(tempImage);
                g.drawImage(img, 0, 0, null);
                imageTransforming = true;
                //layer.image = new ImageController().getScaled(img);
                switch (canvas.getCursor().getType()) {
                    case Cursor.NW_RESIZE_CURSOR:
                        pressTranformX = layer.x + layer.width;
                        pressTranformY = layer.y + layer.height;
                        break;
                    case Cursor.NE_RESIZE_CURSOR:
                        pressTranformX = layer.x;
                        pressTranformY = layer.y + layer.height;
                        break;
                    case Cursor.SW_RESIZE_CURSOR:
                        pressTranformX = layer.x + layer.width;
                        pressTranformY = layer.y;
                        break;
                    case Cursor.SE_RESIZE_CURSOR:
                        pressTranformX = layer.x;
                        pressTranformY = layer.y;
                        break;

                    case Cursor.N_RESIZE_CURSOR:
                        pressTranformY = layer.y + layer.height;
                        break;
                    case Cursor.E_RESIZE_CURSOR:
                        pressTranformX = layer.x;
                        break;
                    case Cursor.S_RESIZE_CURSOR:
                        pressTranformY = layer.y;
                        break;
                    case Cursor.W_RESIZE_CURSOR:
                        pressTranformX = layer.x + layer.width;
                        break;

                }

            }
        }
        else {

            // start to save stuff for draggin
            int selectedLayerNum = jTable1.getSelectedRow();
            if (selectedLayerNum == -1) {
                return;
            }
            Layer selectedLayer = renderC.getLayer(selectedLayerNum);
            pressImgX = selectedLayer.x;
            pressImgY = selectedLayer.y;
        }
        pressX = evt.getX();
        pressY = evt.getY();
    }//GEN-LAST:event_canvasMousePressed

    private void canvasMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_canvasMouseDragged
        this.requestFocusInWindow();
        if (clickType == MOUSE_RIGHT_CLICK) {

        }
        else if (clickType == MOUSE_LEFT_CLICK) {

            int type = canvas.getCursor().getType();
            if (imageTransforming && type >= 4 && type <= 11) {
                tranformMouseDrag(evt);
            }

            else if (imgC.lassoComplete && imgSelection != null && canvas.getCursor().getType() == Cursor.MOVE_CURSOR) {
                Layer l = renderC.getCurrentLayer();
                BufferedImage img = new BufferedImage(l.width, l.height, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g = renderC.getGraphics(img);
                g.drawImage(imgSelectionInverse, l.x, l.y, null);
                g.drawImage(imgSelection, l.x + selectionPrevX + (-pressX + evt.getX()), l.x + selectionPrevY + (-pressY + evt.getY()), null);
                Polygon poly = new Polygon();
                for (int i = 0; i < imgC.lassoPoints.size(); i++) {
                    Point p = imgC.lassoPoints.get(i);
                    poly.addPoint(p.x + (-pressX + evt.getX()), p.y + (-pressY + evt.getY()));
                }
                selectionMoveX = selectionPrevX + (-pressX + evt.getX());
                selectionMoveY = selectionPrevY + (-pressY + evt.getY());
                imgC.lassoPoly = poly;
                l.image = img;
                renderC.updatePreviewImage(canvasWidth, canvasHeight);
                renderC.drawPreview(canvas);
            }
            else {
                if (canvas.getCursor().getType() == Cursor.MOVE_CURSOR) {
                    moveMouseDrag(evt);
                }
            }
        }


    }//GEN-LAST:event_canvasMouseDragged

    private void canvasComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_canvasComponentResized
//no use        
//renderC.drawPreview(canvas);
    }//GEN-LAST:event_canvasComponentResized

    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
        renderC.drawPreview(canvas);
    }//GEN-LAST:event_formComponentResized

    private void canvasComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_canvasComponentShown

    }//GEN-LAST:event_canvasComponentShown

    private void formWindowStateChanged(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowStateChanged
        renderC.drawPreview(canvas);
    }//GEN-LAST:event_formWindowStateChanged

    private void formFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_formFocusGained
        //no use maybe later
    }//GEN-LAST:event_formFocusGained

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        int selectedLayerNum = jTable1.getSelectedRow();
        if (selectedLayerNum == -1) {
            return;
        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void canvasComponentMoved(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_canvasComponentMoved

    }//GEN-LAST:event_canvasComponentMoved

    private void formWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowGainedFocus
        renderC.drawPreview(canvas);
    }//GEN-LAST:event_formWindowGainedFocus

    private void jScrollPane1ComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jScrollPane1ComponentResized

        new Thread() {
            public void run() {
                resizing = true;
                try {
                    Thread.sleep(100);
                }
                catch (InterruptedException ex) {
                    Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
                resizing = false;
            }
        }.start();
    }//GEN-LAST:event_jScrollPane1ComponentResized

    private void canvasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_canvasMouseClicked
        jLabel2.setText("x:" + evt.getX() + " y:" + evt.getY());
        this.requestFocusInWindow();
        if (evt.getButton() == MOUSE_LEFT_CLICK) {
            if (renderC.mode == null) {
//change the selection of layers if a different layer is clicked
                if (evt.getClickCount() > 1) {
                    for (int i = renderC.layerList.size() - 1; i >= 0; i--) {
                        Layer current = renderC.getLayer(i);
                        int x = current.x;
                        int y = current.y;
                        int width = current.width;
                        int height = current.height;
                        boolean check1 = x < evt.getX() && x + width > evt.getX();
                        boolean check2 = y < evt.getY() && y + height > evt.getY();

                        if (check1 && check2) {
                            jTable1.setRowSelectionInterval(i, i);
                            break;
                        }
                    }
                }
            }
            else if (renderC.mode.equals(RenderController.MODE_POLY_LASSO)) {

                if (evt.getClickCount() > 1) {
                    imgC.polygonLassoComplete();
                }
                else {
                    imgC.addPolygonLassoPoint(evt);
                }
            }
            else {//change the selection of layers if a different layer is clicked
                if (evt.getClickCount() > 1) {
                    for (int i = renderC.layerList.size() - 1; i >= 0; i--) {
                        Layer current = renderC.getLayer(i);
                        int x = current.x;
                        int y = current.y;
                        int width = current.width;
                        int height = current.height;
                        boolean check1 = x < evt.getX() && x + width > evt.getX();
                        boolean check2 = y < evt.getY() && y + height > evt.getY();

                        if (check1 && check2) {
                            jTable1.setRowSelectionInterval(i, i);
                            break;
                        }
                    }
                }
            }
        }
        else if (evt.getButton() == MOUSE_RIGHT_CLICK) {
            JPopupMenu pop = new JPopupMenu();
            JMenuItem itemCanvas = new JMenuItem("Cavas Size");
            itemCanvas.addActionListener((ActionEvent e) -> {
                new CanvasSizeFrame(canvas, this).setVisible(true);
            });

            JMenuItem itemPaste = new JMenuItem("Paste");
            itemPaste.addActionListener((ActionEvent e) -> {
                jMenuItemPasteActionPerformed(e);
            });
            JMenuItem itemTransform = new JMenuItem("Transform");
            itemTransform.addActionListener((ActionEvent e) -> {
                renderC.mode = RenderController.MODE_TRANSFORM;
                renderC.updatePreviewImage(canvasWidth, canvasHeight);
                renderC.drawPreview(canvas);
            });

            pop.add(itemPaste);
            pop.add(itemTransform);
            pop.add(new JPopupMenu.Separator());
            pop.add(itemCanvas);
            pop.show(canvas, evt.getX(), evt.getY());

            pop.addPopupMenuListener(new PopupMenuListener() {
                @Override
                public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                    renderC.drawPreview(canvas);
                }

                @Override
                public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
                    renderC.drawPreview(canvas);
                }

                @Override
                public void popupMenuCanceled(PopupMenuEvent e) {
                    renderC.drawPreview(canvas);
                }
            });
            return;
        }

    }//GEN-LAST:event_canvasMouseClicked

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        tool = "Move";
        canvas.setCursor(new java.awt.Cursor(java.awt.Cursor.MOVE_CURSOR));
        renderC.mode = null;
        imgC.resetPolyTool();

//        ImageIcon img s= new ImageIcon(getClass().getResource("/img/baseline_highlight_alt_black_24dp.png"));
//        Toolkit toolkit = Toolkit.getDefaultToolkit();
//        Cursor c = toolkit.createCustomCursor(img.getImage(), new Point(jPanelCanvas.getX(),
//                jPanelCanvas.getY()), "img");
//        jPanelCanvas.setCursor(c);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        tool = "Lasso";
        renderC.mode = RenderController.MODE_POLY_LASSO;
        canvas.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jTable1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable1KeyReleased

    }//GEN-LAST:event_jTable1KeyReleased

    private void jPanel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel4MouseClicked
        this.requestFocusInWindow();
    }//GEN-LAST:event_jPanel4MouseClicked

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        new CanvasSizeFrame(canvas, this).setVisible(true);
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void canvasMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_canvasMouseMoved
        jLabel2.setText("x:" + evt.getX() + " y:" + evt.getY());

        final int padding = 20;
        if (renderC.mode == null) {
            return;
        }
        if (renderC.mode.equals(RenderController.MODE_TRANSFORM)) {
            if (renderC.selectedLayer != -1) {
                int x = evt.getX();
                int y = evt.getY();
                Layer layer = renderC.getLayer(renderC.selectedLayer);
                Polygon topLine = new Polygon();
                topLine.addPoint(layer.x - padding, layer.y - padding);
                topLine.addPoint(layer.x + padding, layer.y + padding);
                topLine.addPoint(layer.x + layer.width - padding, layer.y - padding);
                topLine.addPoint(layer.x + layer.width + padding, layer.y + padding);
                Polygon leftLine = new Polygon();
                leftLine.addPoint(layer.x - padding, layer.y + layer.height - padding);
                leftLine.addPoint(layer.x + padding, layer.y + layer.height + padding);
                leftLine.addPoint(layer.x - padding, layer.y - padding);
                leftLine.addPoint(layer.x + padding, layer.y + padding);

                Polygon botLine = new Polygon();
                botLine.addPoint(layer.x - padding, layer.y - padding + layer.height);
                botLine.addPoint(layer.x + padding, layer.y + padding + layer.height);
                botLine.addPoint(layer.x + layer.width - padding, layer.y - padding + layer.height);
                botLine.addPoint(layer.x + layer.width + padding, layer.y + padding + layer.height);

                Polygon rightLine = new Polygon();
                rightLine.addPoint(layer.x + layer.width - padding, layer.y + layer.height - padding);
                rightLine.addPoint(layer.x + layer.width + padding, layer.y + layer.height + padding);
                rightLine.addPoint(layer.x + layer.width - padding, layer.y - padding);
                rightLine.addPoint(layer.x + layer.width + padding, layer.y + padding);

                Rectangle leftTop = new Rectangle(layer.x - padding, layer.y - padding, padding * 2, padding * 2);
                Rectangle leftBot = new Rectangle(layer.x - padding, layer.y + layer.height - padding, padding * 2, padding * 2);
                Rectangle rightTop = new Rectangle(layer.x + layer.width - padding, layer.y - padding, padding * 2, padding * 2);
                Rectangle rightBot = new Rectangle(layer.x + layer.width - padding, layer.y + layer.height - padding, padding * 2, padding * 2);
                if (leftTop.contains(x, y)) {
                    canvas.setCursor(new Cursor(Cursor.NW_RESIZE_CURSOR));
                }
                else if (leftBot.contains(x, y)) {
                    canvas.setCursor(new Cursor(Cursor.SW_RESIZE_CURSOR));
                }
                else if (rightTop.contains(x, y)) {
                    canvas.setCursor(new Cursor(Cursor.NE_RESIZE_CURSOR));
                }
                else if (rightBot.contains(x, y)) {
                    canvas.setCursor(new Cursor(Cursor.SE_RESIZE_CURSOR));
                }
                else if (topLine.contains(x, y)) {
                    canvas.setCursor(new Cursor(Cursor.N_RESIZE_CURSOR));
                }
                else if (botLine.contains(x, y)) {
                    canvas.setCursor(new Cursor(Cursor.S_RESIZE_CURSOR));
                }
                else if (rightLine.contains(x, y)) {
                    canvas.setCursor(new Cursor(Cursor.E_RESIZE_CURSOR));
                }
                else if (leftLine.contains(x, y)) {
                    canvas.setCursor(new Cursor(Cursor.W_RESIZE_CURSOR));
                }
                else {
                    canvas.setCursor(new Cursor(Cursor.MOVE_CURSOR));
                }
            }

        }
        else if (renderC.mode.equals(RenderController.MODE_POLY_LASSO)) {
            renderC.updatePreviewImage(canvasWidth, canvasHeight);
            renderC.drawPreview(canvas);
            BufferedImage b = renderC.previewImg;
            Graphics2D g = renderC.getGraphics(b);
            g.setColor(Color.black);
            if (imgC.lassoComplete) {
                if (imgC.lassoPoly.contains(evt.getPoint())) {
                    canvas.setCursor(new Cursor(Cursor.MOVE_CURSOR));
                }
                else {
                    canvas.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
            }
            else {
                Point last = null;
                for (int i = 0; i < imgC.lassoPoints.size() - 1; i++) {
                    Point p = imgC.lassoPoints.get(i);
                    Point p2 = imgC.lassoPoints.get(i + 1);
                    g.drawLine(p.x, p.y, p2.x, p2.y);

                    last = p2;
                }
                if (imgC.lassoPoints.size() > 0) {
                    if (last == null) {
                        last = imgC.lassoPoints.get(0);
                    }
                    g.drawLine(last.x, last.y, evt.getX(), evt.getY());
                }
            }
        }


    }//GEN-LAST:event_canvasMouseMoved

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        imgC.resetPolyTool();
        renderC.updatePreviewImage(canvasWidth, canvasHeight);
        renderC.drawPreview(canvas);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jSlider1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSlider1StateChanged
        float opacity = (float) (jSlider1.getValue() / 100.0);
        Layer l = renderC.getLayer(renderC.selectedLayer);
        l.opacity = opacity;
        renderC.updatePreviewImage(canvasWidth, canvasHeight);
        renderC.drawPreview(canvas);
    }//GEN-LAST:event_jSlider1StateChanged

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        int newPos = renderC.moveCurrentLayer(1);
        updateLayer();
        jTable1.getSelectionModel().setSelectionInterval(newPos, newPos);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        int newPos = renderC.moveCurrentLayer(-1);
        updateLayer();
        jTable1.getSelectionModel().setSelectionInterval(newPos, newPos);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jMenuItemDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemDeleteActionPerformed
        if (renderC.mode != null) {
            if (renderC.mode.equals(RenderController.MODE_POLY_LASSO) && imgC.lassoComplete) {
                imgC.removeArea(renderC.getLayer(renderC.selectedLayer));
                renderC.updatePreviewImage(canvasWidth, canvasHeight);
                renderC.drawPreview(canvas);
            }
        }
        else {
            renderC.deleteSelectedLayer();
            updateLayer();
        }
    }//GEN-LAST:event_jMenuItemDeleteActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        renderC.addWhiteLayer(canvasWidth, canvasHeight);
        updateLayer();
        renderC.updatePreviewImage(canvasWidth, canvasHeight);
        renderC.drawPreview(canvas);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void canvasMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_canvasMouseReleased
        if (imgC.lassoComplete && imgSelection != null) {
            imgC.updateSelectionArray();
            selectionPrevX = selectionMoveX;
            selectionPrevY = selectionMoveY;
        }
    }//GEN-LAST:event_canvasMouseReleased

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed

    }//GEN-LAST:event_jMenuItem3ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        //        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        }
//        catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */

        try {
            javax.swing.UIManager.setLookAndFeel(new WindowsLookAndFeel());
        }
        catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel canvas;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItemDelete;
    private javax.swing.JMenuItem jMenuItemPaste;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JSlider jSlider1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables

    public void updateLayer() {
        DefaultTableModel tb = (DefaultTableModel) jTable1.getModel();
        tb.setRowCount(0);
        for (Layer layer : renderC.layerList) {
            tb.addRow(new String[]{layer.name});
        }
        if (!renderC.layerList.isEmpty()) {
            jTable1.getSelectionModel().setSelectionInterval(0, 0);
        }
    }

    private void tranformMouseDrag(MouseEvent evt) {
        Layer layer = renderC.getLayer(renderC.selectedLayer);
        double x = evt.getX();
        double y = evt.getY();

        double lWidth = tempImage.getWidth();
        double lHeight = tempImage.getHeight();
        BufferedImage scaled;
        boolean isShiftScale = evt.isShiftDown();
        switch (canvas.getCursor().getType()) {
            case Cursor.NW_RESIZE_CURSOR:
                scaled = new ImageController().getScaled(tempImage, ((pressX - x) / lWidth) + 1, ((pressY - y) / lHeight) + 1, isShiftScale);
                layer.image = scaled;
                layer.width = scaled.getWidth();
                layer.height = scaled.getHeight();
                layer.x = pressTranformX - layer.width;//+ (-pressX + evt.getX());
                layer.y = pressTranformY - layer.height;//+ (-pressY + evt.getY());
                break;
            case Cursor.NE_RESIZE_CURSOR:
                scaled = new ImageController().getScaled(tempImage, ((-pressX + x) / lWidth) + 1, ((pressY - y) / lHeight) + 1, isShiftScale);
                layer.image = scaled;
                layer.width = scaled.getWidth();
                layer.height = scaled.getHeight();
                layer.x = pressTranformX;
                layer.y = pressTranformY - layer.height;
                break;
            case Cursor.SW_RESIZE_CURSOR:
                scaled = new ImageController().getScaled(tempImage, ((pressX - x) / lWidth) + 1, ((-pressY + y) / lHeight) + 1, isShiftScale);
                layer.image = scaled;
                layer.width = scaled.getWidth();
                layer.height = scaled.getHeight();
                layer.x = pressTranformX - layer.width;
                layer.y = pressTranformY;
                break;
            case Cursor.SE_RESIZE_CURSOR:
                scaled = new ImageController().getScaled(tempImage, ((-pressX + x) / lWidth) + 1, ((-pressY + y) / lHeight) + 1, isShiftScale);
                layer.image = scaled;
                layer.width = scaled.getWidth();
                layer.height = scaled.getHeight();
                layer.x = pressTranformX;
                layer.y = pressTranformY;
                break;

            case Cursor.N_RESIZE_CURSOR:
                scaled = new ImageController().getScaled(tempImage, 1.0, ((pressY - y) / lHeight) + 1, false);
                layer.image = scaled;
                layer.height = scaled.getHeight();
                layer.y = pressTranformY - layer.height;
                break;
            case Cursor.E_RESIZE_CURSOR:
                scaled = new ImageController().getScaled(tempImage, ((-pressX + x) / lWidth) + 1, 1.0, false);
                layer.image = scaled;
                layer.width = scaled.getWidth();
                layer.x = pressTranformX;
                break;
            case Cursor.S_RESIZE_CURSOR:
                scaled = new ImageController().getScaled(tempImage, 1.0, ((-pressY + y) / lHeight) + 1, false);
                layer.image = scaled;
                layer.height = scaled.getHeight();
                layer.y = pressTranformY;
                break;
            case Cursor.W_RESIZE_CURSOR:
                scaled = new ImageController().getScaled(tempImage, ((pressX - x) / lWidth) + 1, 1.0, false);
                layer.image = scaled;
                System.out.println(layer.width + "->" + scaled.getWidth());
                layer.width = scaled.getWidth();
                layer.x = pressTranformX - layer.width;
                break;

        }
        renderC.updatePreviewImage(canvasWidth, canvasHeight);
        renderC.drawPreview(canvas);
    }

    private void moveMouseDrag(MouseEvent evt) {
        new Thread() {
            public void run() {

                int selectedLayerNum = jTable1.getSelectedRow();
                if (selectedLayerNum == -1) {
                    return;
                }
                Layer selectedLayer = renderC.getLayer(selectedLayerNum);
                selectedLayer.x = pressImgX + (-pressX + evt.getX());
                selectedLayer.y = pressImgY + (-pressY + evt.getY());
                renderC.updatePreviewImage(canvasWidth, canvasHeight);
                renderC.drawPreview(canvas);
            }
        }.start();
    }

}
