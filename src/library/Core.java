/**
 * The simjava package is a support library for discrete event simulations.
 *
 * @version 1.2, 4 August 1997
 * @author Ross McNab, Fred Howell
 */
package library;

import Simulator.ObjectFactory;
import java.text.NumberFormat;
import java.util.Vector;
import java.util.Iterator;
import java.util.List;

/**
 * This is the system class which manages the simulation. All of the members of
 * this class are static, so there is no need to create an instance of this
 * class.
 *
 * @version 1.0, 4 September 1996
 * @version 1.2, 14 July 1997
 * @author Ross McNab, Fred Howell
 */
public class Core {
    // Private data members

    static private Vector entities;  // The current entity list
    static private Evqueue future;   // The future event queue
    static private Evqueues deferredQueues; // The deferred event queue
    // a HashMap from destinations (int) to Evqueues
    static private double clock;     // Holds the current global sim time
    static private boolean running;  // Has the run() member been called yet
    static private Semaphore onestopped;
    static private Sim_output trcout; // The output object for trace messages
    static private int trc_level = 0xff;
    static private boolean auto_trace = false; // Should we print auto trace messages?
    static private NumberFormat nf;

//    private static boolean include_seeds = false; // The default is to produce a report that does not include seed information
//    private static List initial_seeds; // The set of initial seeds
//    // Private fields used for seed generation
//    private static long root_seed = 4852311L; // The default seed
//    private static int seed_spacing = 100000; // The default seed spacing
//    private static RandomP seed_source = new RandomP("Seed generator", root_seed); // The seed generator
    private static boolean not_sampled = true; // Flag that is checked to see whether the seed generator has been sampled

    public static Evqueue getFuture() {
        return future;
    }
   
    /* Events by simulation */
    public static synchronized void surgonFlag(int src, double delay) {
        Event e = new Event(Event.SURGONFLAG, clock + delay, src);
        future.add(e);
    }
  public static synchronized void stateFlag(int src, double delay) {
        Event e = new Event(Event.STATEFLAG, clock + delay, src);
        future.add(e);
    }
    public static synchronized void dontAllow(int src, double delay) {
        Event e = new Event(Event.PG, clock + delay, src);
        future.add(e);
    }
    
    public static synchronized void dayChange(int src, double delay) {
        Event e = new Event(Event.DAYCHANGE, delay, src);
        future.add(e);
    }
    // Initialise system
    /**
     * Initialise the system, this function does the job of a constructor, and
     * should be called at the start of any simulation program. It comes in
     * several flavours depending on what context the simulation is running.<P>
     * This is the simplest, and should be used for standalone simulations It
     * sets up trace output to a file in the current directory called
     * `tracefile'
     */
    static public void initialise() {
        initialise(new Sim_outfile(), null);
    }

    /**
     * This version of initialise() is for experienced users who want to use the
     * trace output to draw a graph or an animation as part of the application.
     *
     * @param out The object to be used for trace output
     * @param sim The thread the simulation is running under, (set to
     * <tt>null</tt> if not applicable
     */
    static public void initialise(Sim_output out, Thread sim) {
        if (trc_level > 0) {
//            System.out.println("SimJava V1.2 - Ross McNab and Fred Howell");
        }
        entities = new Vector();
        future = new Evqueue();
        deferredQueues = new Evqueues();
        clock = 1.0;
        running = false;
        onestopped = new Semaphore(0);
        trcout = out;
        trcout.initialise();

        // Set the default number format
        nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(4);
        nf.setMinimumFractionDigits(2);

    }    

    public static void resetAll() {
        running = true; // Flag for telling whether a simulation is complete or not
        entities.clear();                
        clock = 1.0;
        running = false;
        onestopped = new Semaphore(0);
        trcout.initialise();
        nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(4);
        nf.setMinimumFractionDigits(2);

    }

