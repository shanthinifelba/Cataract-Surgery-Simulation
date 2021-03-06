/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Simulator;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Queue;
import javax.swing.JSlider;

/**
 * // TODO add your handling code here:
 *
 * @author IDontKnow
 */
public class Animation extends javax.swing.JPanel implements MouseWheelListener {

    private int surgons = 1;
    private int ors = 1;

    private int startX = 30;
    private int startY = 50;

    protected int gap = 20;
    protected int verticalGap = 50;
    protected int queueSurgonGap = 10;

    protected int queueBoxWidth = 6;
    protected int queueBoxHeight = 20;

    protected int sugonBoxWidth = 80;
    protected int sugonBoxHeight = 40;

    protected int scaleGap = 60;

    protected Rectangle gpRef;
    protected Rectangle recoveryZone = new Rectangle();

    protected LinkedHashMap<Integer, Rectangle> surBox = new LinkedHashMap<>();
    protected LinkedHashMap<Integer, Rectangle> globalQueueStorage = new LinkedHashMap<>();
    protected LinkedHashMap<Integer, Rectangle> operationRooms = new LinkedHashMap<>();

    protected LinkedHashMap<Integer, LinkedHashMap<Integer, Rectangle>> surgonQStorage = new LinkedHashMap<>();
    protected LinkedHashMap<Integer, LinkedHashMap<Integer, Rectangle>> operationQStorage = new LinkedHashMap<>();

    protected LinkedHashMap<Integer, LinkedHashMap<Integer, TraceObject>> surgonQueue = new LinkedHashMap<>();
    protected LinkedHashMap<Integer, LinkedHashMap<Integer, TraceObject>> operationQueue = new LinkedHashMap<>();

    protected double zoomFactor = 1.0D;

    public Animation() {
        if (ObjectFactory.getSimulation() != null) {
            surgons = ObjectFactory.getSimulation().sur;
            ors = ObjectFactory.getSimulation().ors;
        }
        initComponents();
        addMouseWheelListener(this);
        loadGP();
        generateSurgons();
        operationRooms();
        initializeQueues();

        ObjectFactory.setAnimation(this);
    }

