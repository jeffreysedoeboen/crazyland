package masterserver.connection;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import masterserver.MasterServer;

public class Connector extends Thread {
	
	MasterServer server = null;
	
	
	public Connector(MasterServer s){
		this.server = s;
	}
	
	public void run(){
		//create an empty reference for the socket and serverSocket so we can set and reach them everywhere in run()
		Socket socket = null;
		ServerSocket serverSocket = null;
		
		//we need these for the connection to get input and send output
		//we link them with the right connection later on
		OutputStream outStream = null;
		InputStream inStream = null;
		PrintWriter out = null;
		Scanner in = null;
		 
		//try to listen to port 1338, the normal geek one step ahead!
		try{
			serverSocket = new ServerSocket(1338);         
		}catch(IOException e){
			e.printStackTrace();
		}    
		
		//creating endless connections :D
		while(true){
			
			//empty the connection so we can accept a new one
			socket = null;
			
			//wait for connection
            try{
                socket = serverSocket.accept();
            }catch(IOException e){
                e.printStackTrace();
            }
            
    		//try to get the streams from the socket and catch the error if it can't be received.
    		try {
    			//receive the outstream and instream
    			outStream = socket.getOutputStream();
                inStream = socket.getInputStream();
    		} catch (IOException e) {
    			//print detailed information about the whereabouts of the error and what caused it.
    			System.out.println("Exception. Trying to get OutputStream and InpuStream from socket.");
    			e.printStackTrace();
    		} 
    		
    		out = new PrintWriter(outStream, true);	
            in = new Scanner(inStream);
 
            
            Connection c = null; 
            c = new Connection(in, out, server);
            c.start();
            server.addConnection(c);
            
            //we don't want 500 connections in 2 seconds.....
			try{
				Thread.currentThread();
				Thread.sleep(1000);
			}catch(InterruptedException ie){
				
			}
			
		}
		
	}
	
}
