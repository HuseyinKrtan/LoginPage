package LoginPage;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserService {

    private String name;
    private String username;
    private String email;
    private String password;
    private User user=new User();

    private DBConnection dbConnection=new DBConnection();

    public void signUp(){
        Scanner inp= new Scanner(System.in);

        // 1) Name-Surname:
        System.out.println("Name- Surname: ");
        name= inp.nextLine();

        // 2) Username:

//        boolean existUsername;
//        do{
        System.out.println("Enter username: ");
        username= inp.nextLine();
//            existUsername=
//            if (existUsername){
//                System.out.println("This user name is used. Try different username!");
//            }
//        }while (existUsername);


        /*
           3) Email:
              must contain @
              must end with gmail.com, hotmail.com or yahoo.com
              In the username part of the mail (before the @) there can only be upper and lowercase letters, number or -._ symbols.
         */


        boolean isValid= true;
//        boolean existsEmail;

        do{
            System.out.println("Enter email: ");
            email= inp.nextLine();
            isValid= validateEmail(email);
//            existsEmail= this.emails.contains(email);
//            if (existsEmail){
//                System.out.println("This email is already registered, try a different email!");
//                isValid=false;
//            }
        }while (!isValid);


        // 4) Password:

        boolean isValidPass= true;

        do {
            System.out.println("Enter your password\n" +
                    "Your password must meet these conditions:\n" +
                    "- must not contain spaces\n" +
                    "- must be at least 6 characters\n" +
                    "- must contain at least one lowercase letter\n" +
                    "- must contain at least one capital letter\n" +
                    "- must contain at least one digit\n" +
                    "- must contain at least one symbol");
            password= inp.nextLine();
            isValidPass= validatePassword(password);
        }while (!isValidPass);


        user= new User(name,username,email,password);
        dbConnection.save(user);

        // End of sign-up
        System.out.println("Congratulations, "+username+" you have registered successfully!");
        System.out.println("You can login to the system with your username or email!");



    }

    // LOGIN
    public void login(){
        Scanner inp= new Scanner(System.in);
        System.out.println("Enter your username or email: ");
        String usernameOrEmail= inp.nextLine();
        user=dbConnection.fetch("email",usernameOrEmail);
        if (user==null) {
            user=dbConnection.fetch("username",usernameOrEmail);
        }
        if (user==null){
            System.out.println("No registered user found in the system!");
            System.out.println("If you are a member, please check your information, if you are not, please register!");
            login();
        }
        boolean isMail= user.getEmail().equals(usernameOrEmail);
        boolean isUsername= user.getUsername().equals(usernameOrEmail);
        if(isMail || isUsername) {
            boolean isWrong = true;
            int count = 0;
            while (isWrong){
                count++;
                System.out.println("Enter your password: ");
                password = inp.nextLine();

                if (user.getPassword().equals(password)) {
                    System.out.println("You have logged in to the system successfully!");
                    isWrong= false;
                } else {

                    if(count>=3){
                        System.out.println("Your account is blocked, because you failed to enter correct password 3 times!");
                        isWrong= false;
                    } else {
                        System.out.println("Your password in incorrect, please try again...");

                    }
                }
            }
        }
        }


    // 3:
    // to run your code without validation you need to comment out validateEmail()
    public  boolean validateEmail(String email){
        boolean isValid;
        boolean space= email.contains(" ");
        boolean containsAt= email.contains("@");
        if(space){
            System.out.println("Email should not contains space!");
            isValid= false;
        } else if (!containsAt) {
            System.out.println("Email must contain @");
            isValid= false;
        } else {
            String firstPart= email.split("@")[0];
            String secondPart= email.split("@")[1];

            int notValid= firstPart.replaceAll("[0-9A-Za-z_.-]","").length();
            boolean checkStart= notValid==0;

            boolean checkEnd= secondPart.equals("gmail.com") ||
                    secondPart.equals("hotmail.com") ||
                    secondPart.equals("yahoo.com");

            if(!checkStart){
                System.out.println("Email cannot contain characters other than upper and lower case letters, numbers or -._ symbols");
            } else if (!checkEnd) {
                System.out.println("Email must end with gmail.com, hotmail.com or yahoo.com");
            }
            isValid= checkStart && checkEnd;
        }

        if (!isValid){
            System.out.println("Invalid email, try again...");
        }

        return isValid;
    }

    // 4:
    public boolean validatePassword(String password){
        boolean isValidPass = true;
        if (!password.replace(" ", "").equals(password)) {
            System.out.println("Password cannot contain a spaces");
            isValidPass =false;
        } else if (password.length()<6) {
            System.out.println("Password must contain at least 6 characters");
            isValidPass = false;
        } else if (password.toUpperCase().equals(password)) {
            System.out.println("Password must contain at least one lowercase character");
            isValidPass = false;
        } else if (password.toLowerCase().equals(password)) {
            System.out.println("Password must contain at least one uppercase character");
            isValidPass = false;
        } else if (password.replaceAll("[0-9]","").equals(password)) {
            System.out.println("Password must contain at least one digit");
            isValidPass = false;
        } else if (password.replaceAll("[0-9A-Za-z]","").length()<=0) {
            System.out.println("Password must contain at least one symbol");
            isValidPass = false;
        }

        if (!isValidPass){
            System.out.println("Please try again...");
        }

        return isValidPass;
    }

}