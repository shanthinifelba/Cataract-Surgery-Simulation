/* Evqueue.java
 */

package library;

import java.util.HashMap;

/**
 * This class implements an event queue used internally by the Sim_system to
 * manage
 * the list of future and deferred Sim_events. It should not be needed in
 * a user simulation. It works like a normal FIFO
 * queue, but during insertion events are kept in order from the smallest time
 * stamp to the largest. This means the next event to occur will be at the top
 * of the queue. <P>
 * The current implementation
 * is uses a Vector to store the queue and is inefficient for popping
 * and inserting elements because the rest of the array has to be
 * moved down one space. A better method would be to use a circular array.
 * @see		eduni.simjava.Sim_system
 * @version     0.1, 25 June 1995
 * @author      Ross McNab
 */

public class Evqueues {

    private HashMap map = new HashMap();

    private Evqueue empty = new Evqueue();

    public Evqueue get(int dest) {
	
	Integer Dest = new Integer(dest);
	Evqueue r = (Evqueue)map.get(Dest);
	if (r == null) {
	    r = empty;
	}
	return r;

    }

    public void check(int dest) {
	
	Integer Dest = new Integer(dest);
	Evqueue r = (Evqueue)map.get(Dest);
	if (r != null && r.size() == 0) {
	    map.remove(Dest);
	}

    }

    public Evqueue create(int dest) {

	Integer Dest = new Integer(dest);
	Evqueue r = (Evqueue)map.get(Dest);
	if (r == null) {
	    r = new Evqueue();
	    map.put(Dest, r);
	}
	return r;

    }

}
