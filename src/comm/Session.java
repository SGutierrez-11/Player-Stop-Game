package comm;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.UUID;


import events.MessageListener;
import javafx.application.Platform;
import model.Words;


public class Session{

	

	private Socket socket;
	
	private String ip="127.0.0.1";
	private int puerto=6000;
	
	private BufferedReader reader;
	private BufferedWriter writer;
	
	
	private static Session s=null;
	
	private MessageListener message;
	
	public MessageListener getMessage() {
		return message;
	}


	public void setMessage(MessageListener message) {
		this.message = message;
	}


	public static synchronized Session getS(Socket so) {
		if(s==null) {
			s = new Session(so);
		}
		return s;
	}

	
	public void readLine() {
		
		
		new Thread(()->{
		String m="";
		try {
			m = reader.readLine();
			message.msgR(m);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}).start();
		
	}
	
	public void writeLine(String msg) {
		try {
			writer.write(msg+"\n");
			writer.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private Session(Socket so) {
		
		
		
		Platform.runLater(()->{
		try {
			
				writer = new BufferedWriter(new OutputStreamWriter(so.getOutputStream()));
				reader = new BufferedReader(new InputStreamReader(so.getInputStream()));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		});
				
	}
	
	
	public void sendMessage(String msg) throws IOException {
		writer.write(msg+"\n");
		writer.flush();
	}
	
	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

}
