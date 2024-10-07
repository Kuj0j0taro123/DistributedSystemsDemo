import java.io.IOException;
import java.net.ServerSocket;

public class Main{
    public static void main(String[] args) {
        int listenPort = 0;
        int destinationPort = 0;
        Boolean send = null;
        String message = null;

        // parsing arguments
        for(int i = 0; i < args.length; i++){
            if (args[i].equals("--listen-port") || args[i].equals("-p")){
                listenPort = Integer.parseInt(args[i + 1]); // this index might not exist in the array. Maybe fix later?
                // after checking that the port is valid, make a loop to check if something is using it.
            }

            if (args[i].equals("--destination-port") || args[i].equals("-d")){
                destinationPort = Integer.parseInt(args[i + 1]); // this index might not exist in the array
            }

            if (args[i].equals("--send") || args[i].equals("-s")){
                send = true;
            }

            if (args[i].equals("--listen") || args[i].equals("-l")){
                send = false;
            }

            if(args[i].equals("--message") || args[i].equals("-m")){
                message = args[i + 1]; // this index might not exist in the array
            }
        }
        

        if (send == null){
            System.err.println("Ambiguous usage. Am I supposed to listen or send? Use --send (-s) or --listen (-l).");
            System.exit(3);
        }

        if (send == true){
            if (message == null){
                System.err.println("You forgot to specify a message with the '--message' (-m) option. You must specify a message when only sending.");
                System.exit(2);
            }

            if (!isPortValid(destinationPort)){
                System.err.println("Destination port is invalid or you forgot to specify it with the '--destination-port' (-d) option.");
                System.exit(1);
            }
            
            Peer peer = new Peer(listenPort, destinationPort);
            peer.send(message);

        }
        else{
            if (!isPortValid(destinationPort)){
                System.err.println("Destination port is invalid or you forgot to specify it with the '--destination-port' (-d) option.");
                System.exit(1);
            }

            if (!isPortValid(listenPort)){
                System.err.println("Listening port is invalid or you forgot to specify it with the '--listen-port' (-l) option.");
                System.exit(1);
            }

            if (!isPortAvailable(listenPort)){
                System.err.println("Listening port is not available.");
                System.exit(1);
            }

            Peer peer = new Peer(listenPort, destinationPort);
            peer.listen();
        }
        
    }

    public static boolean isPortValid(int port){
        // checks if a port is valid
        if (port > 0 && port <= 65535){
            return true;
        }
        return false;
    }

    public static boolean isPortAvailable(int port){
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            // If the socket binds successfully, the port is available
            return true;
        } catch (IOException e) {
            // If an exception occurs, the port is likely already bound
            return false;
        }
    }
}