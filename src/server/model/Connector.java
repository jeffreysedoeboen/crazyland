package server.model;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import server.model.tile.Tile;

public class Connector extends Thread {
	
	GameServer server = null;
	
	
	public Connector(GameServer s){
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
		 
		//try to listen to port 1337, LEEEEEEET!
		try{
			serverSocket = new ServerSocket(1337);         
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
    			System.out.println("Exception Caught in Login.java line 59. Trying to get OutputStream and InpuStream from socket.");
    			e.printStackTrace();
    		} 
    		
    		out = new PrintWriter(outStream, true);	
            in = new Scanner(inStream);
            
            Tile t = (Tile)server.getWorld().getRespawns().get((int) (Math.random()*server.getWorld().getRespawns().size()));
            Player p = new Player("Henk", t.getX() * 16,t.getY() * 16);
            
            Sender s = new Sender(out,p);
            Receiver r = new Receiver(in,p,server);
            r.start();
            server.addReceiver(r);
            server.addSender(s);
    		server.addPlayer(p);
            
            //we don't want 500 connections in 2 seconds.....
			try{
				Thread.currentThread();
				Thread.sleep(1000); //TODO: fix me
			}catch(InterruptedException ie){
				
			}
			
		}
		
	}
	
}
