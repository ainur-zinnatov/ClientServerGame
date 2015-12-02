package com.company;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Main class. Starts ServerSocket - object that can create new sockets witj clients.
 * @author Alexander Ferenets (Istamendil) <ist.kazan@gmail.com>
 */
public class Server extends Thread {
    private static final int PORT = 6735; // Some kind a random port =)
    private static final int MAX_CONNECTIONS = 5;

    private ServerSocket serverSocket;
    private static ArrayList<ServerProcessor> connections;

    public static ArrayList<ServerProcessor> getConnections() {
        return connections;
    }

    public void run() {
        this.connections = new ArrayList<>();

        try{
            // Create server
            this.serverSocket = new ServerSocket(Server.PORT);
            // Wait for connections
            while(true){
                // Got new connection. Now we can read (get) and write(send) data to it.
                Socket socket = this.serverSocket.accept();
                if(connections.size() < Server.MAX_CONNECTIONS){
                    // Create new thread for every connection...
                    // depending on sendResponse special processor
                    ServerProcessor sp = new ServerProcessor(socket);
                    (new Thread(sp)).start();
                    this.connections.add(sp);
                }
                else{
                    // Rude way...
                    System.err.println("Too many connections");
                }
            }
        }
        catch(IOException ex){
            System.err.println("Couldn't start server: "+ex.getMessage());
        }
    }




}