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
/* File: BarberShopApplet.java  
 *  
 * This is a Java applet file for Barbershop problem animation. The GUI  
 * of this applet contains three parts: animation canvas, message canvas  
 * and a button panel.  
 * The animation canvas is where the Barbershop animation is displayed.  
 * The message canvas is where the statues of barbers and customers are displayed.  
 * The button panel has 6 basic buttons: START, STOP, PAUSE, CONTINUE, FASTER,  
 * SLOWER.  
 *  
 * This applet will allow user to choose from a fair barbershop or unfair barbershop.  
 * In the fair barbershop, unless the user choose the number of customers, default  
 * number of the customers is 4.  
 * The number of customers in the unfair barbershop is also 4. And there are two  
 * unfair situations that the user can choose from.  
 *  
 *  
 * @author:      Jie Zhang  
 * Last Updated: 07/19/2002  
 */   
   
   
import java.awt.*;   
import java.applet.Applet;   
   
public class BarberShopApplet extends Applet   
{   
    private BarberShopApplet applet = this;   
    private BarberShop myShop;   
    private Button fastButton, slowButton, stopButton, startButton,pauseButton, continueButton;   
    private Panel buttonPanel, namePanel, namePanel2;   
    private Checkbox haltResource, requestResource;   
    private Choice customer;   
    private int customerN = 4;      //default number of customer and barber   
    private int barberN   = 3;   
    private Thread at;   
    MessageCanvas mc;   
    Customer[] c;   
    Barber[] b;   
    int haltFlag     = 0;   
    int requestFlag  = 0;   
    synchronized void startPushed() {notify();}   
    synchronized void stopPushed()  {notify();}   
   
    @Override
    public void init() {   
        resize(800, 600);   
        setLayout(new GridLayout(3, 1));   
   
        myShop = new BarberShop();   
        mc = new MessageCanvas();   
        add(myShop);                        //add BarberShop canvas at the top   
        add(mc);                            //add message box canvas in the middle   
   
        buttonPanel = new Panel();   
        Panel namePanel = new Panel();   
        Panel bPanel = new Panel();         // to hold all buttons and the labels   
        bPanel.setFont(new Font("TimesRoman", Font.BOLD, 14));   
        bPanel.setLayout(new GridLayout(3, 1));   
   
        buttonPanel.add(startButton     = new Button("START"));   
        buttonPanel.add(stopButton      = new Button("STOP"));   
        buttonPanel.add(pauseButton     = new Button("PAUSE"));   
        buttonPanel.add(continueButton  = new Button("CONTINUE"));   
        buttonPanel.add(fastButton      = new Button("FAST"));   
        buttonPanel.add(slowButton      = new Button("SLOW"));   
   
        Label titleLabel = new Label("Fair Barbershop", Label.CENTER);   
        titleLabel.setFont(new Font("TimesRoman", Font.BOLD, 16));   
        titleLabel.setForeground(Color.blue);   
        Label textLabel = new Label("Maximum Shop Capacity is 8 Customers", Label.CENTER);   
   
        Label titleLabel2 = new Label("Unfair Barbershop ", Label.CENTER);   
        Label textLabel2 = new Label("4 Customers In The Shop", Label.CENTER);   
        titleLabel2.setFont(new Font("TimesRoman", Font.BOLD, 16));   
        titleLabel2.setForeground(Color.blue);   
   
        namePanel.setLayout(new GridLayout(2,1));   
        namePanel.add(titleLabel);   
        namePanel.add(textLabel);   
   
        namePanel2 = new Panel();   
        namePanel2.setLayout(new GridLayout(2,1));   
        namePanel2.add(titleLabel2);   
        namePanel2.add(textLabel2);   
   
        Panel titlePanel = new Panel();   
        titlePanel.setLayout(new GridLayout(1,2));   
        titlePanel.add(namePanel);   
        titlePanel.add(namePanel2);   
   
        Panel choicePanel = new Panel();    //to hold all the choice boxes   
        choicePanel.setLayout(new GridLayout(1,2));   
        customer = new Choice();   
        for(int i = 1; i <=10; i++)   
        {   
            customer.addItem(Integer.toString(i));   
        }   
        customer.select("4");   
        Label customerLabel = new Label("Number of Customers", 2);   
        customerLabel.setBackground(Color.lightGray);   
        Panel customerPanel = new Panel();   
        customerPanel.add(customerLabel);   
        customerPanel.add(customer);   
   
        Panel unfairPanel = new Panel();   
        unfairPanel.setLayout(new GridLayout(2,1));   
        CheckboxGroup g = new CheckboxGroup();   
        unfairPanel.add(haltResource = new Checkbox("Request finished, but resources are held unnecessarily", g, false));   
        unfairPanel.add(requestResource = new Checkbox("Request not finished, but resources are released", g, false));   
        choicePanel.add(customerPanel);   
        choicePanel.add(unfairPanel);   
   
        bPanel.add(titlePanel);   
        bPanel.add(choicePanel);   
        bPanel.add(buttonPanel);   
   
        add(bPanel);   
    }   
   
