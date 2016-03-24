/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Simulator;

import library.Link;
import dataStructure.OperationQueue;
import dataStructure.OperationRoom;
import dataStructure.RecoveryZone;
import dataStructure.Surgeon;
import dataStructure.SurgeonQueue;
import library.Core;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author behnish
 */
public class Simulation extends Thread {

    Map<Integer, Surgeon> surg = new LinkedHashMap<>();
    Map<Integer, SurgeonQueue> surgQ = new LinkedHashMap<>();
    Map<Integer, OperationRoom> operationRooms = new LinkedHashMap<>();
    Map<Integer, OperationQueue> operationQ = new LinkedHashMap<>();
    Map<Integer, RecoveryZone> recoveryRooms = new LinkedHashMap<>();

    int ors, sur, rate, backlogpaitent;

    String algorithm;

    Simulation(int ors, int sur, int backlogpaitent, String type) {

        this.ors = ors;
        this.sur = sur;
        this.backlogpaitent = backlogpaitent;
        this.algorithm = type;
    }

    @Override
    public void run() {        
        Core.initialise();
        switch (algorithm) {
            case "Global Queue for all Surgeons":
                Configuration.global = true;
                globalQueue();
                break;
            case "Local Queue for Each Surgeon":
                Configuration.global = false;
                singleQueue();                
                break;
        }
        updateClockView();
        Core.run();
    }

    private void globalQueue() {
        for (int i = 1; i <= ObjectFactory.getSimulationLenth() / 600; i++) {
            Core.dayChange(0, 600 * i);
        }

        PatientGenerator g = new PatientGenerator("PG");

        /* Put paitent patient generator to surgons */
        for (int i = 1; i <= sur; i++) {
            SurgeonQueue s = new SurgeonQueue("SurQ" + i);
            s.setid(i);
            surgQ.put(i, s);

            Link surgonLink = new Link("SurQL" + i);
            s.add_port(surgonLink);

            Link pgLink = new Link("pgLink" + i);
            g.addLink(pgLink);

            Core.link_ports("PG", "pgLink" + i, "SurQ" + i, "SurQL" + i);

        }

        for (Map.Entry<Integer, LinkedHashMap<Integer, Patient>> entry : ObjectFactory.getPatientG().entrySet()) {
            LinkedHashMap<Integer, Patient> concurrentHashMap = entry.getValue();

            for (Map.Entry<Integer, Patient> entry1 : concurrentHashMap.entrySet()) {
                Patient patient = entry1.getValue();
                g.sendPaitent(patient);
            }
        }

        // Connecting surgons with their queue
        for (int i = 1; i <= sur; i++) {
            Surgeon s = new Surgeon("Sur" + i);
            s.setId(i);
            surg.put(i, s);

            // linking Surgons and Surgons queue
            Link surgonLink = new Link("SurL" + i);
            s.add_port(surgonLink);

            Link docL = new Link("surQL" + i);
            surgQ.get(i).setLink(docL);
            Core.link_ports(surgQ.get(i).getSurgeonQName(), "surQL" + i, "Sur" + i, "SurL" + i);
        }

        // connecting operation queue with each surgon
        OperationQueue or1 = new OperationQueue("ORQ1");
        or1.setid(1);
        operationQ.put(1, or1);

        for (int j = 1; j <= sur; j++) {
            Link surL = new Link("SurL" + j);
            surg.get(j).setOperationQueueLink(surL);

            // linking Surgons's patients to Operating Room 
            Link ORLink = new Link("OrQLink1-" + j);
            or1.add_port(ORLink);

            Core.link_ports(surg.get(j).getSurgeonName(), "SurL" + j, "ORQ1", "OrQLink1-" + j);
        }

        // connecting operation room with their queue
        for (int i = 1; i <= ors; i++) {

            OperationRoom or = new OperationRoom("OR" + i);
            or.setId(i);
            operationRooms.put(i, or);

            Link surL = new Link("ORQL1" + i);
            operationQ.get(1).addLink(i, surL);

            // linking Surgons's patients to Operating Room 
            Link ORLink = new Link("OrLink" + i);
            or.add_port(ORLink);

            Core.link_ports(operationQ.get(1).getOperationQName(), "ORQL1" + i, "OR" + i, "OrLink" + i);

        }

        RecoveryZone rz = new RecoveryZone("RZ1");
        rz.setId(1);
        recoveryRooms.put(1, rz);

        for (int j = 1; j <= ors; j++) {
            Link surL = new Link("OrLink" + j);
            operationRooms.get(j).addLink(surL);

            // linking Surgons's patients to Operating Room 
            Link ORLink = new Link("RZLink1-" + j);
            rz.add_port(ORLink);

            Core.link_ports(operationRooms.get(j).getOperationRoomName(), "OrLink" + j, "RZ1", "RZLink1-" + j);
        }

    }

