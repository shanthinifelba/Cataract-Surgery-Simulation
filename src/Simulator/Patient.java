/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Simulator;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author behnish
 */
public class Patient {

    private boolean expired = false;
    private double waitTime1;
    private double waitTime2;
    private double startTime;
    private double endTime;
    private double criticalLevel;
    private int priorty;
    private int estimatedTime;
    
    private int doctorId;
    private int surgonId;
    private int operationRoom;
    private int recoveryRoomID;    
    
    private int operationSurgonId;
    private double operationQueueEntryTime;

    public double getOperationQueueEntryTime() {
        return operationQueueEntryTime;
    }

    public void setOperationQueueEntryTime(double operationQueueEntryTime) {
        this.operationQueueEntryTime = operationQueueEntryTime;
    }    

    public int getOperationSurgonId() {
        return operationSurgonId;
    }

    public void setOperationSurgonId(int operationSurgonId) {
        this.operationSurgonId = operationSurgonId;
    }

    
    private int seqenceNumber;
    private boolean processed = false; 

    public double getWaitTime1() {
        return waitTime1;
    }

    public void setWaitTime1(double waitTime1) {
        this.waitTime1 = waitTime1;
    }

    public double getWaitTime2() {
        return waitTime2;
    }

    public void setWaitTime2(double waitTime2) {
        this.waitTime2 = waitTime2;
    }
    
    

    public boolean isProcessed() {
        return processed;
    }

    public void setProcessed(boolean processed) {
        this.processed = processed;
    }

    
    public int getOperationRoom() {
        return operationRoom;
    }

    public void setOperationRoom(int operationRoom) {
        this.operationRoom = operationRoom;
    }

    
    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public int getSurgonId() {
        return surgonId;
    }

    public void setSurgonId(int surgonId) {
        this.surgonId = surgonId;
    }

    public int getRecoveryRoomID() {
        return recoveryRoomID;
    }

    public void setRecoveryRoomID(int recoveryRoomID) {
        this.recoveryRoomID = recoveryRoomID;
    }
    
    

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    public double getStartTime() {
        return startTime;
    }

    public void setStartTime(double startTime) {
        this.startTime = startTime;
    }

    public double getEndTime() {
        return endTime;
    }

    public void setEndTime(double endTime) {
        this.endTime = endTime;
    }

    public int getPriorty() {
        return priorty;
    }

    public void setPriorty(int priorty) {
        this.priorty = priorty;
    }

    public int getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(int estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public double getCriticalLevel() {
        return criticalLevel;
    }

    public void setCriticalLevel(double criticalLevel) {
        this.criticalLevel = criticalLevel;
    }

    public Patient(int id) {
        this.seqenceNumber = id;
    }

    public int getSeqenceNumber() {
        return seqenceNumber;
    }

    
//    public Patient cloneME() {
//        Patient temp = new Patient(getSeqenceNumber());
//        temp.setCriticalLevel(getCriticalLevel());
//        temp.setStartTime(getStartTime());
//        temp.setDoctorId(getDoctorId());
//        temp.setSurgonId(getSurgonId());
//        temp.setRecoveryRoomID(getRecoveryRoomID());        
//        return temp;
//    }
    
    private double clock;
    
    public void setDelay(double delayPatient) {
        this.clock = delayPatient;
    }
    
    
    public double getDelay() {
        return clock;
    }
    
    int x = randInt(20, 100);
    int y = randInt(70, 350);
    int r = 10;
    
    public void draw(Graphics g)
    {
        g.setColor(Color.BLUE);
        g.drawOval(x, y, r, r);
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
    
   
    public static int randInt(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }
}
