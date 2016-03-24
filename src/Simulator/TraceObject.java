/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Simulator;

/**
 *
 * @author behnish
 */
public class TraceObject {

    String type;
    int id;
    double simulationClock;
    boolean eventAddedOrNot = false;
    boolean animationFinished = false;
    int patientId;    
    Rectangle rect;
    
    private double waitTime1;
    private double waitTime2;
    private int surgeonId;
    private int operationSurgeonId;

    public int getSurgeonId() {
        return surgeonId;
    }

    public void setSurgeonId(int surgeonId) {
        this.surgeonId = surgeonId;
    }

    public int getOperationSurgeonId() {
        return operationSurgeonId;
    }

    public void setOperationSurgeonId(int operationSurgonId) {
        this.operationSurgeonId = operationSurgonId;
    }
    
    

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
    
    int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
    
    

    public Rectangle getRect() {
        return rect;
    }

    public void setRect(Rectangle rect) {
        this.rect = rect;
    }
    
    
    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getSimulationClock() {
        return simulationClock;
    }

    public void setSimulationClock(double simulationClock) {
        this.simulationClock = simulationClock;
    }

    public boolean isEventAddedOrNot() {
        return eventAddedOrNot;
    }

    public void setEventAddedOrNot(boolean eventAddedOrNot) {
        this.eventAddedOrNot = eventAddedOrNot;
    }

    public boolean isAnimationFinished() {
        return animationFinished;
    }

    public void setAnimationFinished(boolean animationFinished) {
        this.animationFinished = animationFinished;
    }
}