    private void singleQueue() {

        for (int i = 1; i <= ObjectFactory.getSimulationLenth() / 600; i++) {
            Core.dayChange(0, 600 * i);
        }

        PatientGenerator g = new PatientGenerator("PG");

        /* Put paitent into event queue */
        for (int i = 1; i <= sur; i++) {
            SurgeonQueue s = new SurgeonQueue("SurQ" + i);
            s.setid(i);
            surgQ.put(i, s);

            Link surgonLink = new Link("SurQL" + i);
            s.add_port(surgonLink);

            Link pgLink = new Link("pgLink" + i);
            g.addLink(pgLink);

            Core.link_ports("PG", "pgLink" + i, "SurQ" + i, "SurQL" + i);

        }

        for (Map.Entry<Integer, LinkedHashMap<Integer, Patient>> entry : ObjectFactory.getPatientG().entrySet()) {
            LinkedHashMap<Integer, Patient> concurrentHashMap = entry.getValue();

            for (Map.Entry<Integer, Patient> entry1 : concurrentHashMap.entrySet()) {
                Patient patient = entry1.getValue();
                g.sendPaitent(patient);
            }

        }

        // Connecting surgons with their queue
        for (int i = 1; i <= sur; i++) {
            Surgeon s = new Surgeon("Sur" + i);
            s.setId(i);
            surg.put(i, s);

            // linking Surgons and Surgons queue
            Link surgonLink = new Link("SurL" + i);
            s.setOperationQueueLink(surgonLink);
            s.add_port(surgonLink);

            Link docL = new Link("surQL" + i);
            surgQ.get(i).setLink(docL);
            Core.link_ports(surgQ.get(i).getSurgeonQName(), "surQL" + i, "Sur" + i, "SurL" + i);
        }

        // connecting operation room with each surgon
        for (int i = 1; i <= sur; i++) {

            OperationQueue or = new OperationQueue("ORQ" + i);
            or.setid(i);
            operationQ.put(i, or);

            Link surL = new Link("SurL" + i);
            surg.get(i).setOperationQueueLink(surL);

            // linking Surgons's patients to Operating Room 
            Link ORLink = new Link("OrQLink" + i);
            or.add_port(ORLink);

            Core.link_ports(surg.get(i).getSurgeonName(), "SurL" + i, "ORQ" + i, "OrQLink" + i);
        }

        // connecting operation room with their queue
        for (int i = 1; i <= ors; i++) {

            OperationRoom or = new OperationRoom("OR" + i);
            or.setId(i);
            operationRooms.put(i, or);

            for (int j = 1; j <= sur; j++) {
                Link surL = new Link("ORQL" + j);
                operationQ.get(j).addLink(i, surL);

                // linking Surgons's patients to Operating Room 
                Link ORLink = new Link("OrLink" + i + "-" + j);
                or.add_port(ORLink);

                Core.link_ports(operationQ.get(j).getOperationQName(), "ORQL" + j, "OR" + i, "OrLink" + i + "-" + j);
            }
        }

        RecoveryZone rz = new RecoveryZone("RZ1");
        rz.setId(1);
        recoveryRooms.put(1, rz);

        for (int j = 1; j <= ors; j++) {
            Link surL = new Link("OrLink" + j);
            operationRooms.get(j).addLink(surL);

            // linking Surgons's patients to Operating Room 
            Link ORLink = new Link("RZLink1" + "-" + j);
            rz.add_port(ORLink);

            Core.link_ports(operationRooms.get(j).getOperationRoomName(), "OrLink" + j, "RZ1", "RZLink1-" + j);

        }
    }
    
    private void addBackLog()
    {
        
    }

    public Map<Integer, Surgeon> getSurg() {
        return surg;
    }

    public Map<Integer, SurgeonQueue> getSurgQ() {
        return surgQ;
    }

    public Map<Integer, OperationRoom> getOperationRooms() {
        return operationRooms;
    }

    public Map<Integer, OperationQueue> getOperationQ() {
        return operationQ;
    }

    public Map<Integer, RecoveryZone> getRecoveryRooms() {
        return recoveryRooms;
    }

    public void updateClockView() {
        new Thread() {
            @Override
            public void run() {
                while (ObjectFactory.getSimulationLenth() > Core.clock()) {
                    ObjectFactory.getParmeters().clockValue.setText(String.valueOf(Core.clock()));
                }
            }
        }.start();
    }
}
