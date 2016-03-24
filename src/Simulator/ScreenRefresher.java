/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Simulator;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author behnish
 */
public class ScreenRefresher extends Thread {

    boolean simulation = true;
    private int lastIndex;
    private int count = 1;

    public void setSimulation(boolean simulation) {
        this.simulation = simulation;
    }      
    private synchronized void incrementCounter() {
        count++;
    }

    @Override
    public void run() {
        ObjectFactory.setAnimationClock(ObjectFactory.getTrace().get(1).getSimulationClock());

        while (simulation) {
            putEvents();
            draw();
            checkMe();
            try {
                Thread.sleep(Configuration.speed);
            } catch (InterruptedException ex) {
                Logger.getLogger(ScreenRefresher.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void putEvents() {
        for (Map.Entry<Integer, TraceObject> en : ObjectFactory.getTrace().entrySet()) {
            Integer integer = en.getKey();
            TraceObject event = en.getValue();
            switch (event.getType()) {
                case "Q_REC":
                case "Q_PRO":                    
                case "S_REC":
                case "S_PRO":                    
                case "ORQ_REC":
                case "ORQ_PRO":                    
                case "OP_START":
                case "OP_END":                    
                case "RZ_ENT":                                        
                    if ((event.getSimulationClock() <= ObjectFactory.getAnimationClock()) && !event.isEventAddedOrNot()) {                        
                        event.setEventAddedOrNot(true);
                        lastIndex = integer;
                        ObjectFactory.getDrawingList().put(count, event);
                        incrementCounter();
                    }
                    break;                   
            }
        }
    }

    private void checkMe() {
        if (ObjectFactory.getDrawingList().isEmpty()) {
            if (ObjectFactory.getTrace().get(lastIndex + 1) != null) {
                ObjectFactory.setAnimationClock(ObjectFactory.getTrace().get(lastIndex + 1).getSimulationClock());
            }
        }
    }

    private void draw() {
        ObjectFactory.getAnimation().repaint();
    }

    private void updateLocations() {
        for (Map.Entry<Integer, TraceObject> entry : ObjectFactory.getDrawingList().entrySet()) {
            TraceObject traceObject = entry.getValue();
            if (traceObject.getType().equals("Q_REC")) {

            }
        }
    }
}
