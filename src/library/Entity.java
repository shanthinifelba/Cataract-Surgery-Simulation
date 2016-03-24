/* Sim_entity.java
 */
package library;

import java.util.Vector;
import java.util.Enumeration;

/**
 * This class represents entities, or processes, running in the system. To
 * create a new type of entity, it should be <tt>extended</tt> and a definition
 * for the <tt>body()</tt> method given. The <tt>body()</tt>
 * method is called by the <tt>Sim_system</tt> and defines the behaviour of the
 * entity during the simulation.
 * <p>
 * The methods with names starting with the prefix <tt>sim_</tt> are runtime
 * methods, and should only be called from within the entity's
 * <tt>body()</tt> method.
 *
 * @see Core
 * @version 0.1, 25 June 1995
 * @author Ross McNab
 */
public class Entity {

    // Private data members
    private String name;       // The entities name
    private int me;            // Unique id
    private Event evbuf;   // For incoming events
    private int state;         // Our current state from list below
    private Semaphore restart; // Used by Sim_system to schedule us
    private Vector ports;      // Our outgoing ports

    private boolean surgonFlag = true;
    private boolean PG = true; // Allow patient generator to generate another patient    
    private boolean stateFlag = true;
    
    private boolean dayChange = false;

    public boolean isDayChange() {
        return dayChange;
    }

    public void setDayChange(boolean dayChange) {
        this.dayChange = dayChange;
    }        

    public boolean isStateFlag() {
        return stateFlag;
    }

    public void setStateFlag(boolean stateFlag) {
        this.stateFlag = stateFlag;
    }
        
        

    public boolean isPG() {
        return PG;
    }

    public void setPG(boolean PG) {
        this.PG = PG;
    }
    
    public void dontAllow(double duration) {
        if (!Core.running()) {
            return;
        }
        Core.dontAllow(me, duration);
    }
        
    public boolean isFlag() {
        return surgonFlag;
    }

    public void setFlag(boolean surgonFlag) {
        this.surgonFlag = surgonFlag;
    }

    public void enterSurgonFlag(double duration) {
        if (!Core.running()) {
            return;
        }
        Core.surgonFlag(me, duration);
    }
    
    public void enterStateFlag(double duration) {
        if (!Core.running()) {
            return;
        }
        Core.stateFlag(me, duration);
    }

    // Public constructor
    /**
     * The standard constructor.
     *
     * @param name The name to be associated with this entity
     * @return A new instance of the class Sim_entity
     */
    public Entity(String name) {
        this.name = name;
        me = -1;
        state = RUNNABLE;
        restart = new Semaphore(0);
        ports = new Vector();
       
        // Adding this to Sim_system automatically
        Core.add(this);
    }
    


    // Public access methods
    /**
     * Get the name of this entity
     *
     * @return The entity's name
     */
    public String get_name() {
        return name;
    }

    /**
     * Get the unique id number assigned to this entity
     *
     * @return The id number
     */
    public int get_id() {
        return me;
    }
    // Search for the port that the event came from
    /**
     * Search through this entity's ports, for the one which sent this event.
     *
     * @param ev The event
     * @return A reference to the port which sent the event, or null if it could
     * not be found
     */
    public Port get_port(Event ev) {

        Port found = null, curr;
        Enumeration e;
        for (e = ports.elements(); e.hasMoreElements();) {
            curr = (Port) e.nextElement();
            if (ev.get_src() == curr.get_dest()) {
                found = curr;
            }
        }
        return found;
    }
    // Search for a port by name
    /**
     * Search through this entity's ports, for one called <tt>name</tt>.
     *
     * @param name The name of the port to search for
     * @return A reference to the port, or null if it could not be found
     */
    public Port get_port(String name) {

        Port found = null, curr;
        Enumeration e;
        for (e = ports.elements(); e.hasMoreElements();) {
            curr = (Port) e.nextElement();
            if (name.compareTo(curr.get_pname()) == 0) {
                found = curr;
            }
        }
        if (found == null) {
            System.out.println("Sim_entity: could not find port " + name
                    + " on entity " + this.name);
        }
        return found;
    }

