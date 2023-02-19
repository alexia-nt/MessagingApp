import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

public class Client {
    static void printAuthToken(int authToken){
        if (authToken == -1){
            System.out.println("Invalid username!");
        } else if (authToken == -2) {
            System.out.println("Sorry, the user already exists.");
        }
        else {
            System.out.println(authToken);
        }
    }

    static void printAccounts(ArrayList<Account> accountsList){
        if(accountsList == null){
            System.out.println("Invalid auth token!");
        }
        else {
            for (int i = 0; i < accountsList.size(); i++) {
                int num = i + 1;
                System.out.print(num + ". " + accountsList.get(i).getUsername() + "\n");
            }
        }
    }

    static void printSendMessage(boolean ret){
        if(ret){
            System.out.println("OK");
        }
        else {
            System.out.println("User does not exist");
        }
    }

    static void printInbox(ArrayList<Message> messageBox){
        if(messageBox == null){
            System.out.println("Invalid auth token!");
        }
        else {
            if (messageBox.isEmpty()) {
                System.out.println("You don't have any messages.");
                return;
            }
            for (Message message : messageBox) {
                if (message.isRead)
                    System.out.println(message.messageID + ". from: " + message.sender); // get messageID, get sender
                else
                    System.out.println(message.messageID + ". from: " + message.sender + "*"); // get messageID, get sender
            }
        }
    }

    static void printMessage(String message){
        if(message == null){
            System.out.println("Message ID does not exist!");
        }
        else {
            System.out.println(message);
        }
    }

    static void printDelMess(int delMess){
        if (delMess == -1)
            System.out.println("Invalid auth token");
        else if (delMess == 0)
            System.out.println("Message does not exist!");
        else
            System.out.println("OK");
    }

    public static void main(String[] args) {
        try {
            String username, recipient, message;
            int authToken, messageID;

            String ip = args[0]; // ip address
            int port = Integer.parseInt(args[1]); // port number
            int fnid = Integer.parseInt(args[2]); // fn id

            // get reference for remote object
            Registry rmiRegistry = LocateRegistry.getRegistry(ip, port);
            MessagingInt stub = (MessagingInt) rmiRegistry.lookup("messaging");

            if (fnid == 1) { // create account
                username = args[3];
                int authTok = stub.create_account(username);
                printAuthToken(authTok);
            }
            else {
                authToken = Integer.parseInt(args[3]); // get the auth token
                if (fnid == 2){ // show accounts
                    ArrayList<Account> accountsList = stub.show_accounts(authToken);
                    printAccounts(accountsList);
                }
                else if (fnid == 3) { // send message
                    recipient = args[4];
                    message = args[5];
                    boolean ret = stub.send_message(authToken, recipient, message);
                    printSendMessage(ret);
                }
                else if (fnid == 4) { // show inbox
                    ArrayList<Message> messageBox = stub.show_inbox(authToken);
                    printInbox(messageBox);
                }
                else if (fnid == 5 || fnid == 6) {
                    messageID = Integer.parseInt(args[4]); // get the message ID
                    if (fnid == 5) { // read message
                        String mess = stub.read_message(authToken, messageID);
                        printMessage(mess);
                    }
                    else { // delete message
                        int delMess = stub.delete_message(authToken, messageID);
                        printDelMess(delMess);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}