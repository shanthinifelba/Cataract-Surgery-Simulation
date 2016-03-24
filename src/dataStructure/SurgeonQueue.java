/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataStructure;

import Simulator.Patient;
import library.Link;
import library.Algorithm;
import library.Core;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author behnish
 */
public class SurgeonQueue extends Algorithm {

    Queue<Patient> queue = new LinkedList<>();
    private Link link;
    private int id;
    private String surgonQName;    

    public SurgeonQueue(String name) {
        super(name);
        this.surgonQName = name;
    }

    public String getSurgeonQName() {
        return surgonQName;
    }

    public void setSurgeonQName(String surgonQName) {
        this.surgonQName = surgonQName;
    }

    public int getid() {
        return id;
    }

    public void setid(int id) {
        this.id = id;
    }

    public void setLink(Link l) {
        link = l;
        add_port(l);
    }

    public Link getLink() {
        return link;
    }

    @Override
    public void init() {
        if (!queue.isEmpty()) {
            if (link.isOpen()) {
                link.setOpen(false);
                Patient tp = queue.remove();
                sim_trace(1, "<Q_PRO," + getid() + "," + tp.getSeqenceNumber() + "," + Core.clock() + ">");
                process(link, 1, tp);
            }
        }
    }

    @Override
    protected void onReceive(Patient m) {
        if (m != null && m.getSeqenceNumber() != -1) {
            m.setSurgonId(id);
            queue.add(m);
            sim_trace(1, "<SQ_SIZE," + getid()+","+queue.size()+","+Core.clock()+">");
            sim_trace(1, "<Q_REC," + getid() + "," + m.getSeqenceNumber() + "," + Core.clock() + ">");
        }
    }
}
