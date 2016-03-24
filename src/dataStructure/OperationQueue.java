/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataStructure;

import Simulator.Patient;
import java.util.LinkedHashMap;
import library.Link;
import library.Algorithm;
import library.Core;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/**
 *
 * @author behnish
 */
public class OperationQueue extends Algorithm {

    Queue<Patient> queue = new LinkedList<>();

    Map<Integer, Link> links = new LinkedHashMap<>();

    private int id;
    private String operationQName;

    public int getid() {
        return id;
    }

    public Map<Integer, Link> getLinks() {
        return links;
    }

    
    public void setid(int id) {
        this.id = id;
    }

    public OperationQueue(String name) {
        super(name);
        this.operationQName = name;
    }

    public void addLink(int number, Link l) {
        links.put(number, l);
        add_port(l);
    }

    public String getOperationQName() {
        return operationQName;
    }

    public void setOperationQName(String operationQName) {
        this.operationQName = operationQName;
    }

    @Override
    public void init() {        
        Link send = returnLink();
        if (send != null && send.isOpen()) {
            if (!queue.isEmpty()) {
                sim_trace(1, "<ORQ_PRO," + getid() + "," + queue.peek().getSeqenceNumber() + "," + Core.clock() + ">");
                process(send, 0, queue.remove());
                send.setOpen(false);
            }
        }
    }

    @Override
    protected void onReceive(Patient m) {
        queue.add(m);
        m.setOperationQueueEntryTime(Core.clock());
        sim_trace(1, "<OPT_SIZE," + getid()+","+queue.size()+","+Core.clock()+">");
        sim_trace(1, "<ORQ_REC," + getid() + "," + m.getSeqenceNumber() + "," + Core.clock() + ">");
    }

    private Link returnLink() {
        for (Map.Entry<Integer, Link> entry : links.entrySet()) {
            Link link = entry.getValue();
            if (link.isOpen()) {
                return link;
            }
        }
        return null;
    }
}