    @Override
    public boolean action(Event evt, Object arg)   
    {   
        if(evt.target == customer)   
        {   
            customerN = Integer.parseInt(arg.toString());   
            haltResource.setEnabled(false);   
            requestResource.setEnabled(false);   
            return true;   
        }   
        else if(evt.target == haltResource)   
        {   
            startButton.setEnabled(false);   
            customer.setEnabled(false);   
            stopButton.setEnabled(true);   
            haltResource.setEnabled(false);   
            requestResource.setEnabled(false);   
            haltFlag = 1;   
            System.out.println("HaltResource");   
            customerN = 4;   
            myShop.setSize(customerN);   
            c = new Customer[customerN+1];      //Customer[0] is a dummy slot   
            b = new Barber[barberN+1];   
   
            mc.setMessage(barberN, customerN);   
            for(int i = 1; i <= customerN; i++)   
            {   
                c[i] = new Customer(applet, myShop, i);   
            }   
   
            for(int i = 1; i <= barberN; i++)   
            {   
                b[i] = new Barber(applet, myShop, i);   
            }   
            for(int i = 1; i <= barberN; i++)   
            {   
                b[i].start();   
            }   
            for(int i = 1; i <= customerN; i++)   
            {   
                c[i].start();   
            }   
   
            return true;   
        }   
        else if(evt.target == requestResource)   
        {   
            startButton.setEnabled(false);   
            stopButton.setEnabled(true);   
            customer.setEnabled(false);   
            haltResource.setEnabled(false);   
            requestResource.setEnabled(false);   
            System.out.println("RequestResource");   
            requestFlag = 1;   
   
            customerN = 4;   
            myShop.setSize(customerN);   
            c = new Customer[customerN+1];      //Customer[0] is a dummy slot   
            b = new Barber[barberN+1];   
   
            mc.setMessage(barberN, customerN);   
            for(int i = 1; i <= customerN; i++)   
            {   
                c[i] = new Customer(applet, myShop, i);   
            }   
   
            for(int i = 1; i <= barberN; i++)   
            {   
                b[i] = new Barber(applet, myShop, i);   
            }   
            for(int i = 1; i <= barberN; i++)   
            {   
                b[i].start();   
            }   
            for(int i = 1; i <= customerN; i++)   
            {   
                c[i].start();   
            }   
   
            return true;   
        }   
        else if(arg.equals("PAUSE"))   
        {   for(int i = 1; i <= customerN; i++)   
            {   
                if(c[i].isAlive())  c[i].suspend();   
            }   
            for(int i = 1; i <= barberN; i++)   
            {   
                if(b[i].isAlive())  b[i].suspend();   
            }   
            fastButton.setEnabled(false);   
            slowButton.setEnabled(false);   
            return true;   
        }   
        else if(arg.equals("CONTINUE"))   
        {   
            for(int i = 1; i <= customerN; i++)   
            {   
                if(c[i].isAlive())  c[i].resume();   
            }   
   
            for(int i = 1; i <= barberN; i++)   
            {   
                if(b[i].isAlive())  b[i].resume();   
            }   
            fastButton.setEnabled(true);   
            slowButton.setEnabled(true);   
            return true;   
        }   
        else if(arg.equals("FASTER"))   
        {   
            int newDelay = b[1].delay;   
            newDelay /= 2;   
            newDelay = newDelay < 100 ? 100: newDelay;   
   
            for(int i = 1; i <= customerN; i++)   
            {   
                    c[i].delay = newDelay;   
            }   
            for(int i = 1; i <= barberN; i++)   
            {   
                    b[i].delay = newDelay;   
            }   
            return true;   
        }   
        else if(arg.equals("SLOWER"))   
        {   
            int newDelay = b[1].delay;   
            newDelay *= 2;   
            for(int i = 1; i <= customerN; i++)   
            {   
                c[i].delay = newDelay;   
            }   
            for(int i = 1; i <= barberN; i++)   
            {   
                b[i].delay = newDelay;   
            }   
            return true;   
        }   
        else if(arg.equals("START"))   
        {   
            myShop.setSize(customerN);   
            c = new Customer[customerN+1];      //Customer[0] is a dummy slot   
            b = new Barber[barberN+1];   
   
            mc.setMessage(barberN, customerN);   
            for(int i = 1; i <= customerN; i++)   
            {   
                c[i] = new Customer(applet, myShop, i);   
            }   
   
            for(int i = 1; i <= barberN; i++)   
            {   
                b[i] = new Barber(applet, myShop, i);   
            }   
            for(int i = 1; i <= barberN; i++)   
            {   
                b[i].start();   
            }   
            for(int i = 1; i <= customerN; i++)   
            {   
                c[i].start();   
            }   
   
            applet.startPushed();   
            stopButton.setEnabled(true);   
            startButton.setEnabled(false);   
            fastButton.setEnabled(true);   
            slowButton.setEnabled(true);   
            customer.setEnabled(false);   
            haltResource.setEnabled(false);   
            requestResource.setEnabled(false);   
            return true;   
        }   
   
        else if(arg.equals("STOP"))   
        {   
            try{   
                for(int i = 1; i <= customerN; i++)   
                {   
                    if(c[i].isAlive())  c[i].stop();   
                    c[i] = null;   
                }   
   
                for(int i = 1; i <= barberN; i++)   
                {   
                    if(b[i].isAlive())  b[i].stop();   
                    b[i] = null;   
                }   
                }catch(Exception e) {}   
   
            myShop.clear();   
            applet.stopPushed();   
            haltFlag    = 0;   
            requestFlag = 0;   
            startButton.setEnabled(true);   
            customer.setEnabled(true);   
            haltResource.setEnabled(true);   
            requestResource.setEnabled(true);   
            fastButton.setEnabled(true);   
            slowButton.setEnabled(true);   
            if(at != null) at.stop();   
            at = null;   
            return true;   
        }   
        else{ return false;}   
   
    }   
   
}
