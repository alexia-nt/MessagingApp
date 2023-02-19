import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
    public static void main(String[] args) {
        try {
            RemoteMessaging stub = new RemoteMessaging();

            int port = Integer.parseInt(args[0]);

            // create the RMI registry on port 5000
            Registry rmiRegistry = LocateRegistry .createRegistry(port);

            // path to access is rmi://localhost:5000/messaging
            rmiRegistry.rebind("messaging", stub);
            System.out.println("Server is ready");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}