    /**
     * Returns the number format used for generating times in trace lines
     */
    static public NumberFormat getNumberFormat() {
        return nf;
    }
    // The two standard predicates
    /**
     * A standard predicate that matches any event.
     */
    static public Sim_any_p SIM_ANY = new Sim_any_p();
    /**
     * A standard predicate that does not match any events.
     */
    static public Sim_none_p SIM_NONE = new Sim_none_p();

    // Public access methods
    /**
     * Get the current simulation time.
     *
     * @return The simulation time
     */
    static public double clock() {
        return clock;
    }

    /**
     * A different name for <tt>Sim_system.clock()</tt>.
     *
     * @return The current simulation time
     */
    static public double sim_clock() {
        return clock;
    }

    /**
     * Get the current number of entities in the simulation
     *
     * @return A count of entities
     */
    static public int get_num_entities() {
        return entities.size();
    }

    /**
     * Get the current trace level (initially <tt>0xff</tt>), which controls
     * trace output.
     *
     * @return The trace level flags
     */
    static public int get_trc_level() {
        return trc_level;
    }

    /**
     * Find an entity by its id number.
     *
     * @param id The entity's unique id number
     * @return A reference to the entity, or null if it could not be found
     */
    static public Entity get_entity(int id) {
        return (Entity) entities.elementAt(id);
    }

    /**
     * Find an entity by its name.
     *
     * @param name The entity's name
     * @return A reference to the entity, or null if it could not be found
     */
    static public Entity get_entity(String name) {
        Iterator e;
        Entity ent, found = null;

        for (e = entities.iterator(); e.hasNext();) {
            ent = (Entity) e.next();
            if (name.compareTo(ent.get_name()) == 0) {
                found = ent;
            }
        }
        if (found == null) {
            System.out.println("Sim_system: could not find entity " + name);
        }
        return found;
    }

    /**
     * Find out an entities unique id number from its name.
     *
     * @param name The entity's name
     * @return The entity's unique id number, or -1 if it could not be found
     */
    static public int get_entity_id(String name) {
        return entities.indexOf(get_entity(name));
    }

    // Public update methods
    /**
     * Set the trace level flags which control trace output.
     *
     * @param level The new flags
     */
    static public void set_trc_level(int level) {
        trc_level = level;
    }

    /**
     * Switch the auto trace messages on and off.
     *
     * @param on If <tt>true</tt> then the messages are switched on
     */
    static public void set_auto_trace(boolean on) {
        auto_trace = on;
    }

    /**
     * Add a new entity to the simulation. This is now done automatically in the
     * Sim_entity constructor, so there is no need to call this explicitly.
     *
     * @param e A reference to the new entity
     */
    static public void add(Entity e) {
        Event evt;

        if (e.get_id() == -1) { // Only add once!
            e.set_id(entities.size());
            entities.addElement(e);
        }

    }

    /**
     * Link the ports the ports on two entities, so that events secheduled from
     * one port are sent to the other.
     *
     * @param ent1 The name of the first entity
     * @param port1 The name of the port on the first entity
     * @param ent2 The name of the second entity
     * @param port2 The name of the port on the second entity
     */
    static public void link_ports(String ent1, String port1, String ent2, String port2) {
        Port p1, p2;
        Entity e1, e2;
        e1 = get_entity(ent1);
        e2 = get_entity(ent2);
        if (e1 == null) {
            System.out.println("Sim_system: " + ent1 + " not found");
        } else if (e2 == null) {
            System.out.println("Sim_system: " + ent2 + " not found");
        } else {
            p1 = e1.get_port(port1);
            p2 = e2.get_port(port2);
            if (p1 == null) {
                System.out.println("Sim_system: " + port1 + " not found");
            } else if (p2 == null) {
                System.out.println("Sim_system: " + port1 + " not found");
            } else {
                p1.connect(e2);
                p2.connect(e1);

            }
        }
    }

