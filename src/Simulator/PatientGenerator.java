/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Simulator;

import dataStructure.OperationQueue;
import dataStructure.Surgeon;
import java.util.LinkedHashMap;
import java.util.Map;
import library.Algorithm;
import library.Link;
import unbc.ca.distributed.distributions.Generator;

/**
 *
 * @author behnish
 */
public class PatientGenerator extends Algorithm {

    int linkCounter = 1;
    int backlogpaitent;
    Map<Integer, Link> links = new LinkedHashMap<>();
    Generator u = null;
    Generator p = null;

    public int getBacklogpaitent() {
        return backlogpaitent;
    }

    public void setBacklogpaitent(int backlogpaitent) {
        this.backlogpaitent = backlogpaitent;
    }

    public PatientGenerator(String name) {
        super(name);
    }

    public void addLink(Link l) {
        links.put(linkCounter, l);
        add_port(l);
        linkCounter++;
    }

    private Link returnLink() {
        if (links.size() == 1) {
            return links.get(1);
        } else {
            if (u == null) {
                u = Utilities.returnDistribution("Uniform", 1, links.size());
            }

            int value = u.generate();
            return links.get(value);
        }
    }

    public int delay() {
        if (p == null) {
            p = Utilities.returnDistribution("Poisson", Configuration.delayBetweenPatientInADay, 2);
        }
        return (int) p.generate();
    }
    boolean firstDay = false;

    @Override
    public void init() {
        if (Configuration.global) {
            global();
        } else {
            local();
        }
    }

    private void local() {
         /* For day 1 */
        if (ObjectFactory.currentDay == 1 && !firstDay) {

            for (Map.Entry<Integer, OperationQueue> entry : ObjectFactory.getSimulation().getOperationQ().entrySet()) {                
                OperationQueue operationQueue = entry.getValue();                
                for (Map.Entry<Integer, Link> entry1 : operationQueue.getLinks().entrySet()) {                    
                    Link link = entry1.getValue();
                    link.setOpen(false);                    
                }                
            }

            /* Enabling the surgeons to see patients on their consulting days */
            for (Map.Entry<Integer, Surgeon> entry : ObjectFactory.getSimulation().getSurg().entrySet()) {
                Surgeon surgeon = entry.getValue();
                if (ObjectFactory.getSurgeonSchedule().get(surgeon.getid()).get(ObjectFactory.currentDay).equals("CON")) {
                    ObjectFactory.getSimulation().getSurgQ().get(surgeon.getid()).getLink().setOpen(true);
                } else {
                    ObjectFactory.getSimulation().getSurgQ().get(surgeon.getid()).getLink().setOpen(false);
                    
                    String operation = ObjectFactory.getSurgeonSchedule().get(surgeon.getid()).get(ObjectFactory.currentDay);
                    int operationRoom = Integer.parseInt(operation.split(",")[1]);
                    ObjectFactory.getLimit().put(surgeon.getid(), Configuration.operationPerSurgeon);

                    ObjectFactory.getSimulation().getOperationRooms().get(operationRoom).setCurrentSugonId(surgeon.getid());
                    ObjectFactory.getSimulation().getOperationQ().get(surgeon.getid()).getLinks().get(operationRoom).setOpen(true);
                }
            }

            /* Enabling the surgeons to operate on their operating day */
            firstDay = true;
        }

        /* For rest of the days */
        if (isDayChange() && ObjectFactory.currentDay <= ObjectFactory.getSimulationLenth() / 600) {
            ObjectFactory.currentDay++;
            for (Map.Entry<Integer, OperationQueue> entry : ObjectFactory.getSimulation().getOperationQ().entrySet()) {
                OperationQueue operationQueue = entry.getValue();                
                for (Map.Entry<Integer, Link> entry1 : operationQueue.getLinks().entrySet()) {                    
                    Link link = entry1.getValue();
                    link.setOpen(false);                    
                }                
            }
            for (Map.Entry<Integer, Surgeon> entry : ObjectFactory.getSimulation().getSurg().entrySet()) {
                Surgeon surgeon = entry.getValue();
                if (ObjectFactory.getSurgeonSchedule().get(surgeon.getid()).get(ObjectFactory.currentDay) != null) {
                    if (ObjectFactory.getSurgeonSchedule().get(surgeon.getid()).get(ObjectFactory.currentDay).equals("CON")) {
                        ObjectFactory.getSimulation().getSurgQ().get(surgeon.getid()).getLink().setOpen(true);
                    } else {
                        ObjectFactory.getSimulation().getSurgQ().get(surgeon.getid()).getLink().setOpen(false);
                        String operation = ObjectFactory.getSurgeonSchedule().get(surgeon.getid()).get(ObjectFactory.currentDay);
                        int operationRoom = Integer.parseInt(operation.split(",")[1]);
                        ObjectFactory.getLimit().put(surgeon.getid(), Configuration.operationPerSurgeon);
                        
                        
                        ObjectFactory.getSimulation().getOperationRooms().get(operationRoom).setCurrentSugonId(surgeon.getid());
                        ObjectFactory.getSimulation().getOperationQ().get(surgeon.getid()).getLinks().get(operationRoom).setOpen(true);
                    }
                }
            }

            setDayChange(false);
        }
    }

