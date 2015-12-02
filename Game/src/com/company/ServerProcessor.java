package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alexander Ferenets (Istamendil) <ist.kazan@gmail.com>
 */
    public class ServerProcessor extends Thread{
    // Main connection object
    private Socket socket;
    // Where from read - get requests
    private BufferedReader in;
    // Where to write - send responses
    private PrintWriter out;

    public ServerProcessor(Socket socket) {
        this.socket = socket;
    }

    private int x=0;
    private int y=0;
    private int s_x=0;
    private int s_y=0;
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setS_x(int s_x) {
        this.s_x = s_x;
    }

    public void setS_y(int s_y) {
        this.s_y = s_y;
    }

    /**
     * Process requests
     */

    public void run() {
        try {

            // Main objects
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.out = new PrintWriter(socket.getOutputStream(), true); // true for autoflushing

            // Communicate with client
            while (!this.socket.isClosed()) {
                // Get request
                String request = this.getRequest();
                // Connection has been closed
                if(request == null){
                    sendResponse("delete");
                    System.out.println("Connection has been closed by client.");
                    socket.close();
                }
                // Got request
                else{
                    // Send response
                    if (request.toString().contains("y:")) {
                        y=Integer.parseInt(request.toString().replaceAll("y:",""));

                    }
                    if (request.toString().contains("x:")) {
                        x=Integer.parseInt(request.toString().replaceAll("x:",""));

                    }

                    else {
                        this.sendResponse("Unkown request.");
                    }
                }
            }

        } catch (IOException ex) {
            System.err.println("Socket error: " + ex.getMessage());
        }
    }

    /**
     * Usefull method for sending response
     *
     * @param response Response string
     */
    protected void sendResponse(String response) {
        System.out.println(">> " + response);
        this.out.println(response);
        this.out.println();
    }

    /**
     * Read request
     *
     * @return String Reqest
     */
    protected String getRequest() {
        try {
            StringBuffer request = new StringBuffer();
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
                request.append(s);
            }
            System.out.println("<< " + request.toString());
            return request.toString();
        } catch (IOException ex) {
            System.err.println("Can't read request: " + ex.getMessage());
        }
        return null;
    }}