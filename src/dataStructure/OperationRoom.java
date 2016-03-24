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
import library.Link;
import library.Algorithm;
import library.Core;
import java.util.LinkedHashMap;
import java.util.Map;
import unbc.ca.distributed.distributions.Generator;

/**
 *
 * @author behnish
 */
public class OperationRoom extends Algorithm {

    private final String operationRoomName;
    private int linkCounter = 1;
    private int id;
    private Patient patient = null;
    Map<Integer, Link> links = new LinkedHashMap<>();
    Generator u = null;

    int currentSugonId;

    public void setCurrentSugonId(int currentSugonId) {
        this.currentSugonId = currentSugonId;
    }

    public int getid() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public OperationRoom(String name) {
        super(name);
        this.operationRoomName = name;
    }

    public String getOperationRoomName() {
        return operationRoomName;
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
            return links.get((int) u.generate());
        }
    }

    @Override
    public void init() {
        if (isFlag() && patient != null) {
            patient.setOperationSurgonId(currentSugonId);
            process(returnLink(), 15, patient);
            sim_trace(1, "<OP_END," + getid() + "," + patient.getSeqenceNumber() + "," + Core.clock() + "," + currentSugonId + ">");
            patient = null;
            int l = ObjectFactory.getLimit().get(currentSugonId);
            l--;
            ObjectFactory.getLimit().put(currentSugonId, l);
            if (ObjectFactory.getLimit().get(currentSugonId) > 0) {
                if (Configuration.global) {
                    ObjectFactory.getSimulation().getOperationQ().get(1).getLinks().get(id).setOpen(true);
                } else {
                    ObjectFactory.getSimulation().getOperationQ().get(currentSugonId).getLinks().get(id).setOpen(true);
                }
            }
        }
    }

    @Override
    protected void onReceive(Patient m) {
        m.setWaitTime2(Core.clock()-m.getStartTime());
        sim_trace(1, "<OP_START," + getid() + "," + m.getSeqenceNumber() + "," + Core.clock() + "," + currentSugonId + ">");
        patient = m;
        enterSurgonFlag(25);
        setFlag(false);
    }
}
