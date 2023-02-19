import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class RemoteMessaging extends UnicastRemoteObject implements MessagingInt  {
    public int authTok=0, messID=0;
    public ArrayList<Account> accounts = new ArrayList<Account>();
    public RemoteMessaging() throws RemoteException {
        super();
    }

    boolean usernameIsValid(String s){
        if (s == null) // checks if the String is null
            return false;

        int len = s.length();
        for (int i = 0; i < len; i++) {
            if (!Character.isLetter(s.charAt(i)) && !Character.isDigit(s.charAt(i)) && (s.charAt(i) != '_')) {
                return false;
            }
        }
        return true;
    }

    boolean authTokenExists(int authToken){
        if (authToken>0 && authToken<=this.authTok) {
            return true;
        }
        else {
            //System.out.println("This token does not exist!");
            return false;
        }
    }

    public int create_account(String username) throws RemoteException {
        // check if username is valid
        if (!usernameIsValid(username))
            return -1;

        // check if username exists
        for (Account account : accounts) {
            if (account.getUsername().equals(username))
                return -2;
        }

        // username can be added to the list
        authTok++;

        // create account
        Account acc = new Account(username, authTok);
        accounts.add(acc);

        return authTok;
    }

    public ArrayList<Account> show_accounts(int authToken) throws RemoteException {
        // check if authToken exists
        if (!authTokenExists(authToken))
            return null;

        // there is at least one account in the list
        return accounts;
    }

    public boolean send_message(int authToken, String recipient, String message) throws RemoteException{
        // find username of sender from authToken
        String sender;
        for (Account accountSender : accounts) {
            if (accountSender.getAuthToken() == authToken) {
                sender = accountSender.getUsername();

                // check if recipient exists
                for (Account accountRecep : accounts) {
                    if (accountRecep.getUsername().equals(recipient)) {
                        // create message object
                        messID++;
                        Message messageObj = new Message(sender, recipient, message, messID);
                        accountRecep.addMessage(messageObj);
                        return true;
                    }
                }
                // did not find recipient username
                return false;
            }
        }
        // did not find sender authToken
        return false;
    }

    public ArrayList<Message> show_inbox(int authToken) throws RemoteException {
        // check if authToken exists
        if(!authTokenExists(authToken))
            return null;

        // show inbox
        for (Account account : accounts) {
            if (account.getAuthToken() == authToken) {
                return account.getInbox();
            }
        }
        return null;
    }

    public String read_message(int authToken, int messageID) throws RemoteException{
        // check if authToken exists
        if(!authTokenExists(authToken))
            return "Invalid auth token!";

        // read message
        for (Account account : accounts) {
            if (account.getAuthToken() == authToken) {
                return account.getMessage(messageID);
            }
        }
        return null;
    }

    public int delete_message(int authToken, int messageID) throws RemoteException{
        // check if authToken exists
        if(!authTokenExists(authToken))
            return -1; // it will print "message does not exist"

        // delete message
        for (Account account : accounts) {
            if (account.getAuthToken() == authToken) {
                boolean delMess = account.deleteMessage(messageID);
                if(delMess)
                    return 1;
                else
                    return 0; // invalid message id
            }
        }
        return 0;
    }
}
