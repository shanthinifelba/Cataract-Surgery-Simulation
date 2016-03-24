/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Simulator;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Adiba
 */
public class Rectangle {

    private int id;
    private int x;
    private int y;
    private int width;
    private int height;
    private boolean empty = true;
    private boolean surgon = false;
    private int currentPatientId;
    private String name;

    private Color color = Color.RED;

    private boolean operationRoomCheck = false;
    private boolean recoveryZoneRoomCheck = false;
    private ArrayList<Location> recoveryPatients = new ArrayList<>();

    public void addRecoveryPatients() {
        if(recoveryPatients.size()>7)
        {
            recoveryPatients.clear();
        }
        double x1 = (Math.random() * (x + width - x)) + x;
        double y1 = (Math.random() * (y + width - y)) + y;
        Location l = new Location();
        l.setX((int) x1);
        l.setY((int) y1);
        recoveryPatients.add(l);
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public boolean isOperationRoomCheck() {
        return operationRoomCheck;
    }

    public void setOperationRoomCheck(boolean operationRoomCheck) {
        this.operationRoomCheck = operationRoomCheck;
    }

    public boolean isRecoveryZoneRoomCheck() {
        return recoveryZoneRoomCheck;
    }

    public void setRecoveryZoneRoomCheck(boolean recoveryZoneRoomCheck) {
        this.recoveryZoneRoomCheck = recoveryZoneRoomCheck;
    }

    public int getCurrentPatientId() {
        return currentPatientId;
    }

    public void setCurrentPatientId(int currentPatientId) {
        this.currentPatientId = currentPatientId;
    }

    public boolean isSurgon() {
        return surgon;
    }

    public void setSurgon(boolean surgon) {
        this.surgon = surgon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }

    public void draw(Graphics g2) {
        Graphics2D g = (Graphics2D) g2;
        if (!empty && !surgon && !operationRoomCheck && !recoveryZoneRoomCheck) {
            g.setColor(color);
            g.drawLine(x, y + 5, x, y + 15);
            g.setColor(Color.BLACK);
        }
        if (!empty) {
            if (surgon) {
                g.setColor(Color.BLUE);
                g.drawOval(x + 20, y + 5, 20, 20);
                g.setColor(Color.BLACK);
            } else if (operationRoomCheck) {
                g.setColor(Color.RED);
                g.drawOval(x + 20, y + 5, 10, 10);
                g.setColor(Color.BLACK);
            } else if (recoveryZoneRoomCheck) {
                for (Location location : recoveryPatients) {
                    g.drawOval(location.getX(), location.getY(), 5, 5);
                }
            }
        }
    }

    public void drawName(Graphics g2) {
        g2.drawString(name, x, y - 5);
    }
}
