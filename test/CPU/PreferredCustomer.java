import java.util.Scanner;

public class PreferredCustomer extends customer {

    private int amount_spent;
    private double discount_level;
    
    public static void main(String[] args){
        person person = new person();
        customer customer = new customer();
        PreferredCustomer preferredcustomer = new PreferredCustomer();
        
        person.person();
        customer.mail_list();
        
        System.out.println(preferredcustomer);
        
        preferredcustomer.DiscountLevel();
    }
    
    
    
    public void DiscountLevel(){
        Scanner kbd = new Scanner(System.in);
        
        double temp_amount;
        
        System.out.println("How much did you spend?");
        temp_amount = kbd.nextInt();
        
        amount_spent += temp_amount - (temp_amount * discount_level);
        
        if(amount_spent < 500){discount_level = 0.0;}
        if(amount_spent >= 500){discount_level = 0.05;}
        if(amount_spent >= 1000){discount_level = 0.06;}
        if(amount_spent >= 1500){discount_level = 0.07;}
        if(amount_spent >= 2000){discount_level = 0.10;}
        
        printed();
    }
    
    public void printed(){
        Scanner kbd = new Scanner(System.in);
        
        System.out.print("The amount that has been spent is " + amount_spent);
        System.out.print(", therefore the dicount level is ");
        System.out.println(discount_level + ".");
        System.out.println();
        System.out.println("Would you like to make another purchase?");
        System.out.println("Enter (1) for yes and (2) for no.");
        int another_purchase = kbd.nextInt();
        if(another_purchase == 1){
            DiscountLevel();
        }
    }
    
}
