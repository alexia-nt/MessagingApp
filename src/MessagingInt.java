import java.rmi.*;
import java.util.ArrayList;

public interface MessagingInt extends Remote {

    public int create_account(String username) throws RemoteException;

    public ArrayList<Account> show_accounts(int authToken) throws RemoteException;

    public boolean send_message(int authToken, String recipient, String message) throws RemoteException;

    public ArrayList<Message> show_inbox(int authToken) throws RemoteException;

    public String read_message(int authToken, int messageID) throws RemoteException;

    public int delete_message(int authToken, int messageID) throws RemoteException;
}