package com.viris.debuger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.omg.CORBA.portable.ApplicationException;

import xyzkwjbl.viris.sample.NextActivity;
import xyzkwjbl.viris.service.Request;
import xyzkwjbl.viris.service.TransactionUnit;





public class SocketClient {

	private int socket;
	private Socket s;
	
	
	public SocketClient(int socket) {
		this.socket = socket;
		
	}
	public void testConnection() throws UnknownHostException, IOException{
		//we try to open a connection if service is listening and closing after
		//if and exception is thrown the server is not listening
		s = new Socket("localhost",socket);
		s.close();
	}

	public void setPort(int port){
		this.socket = port;
	}
	public int getPort(){
		return this.socket;
	}
	public List<TransactionUnit> message(Request msg) throws IOException, ClassNotFoundException {
		
		
			s = new Socket("localhost",socket);  
		
			ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
			out.writeObject(msg);  
			
			ObjectInputStream in = new ObjectInputStream(s.getInputStream()); 
			
			List<TransactionUnit> list=(List<TransactionUnit>)in.readObject();
			/*
			 * For debugging
			 * 
			while( (list = (List<TransactionUnit>)in.readObject()) != null){

				for (TransactionUnit element : list){
					System.out.println(element.name);
				}
				
			}*/
			
			in.close();
			out.close(); 
			return list;
			
			 
		
		
	}
}
