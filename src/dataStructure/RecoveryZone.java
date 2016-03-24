/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataStructure;

import Simulator.Patient;
import library.Algorithm;
import library.Core;

/**
 *
 * @author behnish
 */
public class RecoveryZone extends Algorithm {

    private int id;
    private int counter = 0;

    public int getid() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RecoveryZone(String name) {
        super(name);
    }

    @Override
    public void init() {
    }

    @Override
    protected void onReceive(Patient m) {
        if (m != null) {
            counter++;
            System.out.println("Recovery Room has received paitent " + m.getSeqenceNumber() + " at clock " + Core.clock()+counter);
            sim_trace(1, "<RZ_ENT," + getid() + "," + m.getSeqenceNumber() + "," + Core.clock() + ","+m.getWaitTime1()+","+m.getWaitTime2()+","+m.getSurgonId()+","+m.getOperationSurgonId()+">");
        }
    }
}