    /**
     * Start the simulation running. This should be called after all the
     * entities have been setup and added, and their ports linked Phase 1.
     */
    static public void run_start() {
        running = true;
        // Start all the entities' threads
        int entities_size = entities.size();
        for (int i = 0; i < entities_size; i++) {
            ((Entity) entities.get(i)).run();
        }

    }

    /**
     * Run one tick of the simulation.
     *
     * @return
     */
    static public boolean run_tick() {
        running = true;

        for (Iterator e = entities.iterator(); e.hasNext();) {
            Entity ent = (Entity) e.next();
            ent.body();
        }

        // If there are more future events then deal with them
        //System.out.println("Sim_system: Looking for future event");
        if (future.size() > 0) {
            Event first = future.pop();
            process_event(first);

            // Check if next events are at same time...
            boolean trymore = (future.size() > 0);
            while (trymore) {
                Event next = future.top();
                if (next.event_time() == first.event_time()) {
                    process_event(future.pop());
                    trymore = (future.size() > 0);
                } else {
                    trymore = false;
                }
            }
        } else {
            if (ObjectFactory.getSimulationLenth() > clock) {
                running = true;
            } else {
                running = false;
                if (trc_level > 0) {
                    System.out.println("Sim_system: No more future events");
                }
            }

        }
        return running;
    }

    /**
     * Stop the simulation
     */
    static public void run_stop() {
        Iterator e;
        Entity ent;
        // Attempt to kiiiillll all the entity threads
        for (e = entities.iterator(); e.hasNext();) {
            ent = (Entity) e.next();
            ent.poison();
        }
        if (trc_level > 0) {

            System.out.println("Exiting Sim_system.run() ->" + clock);
        }
        trcout.close();
    }

    /**
     * Start the simulation running. This should be called after all the
     * entities have been setup and added, and their ports linked
     */
    static public void run() {

        run_start();

        while (run_tick()) {
        }
        run_stop();
    }

    //
    // Package level methods
    //
    static boolean running() {
        return running;
    }

    static public Sim_output get_trcout() {
        return trcout;
    }

    // Entity service methods
    // Called by an entity just before it become non-RUNNABLE
    static void paused() {
        onestopped.v();
    }

    static synchronized void send(int src, int dest, double delay, int tag, Object data) {
        Event e = new Event(Event.SEND, clock + delay, src, dest, tag, data);
        if (auto_trace) {
            trace(src, "scheduling event type " + tag + " for "
                    + ((Entity) entities.elementAt(dest)).get_name()
                    + " with delay " + delay);
        }
        future.add(e);
    }
    
    static synchronized void insert(int src, int dest, double delay, int tag, Object data) {
        Event e = new Event(Event.SEND, delay, src, dest, tag, data);
        if (auto_trace) {
            trace(src, "scheduling event type " + tag + " for "
                    + ((Entity) entities.elementAt(dest)).get_name()
                    + " with delay " + delay);
        }
        future.add(e);
    }

    static synchronized void trace(int src, String msg) {
        trcout.println(msg);
    }

    static synchronized int waiting(int d, Sim_predicate p) {
        int w = 0;
        Iterator e;
        Event ev;

        Evqueue deferred = deferredQueues.get(d);
        if (p instanceof Sim_any_p) {
            return deferred.size();
        }

        for (e = deferred.iterator(); e.hasNext();) {
            ev = (Event) e.next();
            if (ev.get_dest() != d) {
                throw new Error("inconsistency in deferredQueues!");
            }

            if (p.match(ev)) {
                w++;
            }
        }
        return w;
    }

