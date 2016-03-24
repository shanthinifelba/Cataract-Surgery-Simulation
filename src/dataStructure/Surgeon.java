/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataStructure;

import Simulator.Configuration;
import Simulator.Utilities;
import Simulator.Patient;
import Simulator.ObjectFactory;
import java.util.LinkedHashMap;
import library.Link;
import library.Algorithm;
import library.Core;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import unbc.ca.distributed.distributions.Generator;

/**
 *
 * @author behnish sim_get_next
 */
public class Surgeon extends Algorithm {

    private final String surgeonName;
    private int id;

    Map<Integer, Link> links = new LinkedHashMap<>();
    private Link operationQueueLink;

    //int consultingTime = 1200;

    Generator uniform = null;
    Generator poisson = null;

    Queue<Patient> pQueue = new LinkedList<>();
    //Patient p;

    int patientLimit = Configuration.totalNoOfPatientSurgeonSees;

    public void setPatientLimit(int patientLimit) {
        this.patientLimit = patientLimit;
    }

    public Link getOperationQueueLink() {
        return operationQueueLink;
    }

    public void setOperationQueueLink(Link queueLink) {
        this.operationQueueLink = queueLink;
        addPort(queueLink);
    }

    public int getid() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Surgeon(String name) {
        super(name);
        this.surgeonName = name;
    }

    public String getSurgeonName() {
        return surgeonName;
    }

    public void addPort(Link l) {
        add_port(l);
    }

    public void addLink(int index ,Link l) {
        links.put(index, l);
        add_port(l);        
    }

    public int delay() {
        if (poisson == null) {
            poisson = Utilities.returnDistribution("Poisson", 5, 1);
        }
        return (int) poisson.generate();
    }

    @Override
    public void init() {
        if (isFlag() && !pQueue.isEmpty()) {
            Patient p = pQueue.remove();
            sim_trace(1, "<S_PRO," + getid() + "," + p.getSeqenceNumber() + "," + Core.clock() + ">");
            
            process(operationQueueLink, Configuration.decisionTime, p);

            patientLimit--;
            if (pQueue.isEmpty()) {
                if (patientLimit > 0) {
                    ObjectFactory.getSimulation().getSurgQ().get(id).getLink().setOpen(true);
                }
            } else {                
                enterSurgonFlag(35);
                setFlag(false);
            }
        }
    }

    @Override
    protected void onReceive(Patient m) {
        if (m != null) {
            sim_trace(1, "<S_REC," + getid() + "," + m.getSeqenceNumber() + "," + Core.clock() + ">");
            pQueue.add(m);
            m.setWaitTime1(Core.clock() - m.getStartTime());
            m.setStartTime(Core.clock());
            
            enterSurgonFlag(35);
            setFlag(false);
        }
    }
}
