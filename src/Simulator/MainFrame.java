/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Simulator;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author behnish
 */
public class MainFrame extends javax.swing.JFrame {

    /**
     * Creates new form MainFrame
     */
    public int widthOfDrawPanel = 1107;
    public int heightOfDrawPanel = 815; // 1107,815 -- old 1093, 727

    public MainFrame() {
        initLookAndFeel();
        setSize(new Dimension(widthOfDrawPanel, heightOfDrawPanel));
        setTitle("Cataract Surgey Simulation");
        initComponents();
        setSize(widthOfDrawPanel, heightOfDrawPanel);
        sizeListener();
    }

    private void sizeListener() {
        setLocationRelativeTo(null);

        ObjectFactory.setMainFrame(this);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                widthOfDrawPanel = getWidth();
                heightOfDrawPanel = getHeight() - 25;

                validate();
                repaint();
            }
        });
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
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu2 = new javax.swing.JMenu();
        parameter = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        animationPanel = new javax.swing.JMenuItem();
        loadTrace = new javax.swing.JMenuItem();
        start = new javax.swing.JMenuItem();
        zoomIn = new javax.swing.JMenuItem();
        zoomOut = new javax.swing.JMenuItem();
        reset = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        performance = new javax.swing.JMenuItem();
        jMenu5 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jMenuItem1.setText("jMenuItem1");

        jMenuItem5.setText("jMenuItem5");

        jMenuItem7.setText("jMenuItem7");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jMenu2.setText("Simulation ");

        parameter.setText("Parameter Setting");
        parameter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                parameterActionPerformed(evt);
            }
        });
        jMenu2.add(parameter);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("Animation");

        animationPanel.setText("Animation Panel");
        animationPanel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                animationPanelActionPerformed(evt);
            }
        });
        jMenu3.add(animationPanel);

        loadTrace.setText("Load Trace");
        loadTrace.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadTraceActionPerformed(evt);
            }
        });
        jMenu3.add(loadTrace);

        start.setText("Start Animation");
        start.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startActionPerformed(evt);
            }
        });
        jMenu3.add(start);

        zoomIn.setText("Zoom In");
        zoomIn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zoomInActionPerformed(evt);
            }
        });
        jMenu3.add(zoomIn);

        zoomOut.setText("Zoom Out");
        zoomOut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zoomOutActionPerformed(evt);
            }
        });
        jMenu3.add(zoomOut);

        reset.setText("Reset");
        reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetActionPerformed(evt);
            }
        });
        jMenu3.add(reset);

        jMenuBar1.add(jMenu3);

        jMenu4.setText("Performance");

        performance.setText("Graphs");
        performance.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                performanceActionPerformed(evt);
            }
        });
        jMenu4.add(performance);

        jMenuBar1.add(jMenu4);

        jMenu5.setText("Help");

        jMenuItem2.setText("About");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem2);

        jMenuBar1.add(jMenu5);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void parameterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_parameterActionPerformed
        // TODO add your handling code here:
        getContentPane().removeAll();
        Parameter p;
        if (ObjectFactory.getParmeters() != null) {
            p = ObjectFactory.getParmeters();
        } else {
            p = new Parameter();
            ObjectFactory.setParmeters(p);
        }
        p.setBounds(10, 10, widthOfDrawPanel - 20, heightOfDrawPanel - 20);
        p.simulate.setEnabled(true);
        getContentPane().add(p, BorderLayout.CENTER);
        refreshFrame();
    }//GEN-LAST:event_parameterActionPerformed

    public void refreshFrame() {
        repaint();
        validate();
    }

    private void animationPanelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_animationPanelActionPerformed
        // TODO add your handling code here:
        getContentPane().removeAll();
        Animation p;
        if (ObjectFactory.getAnimation() != null) {
            p = ObjectFactory.getAnimation();
        } else {
            p = new Animation();
            ObjectFactory.setAnimation(p);
        }

        p.setBounds(10, 10, widthOfDrawPanel - 20, heightOfDrawPanel - 20);
        getContentPane().add(p, BorderLayout.CENTER);
        refreshFrame();
    }//GEN-LAST:event_animationPanelActionPerformed

    private void performanceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_performanceActionPerformed
        // TODO add your handling code here:
        getContentPane().removeAll();
        Performance p;
        if (ObjectFactory.getPerformance() != null) {
            p = ObjectFactory.getPerformance();
        } else {
            try {
                p = new Performance();
                ObjectFactory.setPerformance(p);
                p.setBounds(10, 10, widthOfDrawPanel - 20, heightOfDrawPanel - 20);
                getContentPane().add(p, BorderLayout.CENTER);
                refreshFrame();
            } catch (IOException ex) {
                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }


    }//GEN-LAST:event_performanceActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void loadTraceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadTraceActionPerformed
        // TODO add your handling code here:
        LoadTrace l = new LoadTrace("tracefile");
        try {
            l.loadTraceIntoDS();

        } catch (IOException ex) {
            Logger.getLogger(MainFrame.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_loadTraceActionPerformed
    
    ScreenRefresher s;
            
    private void startActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startActionPerformed
        // TODO add your handling code here:
        s = new ScreenRefresher();
        s.setName("Screen refresher");
        s.start();
    }//GEN-LAST:event_startActionPerformed

    public ScreenRefresher getS() {
        return s;
    }

    private void zoomInActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zoomInActionPerformed
        // TODO add your handling code here:
        ObjectFactory.getAnimation().zoomIn();
        ObjectFactory.getAnimation().revalidate();
        ObjectFactory.getAnimation().repaint();
    }//GEN-LAST:event_zoomInActionPerformed

    private void zoomOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zoomOutActionPerformed
        // TODO add your handling code here:
        ObjectFactory.getAnimation().zoomOut();
        ObjectFactory.getAnimation().revalidate();
        ObjectFactory.getAnimation().repaint();
    }//GEN-LAST:event_zoomOutActionPerformed

    private void resetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetActionPerformed
        // TODO add your handling code here:        
        ObjectFactory.getAnimation().setZoomFactor(1.0D);
        ObjectFactory.getAnimation().revalidate();
        ObjectFactory.getAnimation().repaint();

    }//GEN-LAST:event_resetActionPerformed

    private void initLookAndFeel() {
        String className = UIManager.getSystemLookAndFeelClassName();
        try {
            UIManager.setLookAndFeel(className);
        } catch (ClassNotFoundException |
                InstantiationException |
                IllegalAccessException |
                UnsupportedLookAndFeelException ex) {
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem animationPanel;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JMenuItem loadTrace;
    private javax.swing.JMenuItem parameter;
    private javax.swing.JMenuItem performance;
    private javax.swing.JMenuItem reset;
    private javax.swing.JMenuItem start;
    private javax.swing.JMenuItem zoomIn;
    private javax.swing.JMenuItem zoomOut;
    // End of variables declaration//GEN-END:variables
}