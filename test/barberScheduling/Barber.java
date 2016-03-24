package barberScheduling;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author behnish
 */
    /*  Barber.java  
     *  
     *  The Barber is implemented as thread, and its main activities are sleeping for a random time,  
     *  and then call the methods in Barbershop class.  
     *  
     *  
     *  @author:      Jie Zhang  
     *  Last Updated: 07/19/2002  
     */   
       
    public class Barber extends Thread{   
        private BarberShop shop;   
        private BarberShopApplet tapplet;   
        private int pid;   
        int delay       = 2500;   
        int status      = 0;   
        int customerID  = 0;   
       
        public Barber(BarberShopApplet applet, BarberShop iq, int id){   
            shop    = iq;   
            tapplet = applet;   
            pid     = id;   
        }   
       
        public void run(){   
       
            while(true){   
                try{   
                    status = 0;   
                    tapplet.mc.println(status, "b", pid);   
                    sleep((int)(Math.random()*delay));   
                    shop.cutHair(tapplet, pid);   
                    sleep((int) (Math.random()*delay));   
                    shop.finishCut(tapplet, pid);   
       
                } catch(InterruptedException e){   
                    System.err.println("Exception " +  e.toString());   
                }   
            }   
        }   
       
    }   
       
