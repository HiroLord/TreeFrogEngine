package com.discretesoftworks.networking;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import com.discretesoftworks.framework.GameController;

public abstract class NetworkGameController extends GameController{

	public static Writer writer;
	
	public static final String hostname = "192.168.0.8";
	
	public boolean initializeNetwork(){
		writer = new Writer();
		return true;
	}
	
	public boolean connect(){
		if (writer == null){
			initializeNetwork();
		}
        int port = 7497;
        try {
        	System.out.println("Connecting...");
            Socket socket = new Socket(hostname, port);
            System.out.println("\nConnection successful thru "+hostname+":"+port);
	        socket.setTcpNoDelay(true);
            System.out.println("TCP No Delay: "+socket.getTcpNoDelay());
            writer.setStreams(new BufferedOutputStream(socket.getOutputStream()),
            				 new BufferedInputStream(socket.getInputStream()));
            return true;
        } catch (UnknownHostException e) {
            System.err.println("Unknown host: "+hostname);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Could not connect to '"+hostname+"'");
            System.exit(2);
        }
        return false;
	}
	
	public void listen(){
		try{
			while (writer.getInputStream().available() > 0){
//				System.out.print("In! Buffer size: "+in.available());
				userDefined(writer.getInputStream());
			}
		} catch (IOException ioe){
			System.err.println("Connection lost.");
			System.exit(1);
		}
	}
	
	// Implement this method if you want to do networking.
	public void userDefined(InputStream in){
		try{
			in.skip(in.available());
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
}
