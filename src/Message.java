public class Message implements java.io.Serializable {
    public boolean isRead;
    public String sender;
    public String receiver;
    public String body;
    public int messageID;

    public Message(String sender, String receiver, String body, int messageID) {
        this.isRead = false;
        this.sender = sender;
        this.receiver = receiver;
        this.body = body;
        this.messageID = messageID;
    }
}
