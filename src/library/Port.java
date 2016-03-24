/* Sim_port.java
 */
package library;

/**
 * This class represents ports which are used to connect entities for event
 * passing.
 *
 * @see Sim_system
 * @version 1.0, 4 September 1996
 * @author Ross McNab
 */
public class Port {

    // Private data members
    private String pname;      // The port's name
    private String dest_ename; // The destination entity's name
    private int srce;          // Index of the source entity
    private int deste;         // Index of the destination entity

    /**
     * Constructor, for stand-alone simulations.
     *
     * @param port_name The name to identify this port
     * @return A new instance of the class Sim_port
     */
    public Port(String port_name) {
        pname = port_name;
        dest_ename = null;
        srce = -1;
        deste = -1;
    }

    // Public access methods
    /**
     * Get the name of the destination entity of this port.
     *
     * @return The name of the entity
     */
    public String get_dest_ename() {
        return dest_ename;
    }

    /**
     * Get the name of this port.
     *
     * @return The name
     */
    public String get_pname() {
        return pname;
    }

    /**
     * Get the unique id number of the destination entity of this port.
     *
     * @return The id number
     */
    public int get_dest() {
        return deste;
    }

    /**
     * Get the unique id number of the source entity of this port.
     *
     * @return The id number
     */
    public int get_src() {
        return srce;
    }

  //
    // Package level interface
    //
    void set_src(int s) {
        srce = s;
    }

    void connect(Entity dest) {
        dest_ename = dest.get_name();
        deste = dest.get_id();
    }

}
