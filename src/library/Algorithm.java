/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library;

import Simulator.Patient;
import Simulator.ObjectFactory;

/**
 *
 * @author IDontKnow
 */
public abstract class Algorithm extends Entity {

    public String name;

    public Algorithm(String name) {
        super(name);
        this.name = name;
    }

    public abstract void init();
    protected abstract void onReceive(Patient p);

    @Override
    public void body() {
        init();
        checkmessage();
    }

    public synchronized void checkmessage() {
        if (checkMessages()) {
            Patient messageR = getMessage();
            if (messageR != null) {
                onReceive(messageR);
            }
        }
    }

    private synchronized boolean checkMessages() {
        return sim_waiting() > 0;
    }  

    public Patient getMessage() {
        Event ev = new Event();

        while (ObjectFactory.getSimulationLenth() > Core.clock()) {
            sim_get_next(ev);

            Patient message = (Patient) ev.get_data();

            if (message != null) {
                return message;
            }

        }
        return null;
    }

}
