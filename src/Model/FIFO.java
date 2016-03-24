/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Simulator.Patient;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author behnish
 */
public class FIFO {

    private Queue<Patient> queue = new LinkedList<>();        

    public Queue<Patient> getQueue() {
        return queue;
    }

    public void setQueue(Queue<Patient> queue) {
        this.queue = queue;
    }   
    public Patient getNext()
    {        
        return queue.remove();
    }
    public void addPatient(Patient p)
    {
        queue.add(p);
    }    
    public int size()
    {
        return queue.size();
    }
}
