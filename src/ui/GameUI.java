package ui;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.UUID;

import com.google.gson.Gson;

import comm.Session;
import events.MessageListener;
import events.Starting;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Words;

public class GameUI implements Starting, MessageListener{

	// ------------------VENTANA 1
	@FXML
	private AnchorPane anchorPane;

	@FXML
	private Label title;

	@FXML
	private Button stopBtn;

	@FXML
	private TextField nameAnswer;

	@FXML
	private TextField animalAnswer;

	@FXML
	private TextField locationAnswer;

	@FXML
	private TextField objectAnswer;

	// ---------------------------VENTANA2

	@FXML
	private Label ownNameResult;

	@FXML
	private Label opponentNameResult;

	@FXML
	private Label ownAnimalResult;

	@FXML
	private Label opponentAnimalResult;

	@FXML
	private Label ownLocationResult;

	@FXML
	private Label opponentLocationResult;

	@FXML
	private Label ownObjectResult;

	@FXML
	private Label opponentObjectResult;

	@FXML
	private Button finishBtn;

	// ----------------------------------------------------------
	
	private Session s;

	/*
	 * @Override public void run() { try { writer = new BufferedWriter(new
	 * OutputStreamWriter(socket.getOutputStream())); reader = new
	 * BufferedReader(new InputStreamReader(socket.getInputStream()));
	 * 
	 * while(true) { String line = reader.readLine(); System.out.println(line);
	 * listener.onSend(line); }
	 * 
	 * } catch (IOException ex) { ex.printStackTrace(); } }
	 */
	
	private String msg;
	
	@Override
	public void initialize() {
		
			new Thread(()->{	
				Socket socket;
					try {
						socket = new Socket("127.0.0.1", 6000);
						s = Session.getS(socket);
						System.out.println(s);
						
						s.setMessage(this);
						
						
						
						Platform.runLater(()->{
							
								trying();
							
						});
						
						System.out.println("CLIENTE: ");
					} catch (UnknownHostException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					

				
			}).start();

	}
	
			public void trying() {
				s.readLine();
				
		}	
			
	@Override
	public void msgR(String line) {
		System.out.println("Recibido: " + line);
		if (line.startsWith("start")) {
			Platform.runLater(()->{
					///Platform.exit();
					FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Ventana1.fxml"));
			    	fxmlLoader.setController(this);
			    	try {
						Parent p = fxmlLoader.load();
						Scene s = new Scene(p);
						Main.st.setScene(s);
						Main.st.show();
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			    	title.setText("Letra: "+String.valueOf(line.charAt(6)));
			    	
					
			    	
				
			});
			
		} else if (line.equals("finish")) {
			String name = nameAnswer.getText();
			String animal = animalAnswer.getText();
			String country = locationAnswer.getText();
			String thing = objectAnswer.getText();
			if(name.trim().isEmpty() || animal.trim().isEmpty() || country.trim().isEmpty() || thing.trim().isEmpty()) {
				
			}else {
				
				Words w = new Words(name, animal, country, thing);
				Gson gson = new Gson();
				String j = gson.toJson(w);
				
				s.writeLine("*"+j);
			}
			
		}else if(line.startsWith(":")) {
			Platform.runLater(()->{
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Ventana2.fxml"));
	    	fxmlLoader.setController(this);
	    	try {
				Parent p = fxmlLoader.load();
				Scene s = new Scene(p);
				Main.st.setScene(s);
				Main.st.show();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
	    	String[] word = new String[8];
	    	String[] point = new String[7];
	    	boolean pos = false;
	    	int k=0;
	    	for(int i=0;i<word.length;i++) {
	    		pos = false;
				for(int j=k;j<line.length() && !pos;j++) {
					k++;
					if(line.charAt(j)!=':' && line.charAt(j)!=';') {
						word[i]+=line.charAt(j);
					}else if(line.charAt(j)!=':' && line.charAt(j)==';') {
						point[i] = String.valueOf(line.charAt(j));
					}
					else {
						pos = true;
					}
				}
			}
	    	
	    	
	    	ownNameResult.setText(word[1]+"("+point[0]+")");
	    	ownAnimalResult.setText(word[2]+"("+point[1]+")");
	    	ownLocationResult.setText(word[3]+"("+point[2]+")");
	    	ownObjectResult.setText(word[4]+"("+point[3]+")");
	    	
	    	opponentNameResult.setText(word[5]+"("+point[4]+")");
	    	opponentAnimalResult.setText(word[6]+"("+point[5]+")");
	    	opponentLocationResult.setText(word[7]+"("+point[6]+")");
	    	opponentObjectResult.setText(word[8]+"("+point[7]+")");
	    	
	    	
	    	
			});
		}
	}
	
	public void changeScreen() throws IOException{
				
			
		
	}

	@FXML
	public void OnStopBtn(ActionEvent event) throws IOException {
		String name = nameAnswer.getText();
		String animal = animalAnswer.getText();
		String country = locationAnswer.getText();
		String thing = objectAnswer.getText();
		
		System.out.println("Join btn");
		if(name.trim().isEmpty() || animal.trim().isEmpty() || country.trim().isEmpty() || thing.trim().isEmpty()) {
			

		}else {
			Words w = new Words(name, animal, country, thing);
			Gson gson = new Gson();
			String j = gson.toJson(w);
			
			s.writeLine(":"+j);
		}
	}

	@FXML
	public void OnFinishBtn(ActionEvent event) {

	}



}
