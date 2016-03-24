
import java.util.Scanner;

public class person {

    private String name;
    private String address;
    private String phone_number;

    public void person(){
        Scanner kbd = new Scanner(System.in);
        
        System.out.println("Please enter name: ");
        name = kbd.nextLine();
        System.out.println("Please enter address: ");
        address = kbd.nextLine();//One cannot properly check an address.
        
        do {
            System.out.println("Please enter a valid 10 digit phone number: ");
            phone_number = kbd.nextLine();
        } while (!isNumber());
    }
    
    public boolean isNumber() {
        boolean flag = true;
        long numb;
        for(int i = 0; i < phone_number.length(); i++){
            if(!Character.isDigit(phone_number.charAt(i))){
                flag = false;
            }
        }
        if(flag==false){
            return flag;
        }
        else{
            numb = Long.parseLong(phone_number);
            if (!((numb/1000000000) >= 1 && (numb/1000000000) < 10)) {
            flag = false;
        }
        }
        
        return flag;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPersonalNumber() {
        return phone_number;
    }
}