    // Public update methods
    /**
     * Add a port to this entity.
     *
     * @param port A reference to the port to add
     */
    public void add_port(Port port) {
        ports.addElement(port);
        port.set_src(this.me);
        
    }

   
    // The body function which should be overidden
    /**
     * The method which defines the behavior of the entity. This method should
     * be overidden in subclasses of Sim_entity.
     */
    public void body() {
        System.out.println("Entity " + name + " has no body().");
    }

    

    /**
     * Write a trace message.
     *
     * @param level The level at which the trace should be printed, used with
     * <tt>Sim_system.set_trc_level()</tt> to control what traces are printed
     * @param msg The message to be printed
     */
    public void sim_trace(int level, String msg) {
        if ((level & Core.get_trc_level()) != 0) {
            Core.trace(me, msg);
        }
    }

    // The schedule functions
    /**
     * Send an event to another entity, by id number with data.
     *
     * @param dest The unique id number of the destination entity
     * @param delay How long from the current simulation time the event should
     * be sent
     * @param tag An user-defined number representing the type of event.
     * @param data A reference to data to be sent with the event.
     */
    public void sim_schedule(int dest, double delay, int tag, Object data) {
        Core.send(me, dest, delay, tag, data);
    }

    /**
     * Send an event to another entity, by id number and with <b>no</b> data.
     *
     * @param dest The unique id number of the destination entity
     * @param delay How long from the current simulation time the event should
     * be sent
     * @param tag An user-defined number representing the type of event.
     */
    public void sim_schedule(int dest, double delay, int tag) {
        Core.send(me, dest, delay, tag, null);
    }

    /**
     * Send an event to another entity, by a port reference with data.
     *
     * @param dest A reference to the port to send the event out of
     * @param delay How long from the current simulation time the event should
     * be sent
     * @param tag An user-defined number representing the type of event.
     * @param data A reference to data to be sent with the event.
     */
    public void sim_schedule(Port dest, double delay, int tag, Object data) {
        Core.send(me, dest.get_dest(), delay, tag, data);
    }
    
    /**
     * Send an event to another entity, by a port reference with data.
     *
     * @param dest A reference to the link to send the event
     * @param delay How long from the current simulation time the event should
     * be sent     
     * @param data Patient Details
     */
    public void process(Port dest, double delay, Object data) {
        Core.send(me, dest.get_dest(), delay, 99, data);
    }
    public void process2(Port dest, double delay, Object data) {
        Core.insert(me, dest.get_dest(), delay, 99, data);
    }
    /**
     * Send an event to another entity, by a port reference with <b>no</b> data.
     *
     * @param dest A reference to the port to send the event out of
     * @param delay How long from the current simulation time the event should
     * be sent
     * @param tag An user-defined number representing the type of event.
     */
    public void sim_schedule(Port dest, double delay, int tag) {
        Core.send(me, dest.get_dest(), delay, tag, null);
    }

    /**
     * Send an event to another entity, by a port name with data.
     *
     * @param dest The name of the port to send the event out of
     * @param delay How long from the current simulation time the event should
     * be sent
     * @param tag An user-defined number representing the type of event.
     * @param data A reference to data to be sent with the event.
     */
    public void sim_schedule(String dest, double delay, int tag, Object data) {
        Core.send(me, get_port(dest).get_dest(), delay, tag, data);
    }

    /**
     * Send an event to another entity, by a port name with <b>no</b> data.
     *
     * @param dest The name of the port to send the event out of
     * @param delay How long from the current simulation time the event should
     * be sent
     * @param tag An user-defined number representing the type of event.
     */
    public void sim_schedule(String dest, double delay, int tag) {
        Core.send(me, get_port(dest).get_dest(), delay, tag, null);
    }

    /**
     * Hold until the entity receives an event.
     *
     * @param ev The event received is copied into <tt>ev</tt> if it points to
     * an blank event, or discarded if <tt>ev</tt> is
     * <tt>null</tt>
     */
    public void sim_wait(Event ev) {
       
        if ((ev != null) && (evbuf != null)) {
            ev.copy(evbuf);
        }
    }

    /**
     * Count how many events matching a predicate are waiting for this entity on
     * the deferred queue.
     *
     * @param p The event selection predicate
     * @return The count of matching events
     */
    public int sim_waiting(Sim_predicate p) {
        return Core.waiting(me, p);
    }

