/* Evqueue.java
 */

package library;

import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Iterator;

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
 * <br>
 * Peter Urban: rewrote this class to use a SortedSet
 * @see		eduni.simjava.Sim_system
 * @version     0.1, 25 June 1995
 * @author      Ross McNab
 */

public class Evqueue {

  private SortedSet sortedSet;

  // Constructors
  /**
   * Allocates a new Evqueue object.
   */
  public Evqueue() {
    sortedSet = new TreeSet();
  }

  /**
   * Remove and return the event at the top of the queue.
   * @return           The next event.
   */
  public Event pop() {
    Event event = (Event)sortedSet.first();
    sortedSet.remove(event);
    return event;
  }

  /**
   * Return the event at the top of the queue, without removing it.
   * @return	The next event.
   */
  public Event top() {
    return (Event)sortedSet.first();
  }

  /**
   * Add a new event to the queue, preserving the temporal order of the
   * events in the queue.
   * @param new_event	The event to be put on the queue.
   */
  public void add(Event new_event) {
      
    // The event has to be inserted as the last of all events
    // with the same event_time(). Yes, this matters.
    
    new_event.setSerial(serial++);
    sortedSet.add(new_event);

  }

  private long serial = 0;

  public int size() {
    return sortedSet.size();
  }

  public Iterator iterator() {

    return sortedSet.iterator();

  }

}