    static synchronized void select(int src, Sim_predicate p) {
        Iterator e;
        Event ev = null;
        boolean found = false;

        Evqueue deferred = deferredQueues.get(src);

        // retrieve + remove event with dest == src
        for (e = deferred.iterator(); (e.hasNext()) && !found;) {
            ev = (Event) e.next();
            if (ev.get_dest() == src) {
                if (p.match(ev)) {
                    e.remove();
                    found = true;
                }
            }
        }

        deferredQueues.check(src);

        if (found) {
            ((Entity) entities.elementAt(src)).set_evbuf((Event) ev.clone());
            if (auto_trace) {
                trace(src, "sim_select returning (event time was " + ev.event_time() + ")");
            }
        } else {
            ((Entity) entities.elementAt(src)).set_evbuf(null);
            if (auto_trace) {
                trace(src, "sim_select returning (no event found)");
            }
        }
    }

    static synchronized void cancel(int src, Sim_predicate p) {
        Iterator e;
        Event ev = null;
        boolean found = false;

        // retrieve + remove event with dest == src
        for (e = future.iterator(); (e.hasNext()) && !found;) {
            ev = (Event) e.next();
            if (ev.get_src() == src) {
                if (p.match(ev)) {
                    e.remove();
                    found = true;
                }
            }
        }
        if (found) {
            ((Entity) entities.elementAt(src)).set_evbuf((Event) ev.clone());
            if (auto_trace) {
                trace(src, "sim_cancel returning (event time was " + ev.event_time() + ")");
            }
        } else {
            ((Entity) entities.elementAt(src)).set_evbuf(null);
            if (auto_trace) {
                trace(src, "sim_cancel returning (no event found)");
            }
        }
    }

    static synchronized void putback(Event ev) {
        Evqueue deferred = deferredQueues.create(ev.get_dest());
        deferred.add(ev);
    }

    //
    // Private internal methods
    //
    static private void process_event(Event e) {
        int dest, src;
        Entity dest_ent;

        //System.out.println("Sim_system: Processing event");
        // Update the system's clock
        if (e.event_time() < clock) {
            System.out.println("Sim_system: Error - past event detected!");
            System.out.println("Time: " + clock + ", event time: " + e.event_time()
                    + ", event type: " + e.get_type());
        }
        clock = e.event_time();

        // Ok now process it
        switch (e.get_type()) {
            case (Event.ENULL):
                System.out.println("Sim_system: Error - event has a null type");
                break;
            case (Event.SEND):
                // Check for matching wait
                dest = e.get_dest();
                if (dest < 0) {
                    System.out.println("Sim_system: Error - attempt to send to a null entity");
                } else {
                    dest_ent = (Entity) entities.elementAt(dest);
                    if (dest_ent.get_state() == Entity.WAITING) {
                        dest_ent.set_evbuf((Event) e.clone());
                        dest_ent.set_state(Entity.RUNNABLE);
                    } else {
                        Evqueue deferred = deferredQueues.create(e.get_dest());
                        deferred.add(e);
                    }
                }
                break;
            case (Event.SURGONFLAG):
                src = e.get_src();
                if (src < 0) {
                    throw new Error("Sim_system: Null entity holding.");
                } else {
                    ((Entity) entities.get(src)).setFlag(true);
                }
                break;
            case (Event.STATEFLAG):
                src = e.get_src();
                if (src < 0) {
                    throw new Error("Sim_system: Null entity holding.");
                } else {
                    ((Entity) entities.get(src)).setStateFlag(true);
                }
                break;
            case (Event.DAYCHANGE):
                src = e.get_src();
                if (src < 0) {
                    throw new Error("Sim_system: Null entity holding.");
                } else {
                    ((Entity) entities.get(src)).setDayChange(true);
                }
                break;
                
            case (Event.PG):
                src = e.get_src();
                if (src < 0) {
                    throw new Error("Sim_system: Null entity holding.");
                } else {
                    ((Entity) entities.elementAt(src)).setPG(true);
                }
                break;
            case (Event.HOLD_DONE):
                src = e.get_src();
                if (src < 0) {
                    System.out.println("Sim_system: Error - NULL entity holding");
                } else {
                    ((Entity) entities.elementAt(src)).set_state(Entity.RUNNABLE);
                }
                break;
        }
    }
}
