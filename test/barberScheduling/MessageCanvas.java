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
    /* File: MessageCanvas.java  
     *  
     * This class provides message canvas for the applet GUI.  
     * It will print the statuses of customers and barbers on the GUI.  
     *  
     * @author:      Jie Zhang  
     * Last Updated: 07/18/2002  
     */   
       
    import java.awt.*;   
       
    class MessageCanvas extends Canvas   
    {   
        private Font font;   
        private FontMetrics fm;   
        private int[] barberStatus;   
        private int[] customerStatus;   
        private int[] serviceStatus;        //store the customer id that is cutting hair for each barber   
                                            //serviceStatus[i]=j, Barber i is cutting hair for customer j   
       
        private int msgHeight;   
        private int msgWidth;   
        private int bn, cn;   
        private int frameDelay = 256;   
       
        public MessageCanvas( )   
        {   
            resize(size().width, 50);   
            setBackground(Color.green);   
            font = new Font("TimesRoman", 1, 18);   
            fm = getFontMetrics(font);   
            msgHeight = fm.getHeight();   
       
        }   
       
       public void setMessage(int barberN, int customerN)   
        {   
            bn = barberN;   
            cn = customerN;   
            barberStatus = new int[bn+1];   
            customerStatus = new int[cn+1];   
            serviceStatus = new int[bn+1];   
            repaint();   
        }   
       
       
        void println(String s)   
        {   
            msgWidth = fm.stringWidth(s);   
            repaint();   
        }   
       
        void println(int s, String st, int id)   
        {   
                if(st.equals("b"))   
                        barberStatus[id] = s;   
                else   
                    customerStatus[id] = s;   
                repaint();   
       }   
       
        void println(int s, String st, int id, int cid)   
        {   
                if(st.equals("b"))   
                {   barberStatus[id] = s;   
                    serviceStatus[id] = cid;   
                }   
                else   
                    customerStatus[id] = s;   
                repaint();   
       }   
       
       
        @Override
        public void paint(Graphics g)   
        {   
            g.setFont(font);   
            int xpos = 40;   
            int ypos = 30;   
       
            g.drawString("Status of Customers: ", 60, 20);   
            g.drawString("Status of Barbers: ", 380, 20);   
            g.setFont(new Font("TimesRoman", 1, 12));   
       
            for(int i=1; i<=cn;i++)   
            {   
                g.setColor(Color.black);   
                g.drawString("C" + i, xpos, ypos+(12*i+5*(i-1)));   
                if(customerStatus[i] == 0)   
                {   
                    g.setColor(Color.yellow);   
                    g.fillOval(xpos+40, ypos+(2*i+15*(i-1)), 14, 14);   
                    g.drawString("Standing ...", xpos+80, ypos+(12*i + 5*(i-1)));   
                }   
                else if (customerStatus[i] == 1)   
                {   
                    g.setColor(Color.gray);   
                    g.fillOval(xpos+40, ypos+(2*i+15*(i-1)), 14, 14);   
                    g.drawString("Cutting Hair...", xpos+80, ypos+(12*i + 5*(i-1)));   
                }   
                else if (customerStatus[i] == 2)   
                {   
                    g.setColor(Color.blue);   
                    g.fillOval(xpos+40, ypos+(2*i+15*(i-1)), 14, 14);   
                    g.drawString("Waiting for Cashier", xpos+80, ypos+(12*i + 5*(i-1)));   
                }   
                else if (customerStatus[i] == 3)   
                {   
                    g.setColor(Color.red);   
                    g.fillOval(xpos+40,ypos+(2*i+15*(i-1)), 14, 14);   
                    g.drawString("Finished the Hair Cut", xpos+80, ypos+(12*i + 5*(i-1)));   
                }   
                else if (customerStatus[i] == 4)   
                {   
                    g.setColor(Color.red);   
                    g.fillOval(xpos+40, ypos+(2*i+15*(i-1)), 14, 14);   
                    g.drawString("Waiting to pay", xpos+80, ypos+(12*i + 5*(i-1)));   
                }   
                else if (customerStatus[i] == 5)   
                {   
                    g.setColor(Color.blue);   
                    g.fillOval(xpos+40, ypos+(2*i+15*(i-1)), 14, 14);   
                    g.drawString("Sitting on the Sofa", xpos+80, ypos+(12*i + 5*(i-1)));   
                }else if (customerStatus[i] == 6)   
                {   
                    g.setColor(Color.red);   
                    g.fillOval(xpos+40, ypos+(2*i+15*(i-1)), 14, 14);   
                    g.drawString("Sitting on the BarberChair", xpos+80, ypos+(12*i + 5*(i-1)));   
                }else if (customerStatus[i] == 7)   
                {   
                    g.setColor(Color.red);   
                    g.fillOval(xpos+40, ypos+(2*i+15*(i-1)), 14, 14);   
                    g.drawString("Waiting to pay", xpos+80, ypos+(12*i + 5*(i-1)));   
                }else if (customerStatus[i] == 9)   
                {   
                    g.setColor(Color.green);   
                    g.fillOval(xpos+40, ypos+(2*i+15*(i-1)), 14, 14);   
                    g.drawString("Left ", xpos+80, ypos+(12*i + 5*(i-1)));   
                }else if (customerStatus[i] == 10)   
                {   
                    g.setColor(Color.gray);   
                    g.fillOval(xpos+40, ypos+(2*i+15*(i-1)), 14, 14);   
                    g.drawString("Finished, but hold the resources unnecessarily", xpos+80, ypos+(12*i + 5*(i-1)));   
                }else if (customerStatus[i] == 11)   
                {   
                    g.setColor(Color.gray);   
                    g.fillOval(xpos+40, ypos+(2*i+15*(i-1)), 14, 14);   
                    g.drawString("C1 hasn't finished but has to leave", xpos+80, ypos+(12*i + 5*(i-1)));   
                }   
            }   
            xpos = 380;   
            ypos = 40;   
            for(int i=1; i<=bn; i++)   
            {   
                g.setColor(Color.black);   
                g.drawString("B" + i, xpos, ypos+(15*i+10*(i-1)));   
                if(barberStatus[i] == 0)   
                {   
                    g.setColor(Color.yellow);   
                    g.fillOval(xpos+60, ypos+(2*i+22*(i-1)), 18, 18);   
                    g.drawString("Sleeping ...", xpos+120, ypos+(15*i + 10*(i-1)));   
                }   
                else if (barberStatus[i] == 1)   
                {   
                    g.setColor(Color.gray);   
                    g.fillOval(xpos+60, ypos+(2*i+22*(i-1)), 18, 18);   
                    g.drawString("Cutting Hair for C"+serviceStatus[i], xpos+120, ypos+(15*i + 10*(i-1)));   
                }   
                else if (barberStatus[i] == 2)   
                {   
                    g.setColor(Color.blue);   
                    g.fillOval(xpos+60, ypos+(2*i+22*(i-1)), 18, 18);   
                    g.drawString("Accepting Payment...", xpos+120, ypos+(15*i + 10*(i-1)));   
                }   
                else if (barberStatus[i] == 3)   
                {   
                    g.setColor(Color.red);   
                    g.fillOval(xpos+60, ypos+(2*i+22*(i-1)), 18, 18);   
                    g.drawString("Finished the Hair Cut", xpos+120, ypos+(15*i + 10*(i-1)));   
                }   
                else if (barberStatus[i] == 4)   
                {   
                    g.setColor(Color.gray);   
                    g.fillOval(xpos+60, ypos+(2*i+22*(i-1)), 18, 18);   
                    g.drawString("Waiting ... ", xpos+120, ypos+(15*i + 10*(i-1)));   
                }   
       
       
            }   
        }   
    }   
