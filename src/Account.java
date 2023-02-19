import java.util.ArrayList;
import java.util.List;

public class Account implements java.io.Serializable {
    public String username;
    public int authToken;
    public List<Message> messageBox;

    public Account(String username, int authToken) {
        this.username = username;
        this.authToken = authToken;
        this.messageBox= new ArrayList<Message>();
    }

    public String getUsername(){
        return username;
    }

    public int getAuthToken(){
        return authToken;
    }

    public void addMessage(Message message){
        messageBox.add(message);
    }

    public ArrayList<Message> getInbox(){
        return (ArrayList<Message>) messageBox;
    }

    // returns the message with message ID == messID
    // if there is no message with this message ID, returns null
    public String getMessage(int messID){
        for (Message message : messageBox) {
            if (message.messageID == messID) {
                String mess = "(" + message.sender + ") " + message.body;
                message.isRead = true;
                return mess;
            }
        }
        return null;
    }

    // deletes a message with message ID == messID
    // if there is no message with this message ID, returns false
    public boolean deleteMessage(int messID){
        for (Message message : messageBox) {
            if (message.messageID == messID) {
                messageBox.remove(message);
                return true;
            }
        }
        return false;
    }
}
