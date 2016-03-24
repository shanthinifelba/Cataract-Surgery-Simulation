/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package library;


/**
 *
 * @author behnish
 */
public class Link extends Port{

    private boolean open = false;
    private String portName;
    public Link(String port_name) {
        super(port_name);
        this.portName = port_name;
    }    

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }
    
}