    private void initializeQueues() {
        for (int i = 1; i <= surgons; i++) {
            if (!Configuration.global) {
                surgonQueue.put(i, new LinkedHashMap<Integer, TraceObject>());
                operationQueue.put(i, new LinkedHashMap<Integer, TraceObject>());
            } else {
                surgonQueue.put(i, new LinkedHashMap<Integer, TraceObject>());
                operationQueue.put(1, new LinkedHashMap<Integer, TraceObject>());
            }
        }
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
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        speed = new javax.swing.JSlider();
        jLabel3 = new javax.swing.JLabel();
        day = new javax.swing.JLabel();
        stop = new javax.swing.JButton();

        jLabel1.setText("jLabel1");

        setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setText("Speed");

        speed.setMaximum(1000);
        speed.setMinorTickSpacing(100);
        speed.setPaintLabels(true);
        speed.setPaintTicks(true);
        speed.setValue(10);
        speed.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                speedStateChanged(evt);
            }
        });

        jLabel3.setText("Current Day");

        day.setText("1");

        stop.setText("Stop");
        stop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stopActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(speed, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(jLabel3)
                .addGap(37, 37, 37)
                .addComponent(day, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(stop)
                .addContainerGap(106, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(speed, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(day)
                        .addComponent(stop)))
                .addGap(0, 512, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void speedStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_speedStateChanged
        // TODO add your handling code here:
        JSlider source = (JSlider) evt.getSource();
        if (!source.getValueIsAdjusting()) {
            Configuration.speed = source.getValue();
        }
    }//GEN-LAST:event_speedStateChanged

    private void stopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stopActionPerformed
        // TODO add your handling code here:
        if(ObjectFactory.getMainFrame().getS()!=null)
        {
            ObjectFactory.getMainFrame().getS().simulation = false;
        }
        
    }//GEN-LAST:event_stopActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel day;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JSlider speed;
    private javax.swing.JButton stop;
    // End of variables declaration//GEN-END:variables
    /* 
     **********************************************************************************************************************
     **********************************************************************************************************************
     **********************************************************************************************************************
     Drawing the Boxes and Queues Begins here
     **********************************************************************************************************************
     **********************************************************************************************************************
     **********************************************************************************************************************
     */

    private void loadGP() {
        gpRef = new Rectangle();
        gpRef.setX(startX);
        gpRef.setY(startY);
        gpRef.setWidth(100);
        gpRef.setHeight(300);

        startX += gpRef.getWidth() + gap;
        startY += gap;

    }

    private void generateSurgons() {
        int tempX = 0;
        int tempY = 0;

        for (int i = 1; i <= surgons; i++) {
            tempX = startX;
            tempY = startY;

            surgonQStorage.put(i, new LinkedHashMap<Integer, Rectangle>());
            operationQStorage.put(i, new LinkedHashMap<Integer, Rectangle>());
            for (int j = 1; j <= 30; j++) {
                Rectangle r = new Rectangle();
                r.setId(i);
                r.setX(startX);
                r.setY(startY);
                r.setWidth(queueBoxWidth);
                r.setHeight(queueBoxHeight);
                surgonQStorage.get(i).put(j, r);
                startX += queueBoxWidth;
            }
            startX += gap;
            startY -= queueSurgonGap;

            Rectangle surgonR = new Rectangle();
            surgonR.setSurgon(true);
            surgonR.setX(startX);
            surgonR.setY(startY);
            surgonR.setWidth(sugonBoxWidth);
            surgonR.setHeight(sugonBoxHeight);
            surgonR.setName("Surgon " + i);

            surBox.put(i, surgonR);

            startX += sugonBoxWidth + gap;
            startY += queueSurgonGap;

            if (!Configuration.global) {
                for (int j = 1; j <= 30; j++) {
                    Rectangle r = new Rectangle();
                    r.setX(startX);
                    r.setY(startY);
                    r.setId(i);
                    r.setColor(Color.GREEN);
                    r.setWidth(queueBoxWidth);
                    r.setHeight(queueBoxHeight);
                    operationQStorage.get(i).put(j, r);
                    startX += queueBoxWidth;
                }
            }
            if (i != surgons) {
                startX = tempX;
                startY = tempY;

                startY += scaleGap;
            }
        }
        if (Configuration.global) {
            for (int j = 1; j <= 30; j++) {

                Rectangle globalQueue = new Rectangle();
                int y = tempY / 2;
                int x = startX + gap;
                globalQueue.setColor(Color.GREEN);
                globalQueue.setId(j);
                globalQueue.setX(x);
                globalQueue.setY(y);
                globalQueue.setWidth(10);
                globalQueue.setHeight(20);
                globalQueueStorage.put(j, globalQueue);
                startX += 10;

                operationQStorage.put(1, globalQueueStorage);
            }
        }
    }

    private void operationRooms() {
        int orginalX = startX;
        int orginalY = startY;

        startX += gap * 2;
        if (ors <= 5) {
            startY = startY / 3;
        } else {
            startY = startY / 4;
        }
        for (int i = 1; i <= ors; i++) {
            int tempX = startX;
            int tempY = startY;
            Rectangle r = new Rectangle();
            r.setOperationRoomCheck(true);
            r.setId(i);
            r.setX(startX);
            r.setY(startY);
            r.setWidth(sugonBoxWidth * 2);
            r.setHeight(sugonBoxHeight);
            r.setName("Operation Room " + i);

            operationRooms.put(i, r);

            if (i != ors) {
                startX = tempX;
                startY = tempY;

                startY += scaleGap;
            }
        }

        recoveryZone.setRecoveryZoneRoomCheck(true);
        recoveryZone.setX(orginalX + sugonBoxWidth * 3 + gap);
        recoveryZone.setY(orginalY / 3);
        recoveryZone.setWidth(sugonBoxWidth);
        recoveryZone.setHeight(200);

    }

    protected LinkedHashMap<Integer, Queue<TraceObject>> events = new LinkedHashMap<>();

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(2));
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        
        day.setText(String.valueOf(Math.round(ObjectFactory.getAnimationClock()/600)));

        g2.scale(this.zoomFactor, this.zoomFactor);

        g2.setFont(new Font("TimesRoman", Font.PLAIN, 12));

        g2.drawRect(gpRef.getX(), gpRef.getY(), gpRef.getWidth(), gpRef.getHeight());
        g2.drawString("GP Referral ", gpRef.getX(), gpRef.getY() + gpRef.getHeight() + 15);

        drawSurgonStuff(g2);
        drawOperationRooms(g2);

        g2.drawRect(recoveryZone.getX(), recoveryZone.getY(), recoveryZone.getWidth(), recoveryZone.getHeight());
        g2.drawString("Recovery Zone ", recoveryZone.getX(), recoveryZone.getY() + recoveryZone.getHeight() + 15);

        animateMe(g2);
    }

    private void drawOperationRooms(Graphics2D g2) {
        for (Map.Entry<Integer, Rectangle> entry : operationRooms.entrySet()) {
            Rectangle rectangle = entry.getValue();
            rectangle.drawName(g2);
            g2.drawRect(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight());
        }
    }

    private void drawSurgonStuff(Graphics2D g2) {
        for (Map.Entry<Integer, LinkedHashMap<Integer, Rectangle>> entry : surgonQStorage.entrySet()) {
            LinkedHashMap<Integer, Rectangle> linkedHashMap = entry.getValue();

            for (Map.Entry<Integer, Rectangle> entry1 : linkedHashMap.entrySet()) {
                Integer integer1 = entry1.getKey();
                Rectangle rectangle = entry1.getValue();
                rectangle.draw(g2);
                if (integer1 == 30) {
                    drawRectMy2(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight(), g2);
                } else {
                    drawRectMy(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight(), g2);
                }
            }
        }
        for (Map.Entry<Integer, LinkedHashMap<Integer, Rectangle>> entry : operationQStorage.entrySet()) {
            LinkedHashMap<Integer, Rectangle> linkedHashMap = entry.getValue();

            for (Map.Entry<Integer, Rectangle> entry1 : linkedHashMap.entrySet()) {
                Integer integer1 = entry1.getKey();
                Rectangle rectangle = entry1.getValue();
                rectangle.draw(g2);
                if (integer1 == 30) {
                    drawRectMy2(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight(), g2);
                } else {
                    drawRectMy(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight(), g2);
                }
            }
        }
        for (Map.Entry<Integer, Rectangle> entry : surBox.entrySet()) {
            Rectangle rectangle = entry.getValue();
            rectangle.drawName(g2);
            g2.drawRect(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight());
        }
    }

    public void drawRectMy(int x, int y, int width, int height, Graphics2D g) {
        if ((width < 0) || (height < 0)) {
            return;
        }

        if (height == 0 || width == 0) {
            g.drawLine(x, y, x + width, y + height);
        } else {
            g.drawLine(x, y, x + width - 1, y); // up
            g.drawLine(x + width, y + height, x + 1, y + height);
        }
    }

    public void drawRectMy2(int x, int y, int width, int height, Graphics2D g) {
        if ((width < 0) || (height < 0)) {
            return;
        }

        if (height == 0 || width == 0) {
            g.drawLine(x, y, x + width, y + height);
        } else {
            g.drawLine(x, y, x + width - 1, y); // up
            g.drawLine(x + width, y, x + width, y + height - 1);
            g.drawLine(x + width, y + height, x + 1, y + height);
        }
    }

    /* 
     **********************************************************************************************************************
     **********************************************************************************************************************
     **********************************************************************************************************************
     Animation Logic
     **********************************************************************************************************************
     **********************************************************************************************************************
     **********************************************************************************************************************
     */
    private void animateMe(Graphics2D g) {
        int queueID, patientID;

        for (Map.Entry<Integer, TraceObject> entry : ObjectFactory.getDrawingList().entrySet()) {
            int index = entry.getKey();
            TraceObject traceObject = entry.getValue();

            switch (traceObject.getType()) {
                case "Q_REC":
                    queueID = traceObject.getId();
                    patientID = traceObject.getPatientId();

                    surgonQueue.get(queueID).put(patientID, traceObject);
                    ObjectFactory.getDrawingList().remove(index);
                    break;
                case "Q_PRO":
                    queueID = traceObject.getId();
                    patientID = traceObject.getPatientId();

                    surgonQueue.get(queueID).remove(patientID);
                    ObjectFactory.getDrawingList().remove(index);
                    break;
                case "S_REC":
                    queueID = traceObject.getId();
                    surBox.get(queueID).setEmpty(false);
                    ObjectFactory.getDrawingList().remove(index);
                    break;
                case "S_PRO":
                    queueID = traceObject.getId();
                    surBox.get(queueID).setEmpty(true);
                    ObjectFactory.getDrawingList().remove(index);
                    break;
                case "ORQ_REC":
                    queueID = traceObject.getId();
                    patientID = traceObject.getPatientId();

                    if (Configuration.global) {
                        operationQueue.get(1).put(patientID, traceObject);
                    } else {
                        operationQueue.get(queueID).put(patientID, traceObject);
                    }
                    ObjectFactory.getDrawingList().remove(index);

                    ObjectFactory.getDrawingList().remove(index);
                    break;
                case "ORQ_PRO":
                    queueID = traceObject.getId();
                    patientID = traceObject.getPatientId();

                    if (Configuration.global) {
                        operationQueue.get(1).remove(patientID);
                    } else {
                        operationQueue.get(queueID).remove(patientID);
                    }

                    ObjectFactory.getDrawingList().remove(index);

                    break;
                case "OP_START":
                    queueID = traceObject.getId();
                    operationRooms.get(queueID).setEmpty(false);
                    ObjectFactory.getDrawingList().remove(index);
                    break;
                case "OP_END":
                    queueID = traceObject.getId();
                    operationRooms.get(queueID).setEmpty(true);
                    ObjectFactory.getDrawingList().remove(index);
                    break;
                case "RZ_ENT":
                    recoveryZone.setEmpty(false);
                    recoveryZone.addRecoveryPatients();
                    ObjectFactory.getDrawingList().remove(index);
                    break;
            }
        }

        for (Map.Entry<Integer, LinkedHashMap<Integer, Rectangle>> entry : surgonQStorage.entrySet()) {
            LinkedHashMap<Integer, Rectangle> linkedHashMap = entry.getValue();
            for (Map.Entry<Integer, Rectangle> entry1 : linkedHashMap.entrySet()) {
                Rectangle rectangle = entry1.getValue();
                rectangle.setEmpty(true);
            }
        }

        for (Map.Entry<Integer, LinkedHashMap<Integer, TraceObject>> entry : surgonQueue.entrySet()) {
            Integer integer = entry.getKey();
            LinkedHashMap<Integer, TraceObject> linkedHashMap = entry.getValue();
            int counter = 30;
            for (Map.Entry<Integer, TraceObject> entry1 : linkedHashMap.entrySet()) {
                TraceObject traceObject = entry1.getValue();
                if (counter > 0) {
                    traceObject.setRect(surgonQStorage.get(integer).get(counter));
                    surgonQStorage.get(integer).get(counter).setEmpty(false);
                    counter--;
                }
            }
        }

        /*----------------------------Operation queue --------------------------------- */
        //if (!Configuration.global) {
        for (Map.Entry<Integer, LinkedHashMap<Integer, Rectangle>> entry : operationQStorage.entrySet()) {
            LinkedHashMap<Integer, Rectangle> linkedHashMap = entry.getValue();
            for (Map.Entry<Integer, Rectangle> entry1 : linkedHashMap.entrySet()) {
                Rectangle rectangle = entry1.getValue();
                rectangle.setEmpty(true);
            }
        }

        for (Map.Entry<Integer, LinkedHashMap<Integer, TraceObject>> entry : operationQueue.entrySet()) {
            Integer integer = entry.getKey();
            LinkedHashMap<Integer, TraceObject> linkedHashMap = entry.getValue();
            int counter = 30;
            for (Map.Entry<Integer, TraceObject> entry1 : linkedHashMap.entrySet()) {
                TraceObject traceObject = entry1.getValue();
                if (counter > 0) {
                    traceObject.setRect(operationQStorage.get(integer).get(counter));
                    operationQStorage.get(integer).get(counter).setEmpty(false);
                    counter--;
                }
            }
        }

        /* -----------------------operation rooms and surgon box and recovery rooms drawing */
        for (Map.Entry<Integer, Rectangle> entry : surBox.entrySet()) {
            Rectangle rectangle = entry.getValue();
            rectangle.draw(g);
        }

        for (Map.Entry<Integer, Rectangle> entry : operationRooms.entrySet()) {
            Rectangle rectangle = entry.getValue();
            rectangle.draw(g);
        }                

        recoveryZone.draw(g);

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        int notches = e.getWheelRotation();
        if (notches < 0) {
            zoomIn();
        } else {
            zoomOut();
        }
        invalidate();
        repaint();
    }

    public void setZoomFactor(double zoomFactor) {
        this.zoomFactor = zoomFactor;
    }

    public void zoomIn() {
        this.zoomFactor += 0.05D;
    }

    public void zoomOut() {
        this.zoomFactor -= 0.1D;
        if (this.zoomFactor < 0.1D) {
            zoomIn();
        }
    }

}
