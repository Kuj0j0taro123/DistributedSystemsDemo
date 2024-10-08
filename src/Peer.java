import java.io.*;
import java.net.*;

public class Peer {

    private int listenPort;
    // hard coded the destination host address for now
    private String destinationAddress = "localhost";
    private int destinationPort;
    String message;


    public Peer(int listenPort, int destinationPort){
        this.listenPort = listenPort;
        this.destinationPort = destinationPort;
    }

    public void listen() {
        // binding the port
        try (ServerSocket serverSocket = new ServerSocket(listenPort)) {
            System.out.println("Listening on port " + listenPort + "...");
            

            // listening...
            while (true) {
                // accept incoming connections
                try (Socket clientSocket = serverSocket.accept()) {
                    System.out.println(clientSocket.getInetAddress() + " connected.");

                    // read data from a connected client/peer
                    InputStream clientInput = clientSocket.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(clientInput));

                    StringBuilder messageStringBuilder = new StringBuilder();
                    String messageChunk;

                    while ((messageChunk = reader.readLine()) != null){
                        messageStringBuilder.append(messageChunk);
                    }
                    
                    String receivedMessage = messageStringBuilder.toString();
                    
                    

                    System.out.println("Message: " + receivedMessage);
                    
                    if (this.message != null){
                        if (message.equals(receivedMessage)){
                            System.out.println("Received the same message twice in a row. Exiting.");
                            System.exit(0);
                        }
                    }
                    
                    
                    message = receivedMessage;

                    Thread.sleep(5000);

                    // relay data to the next peer

                    send(receivedMessage);
                } catch (IOException e) {
                    System.err.println("Error handling client connection: " + e.getMessage());
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            System.err.println("Could not listen on port " + listenPort);
            e.printStackTrace();
        }
    }

    // Relay the received message to the destination host and port
    public void send(String message) {
        try (Socket destinationSocket = new Socket(destinationAddress, destinationPort)) {
            System.out.println("Sending message to " + destinationAddress + ":" + destinationPort);

            // Write data to the destination host
            OutputStream destinationOutput = destinationSocket.getOutputStream();
            PrintWriter writer = new PrintWriter(destinationOutput, true);
            writer.println(message);

        } catch (IOException e) {
            System.err.println("Error sending message: " + e.getMessage());
        }
    }
}
