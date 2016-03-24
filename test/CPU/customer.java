
import java.util.Random;
import java.util.Scanner;

public class customer extends person {

    private int customer_number;
    private boolean mail_list;

    customer() {
        Random r = new Random();

        customer_number = r.nextInt(900) + 100;
        
    }
    
    public void mail_list(){
        int check;
        
        Scanner kbd = new Scanner(System.in);
        
        System.out.println("Would you like to be put on the mailing list?");
        System.out.println("Yes(1) No(2)");
        check = kbd.nextInt();

        while (check != 1 && check != 2) {
            System.out.println("Please enter 1 for yes and two for no: ");
            System.out.println("Yes(1) No(2)");
            check = kbd.nextInt();
        }

        switch (check) {
            case 1:
                mail_list = true;
                break;
            case 2:
                mail_list = false;
                break;
            //No default necessary.
        }
    }

    public int getCustomerNumber() {
        return customer_number;
    }

    public boolean getMail() {
        return mail_list;
    }
    
    @Override
    public String toString() {
        String str = "";
        
        str += "\nCustomer Summary: ";
        str += "\nName: " + getName();
        str += "\nAddress: " + getAddress();
        str += "\nPhone Number: " + getPersonalNumber();
        str += "\nCustomer Number: " + getCustomerNumber();
        str += "\nMail Status: " + getMail();
        
        return str;
    }
}