    private void global() {
        /* For day 1 */
        if (ObjectFactory.currentDay == 1 && !firstDay) {

            for (Map.Entry<Integer, Link> entry1 : ObjectFactory.getSimulation().getOperationQ().get(1).getLinks().entrySet()) {
                Link link = entry1.getValue();
                link.setOpen(false);
            }

            /* Enabling the surgeons to see patients on their consulting days */
            for (Map.Entry<Integer, Surgeon> entry : ObjectFactory.getSimulation().getSurg().entrySet()) {
                Surgeon surgeon = entry.getValue();
                if (ObjectFactory.getSurgeonSchedule().get(surgeon.getid()).get(ObjectFactory.currentDay).equals("CON")) {
                    ObjectFactory.getSimulation().getSurgQ().get(surgeon.getid()).getLink().setOpen(true);
                } else {
                    ObjectFactory.getSimulation().getSurgQ().get(surgeon.getid()).getLink().setOpen(false);
                    
                    String operation = ObjectFactory.getSurgeonSchedule().get(surgeon.getid()).get(ObjectFactory.currentDay);
                    int operationRoom = Integer.parseInt(operation.split(",")[1]);
                    ObjectFactory.getLimit().put(surgeon.getid(), Configuration.operationPerSurgeon);
                    //System.out.println("Surgeon is operating on day "+ObjectFactory.currentDay+" in OR "+ operationRoom);

                    ObjectFactory.getSimulation().getOperationRooms().get(operationRoom).setCurrentSugonId(surgeon.getid());
                    ObjectFactory.getSimulation().getOperationQ().get(1).getLinks().get(operationRoom).setOpen(true);
                }
            }

            /* Enabling the surgeons to operate on their operating day */
            firstDay = true;
        }

        /* For rest of the days */
        if (isDayChange() && ObjectFactory.currentDay <= ObjectFactory.getSimulationLenth() / 600) {
            ObjectFactory.currentDay++;
            for (Map.Entry<Integer, Link> entry1 : ObjectFactory.getSimulation().getOperationQ().get(1).getLinks().entrySet()) {
                Link link = entry1.getValue();
                link.setOpen(false);
            }

            for (Map.Entry<Integer, Surgeon> entry : ObjectFactory.getSimulation().getSurg().entrySet()) {
                Surgeon surgeon = entry.getValue();
                if (ObjectFactory.getSurgeonSchedule().get(surgeon.getid()).get(ObjectFactory.currentDay) != null) {
                    if (ObjectFactory.getSurgeonSchedule().get(surgeon.getid()).get(ObjectFactory.currentDay).equals("CON")) {
                        ObjectFactory.getSimulation().getSurgQ().get(surgeon.getid()).getLink().setOpen(true);
                    } else {
                        ObjectFactory.getSimulation().getSurgQ().get(surgeon.getid()).getLink().setOpen(false);
                        String operation = ObjectFactory.getSurgeonSchedule().get(surgeon.getid()).get(ObjectFactory.currentDay);
                        int operationRoom = Integer.parseInt(operation.split(",")[1]);
                        ObjectFactory.getLimit().put(surgeon.getid(), Configuration.operationPerSurgeon);
                        
                        //System.out.println("Surgeon "+surgeon.getid()+" is operating on day "+ObjectFactory.currentDay+" in OR "+ operationRoom);
                        ObjectFactory.getSimulation().getOperationRooms().get(operationRoom).setCurrentSugonId(surgeon.getid());

                        ObjectFactory.getSimulation().getOperationQ().get(1).getLinks().get(operationRoom).setOpen(true);
                    }
                }
            }

            setDayChange(false);
        }
    }

    @Override
    protected void onReceive(Patient p) {
    }

    public void sendPaitent(Patient p) {
        process2(returnLink(), p.getDelay(), p);
    }
}