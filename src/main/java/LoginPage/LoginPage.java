package LoginPage;

import java.util.Scanner;

public class LoginPage {

    public static void main(String[] args) {

        start();


    }

    public static void start(){
        Scanner input= new Scanner(System.in);
        UserService service= new UserService();
        int select;

        do {
            showMenu();
            select= input.nextInt();
            switch (select){
                case 1:
                    service.signUp();
                    break;
                case 2:
                    service.login();
                    break;
                case 0:
                    System.out.println("We wish you a nice day...");
                    break;
                default:
                    System.out.println("You have entered incorrectly, please try again");

            }

        }while (select!=0);

    }

    public static void showMenu(){
        System.out.println("TECHPROEDUCATION");
        System.out.println("1-sign up");
        System.out.println("2-login");
        System.out.println("0-OUT");
        System.out.println("Your choice: ");
    }

}