package com.company;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * SImple client for server.
 *
 * @author Alexander Ferenets (Istamendil) <ist.kazan@gmail.com>
 */
public class Client extends Thread {

    private static String SERVER_HOST = "127.0.0.1";
    private static int SERVER_PORT = 6735;
    private Hero hero;
    public Client(Hero hero){
        this.hero = hero;
    }
    // Main connection object
    private Socket socket;
    // Where from read - get requests
    private BufferedReader in;
    // Where to write - send responses
    private PrintWriter out;

    public void run() {
        try {
            // Estabilish connection to server
            this.socket = new Socket(Client.SERVER_HOST, Client.SERVER_PORT);
            // Get reader-writer streams
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.out = new PrintWriter(socket.getOutputStream(), true); // true -> AutoFLush
;
            // Generate client's ID
            int rand = (int) (Math.random() * 1000);
            System.out.println("Client #" + rand + " has been started");


            while (!this.socket.isClosed()) {


                this.sendRequest("x:"+hero.getX());
                this.sendRequest("y:"+hero.getY());
                // Read answer
                String response = this.getResponse();
                if(response == null){
                    System.out.println("Server closed connection.");
                    socket.close();
                    return;
                }
                Thread.sleep(3);

            }
        } catch (IOException ex) {
            System.err.println("Socket error: " + ex.getMessage());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    /**
     * Usefull method for sending request
     *
     * @param request Response string
     */
    protected void sendRequest(String request) {
        System.out.println(">> " + request);
        this.out.println(request);
        this.out.println();
    }

    /**
     * Read response
     *
     * @return String Response
     */
    protected String getResponse() {
        try {
            StringBuffer response =  new StringBuffer();
            while (true) {
                String s = this.in.readLine();
                // No input (close socket)
                if (s == null) {
                    return null;
                }
                // ... or empty string (space symbols only) -> stop reading
                else if (s.trim().length() == 0) {
                    break;
                }
                response.append(s);
            }

            System.out.println("<< " + response.toString());
            return response.toString();
        } catch (IOException ex) {
            System.err.println("Can't read response: " + ex.getMessage());
        }
        return null;
    }

}