    /**
     * Count how many events are waiting of this entity on the deferred queue
     *
     * @return The count of events
     */
    public int sim_waiting() {
        return Core.waiting(me, Core.SIM_ANY);
    }

    /**
     * Extract the first event waiting for this entity on the deferred queue,
     * matched by the predicate <tt>p</tt>.
     *
     * @param p An event selection predicate
     * @param ev The event matched is copied into <tt>ev</tt> if it points to a
     * blank event, or discarded if <tt>ev</tt> is
     * <tt>null</tt>
     */
    public void sim_select(Sim_predicate p, Event ev) {
        Core.select(me, p);
        if ((ev != null) && (evbuf != null)) {
            ev.copy(evbuf);
        }
    }

    /**
     * Cancel the first event waiting for this entity on the future queue,
     * matched by the predicate <tt>p</tt>. Returns the number of events
     * cancelled (0 or 1).
     *
     * @param p An event selection predicate
     * @param ev The event matched is copied into <tt>ev</tt> if it points to a
     * blank event, or discarded if <tt>ev</tt> is
     * <tt>null</tt>
     */
    public int sim_cancel(Sim_predicate p, Event ev) {
        Core.cancel(me, p);
        if ((ev != null) && (evbuf != null)) {
            ev.copy(evbuf);
        }
        if (evbuf != null) {
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * Repeatedly <tt>sim_wait()</tt> until the entity receives an event matched
     * by a predicate, all other received events are discarded.
     *
     * @param p The event selection predicate
     * @param ev The event matched is copied into <tt>ev</tt> if it points to a
     * blank event, or discarded if <tt>ev</tt> is
     * <tt>null</tt>
     */
    public void sim_wait_for(Sim_predicate p, Event ev) {
        boolean matched = false;
        while (!matched) {
            sim_wait(ev);
            if (p.match(ev)) {
                matched = true;
            } else {
                sim_putback(ev);
            }
        }
    }

    /**
     * Put an event back on the defered queue.
     *
     * @param ev The event to reinsert
     */
    public void sim_putback(Event ev) {
        Core.putback(ev);
    }

    /**
     * Get the first event matching a predicate from the deferred queue, or, if
     * none match, wait for a matching event to arrive.
     *
     * @param p The predicate to match
     * @param ev The event matched is copied into <tt>ev</tt> if it points to a
     * blank event, or discarded if <tt>ev</tt> is
     * <tt>null</tt>
     */
    public void sim_get_next(Sim_predicate p, Event ev) {
        if (sim_waiting(p) > 0) {
            sim_select(p, ev);
        } else {
            sim_wait_for(p, ev);
        }
    }

    /**
     * Get the first event from the deferred queue waiting on the entity, or, if
     * there are none, wait for an event to arrive.
     *
     * @param ev The event matched is copied into <tt>ev</tt> if it points to a
     * blank event, or discarded if <tt>ev</tt> is
     * <tt>null</tt>
     */
    public void sim_get_next(Event ev) {
        sim_get_next(Core.SIM_ANY, ev);
    }

    /**
     * Get the id of the currently running entity
     *
     * @return A unique entity id number
     */
    public int sim_current() {
        return this.get_id();
    }

    /**
     * Send on an event to an other entity, through a port.
     *
     * @param ev A reference to the event to send
     * @param p A reference to the port through which to send
     */
    public void send_on(Event ev, Port p) {
        sim_schedule(p.get_dest(), 0.0, ev.type(), ev.get_data());
    }

    //
    // Package level methods
    //
    // Package access methods
    int get_state() {
        return state;
    }

    Event get_evbuf() {
        return evbuf;
    }

    // The states
    static final int RUNNABLE = 0;
    static final int WAITING = 1;
    static final int HOLDING = 2;
    static final int FINISHED = 3;

    // Package update methods
    void restart() {
        restart.v();
    }

    void set_going() {
        restart.v();
    } // FIXME same as restart, both needed?

    void set_state(int state) {
        this.state = state;
    }

    void set_id(int id) {
        me = id;
    }

    void set_evbuf(Event e) {
        evbuf = e;
    }

    void poison() {
        restart.v();
       // this.stop();
    }

    /**
     * Internal method - don't overide
     */
    public final void run() {
        body();
        state = FINISHED;
        Core.paused();
    }

}
