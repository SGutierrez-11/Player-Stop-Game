package ui;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.UUID;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class GameUI implements Initializable {

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

	private BufferedReader reader;
	private BufferedWriter writer;

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

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		new Thread(() -> {
			try {
				Socket socket = new Socket("192.168.0.103", 6000);

				writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
				BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

				while (true) {
					String line = reader.readLine();
					System.out.println("Recibido: " + line);
					if (line.equals("start")) {
						Platform.runLater(()->{
								title.setText("Letra: "+String.valueOf(line.charAt(5)));
								FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Ventana1.fxml"));
						    	fxmlLoader.setController(this);
						    	try {
									Parent p = fxmlLoader.load();
									anchorPane.getChildren().add(p);
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
						    	
								
						    	
							
						});
						
					} else if (line.equals("finish")) {
						String name = nameAnswer.getText();
						String animal = animalAnswer.getText();
						String country = locationAnswer.getText();
						String thing = objectAnswer.getText();

						writer.write("*" + name + ":" + animal + ":" + country + ":" + thing + "\n");
						writer.flush();
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}).start();

	}
	
	public void changeScreen() throws IOException{
				
			
		
	}

	@FXML
	public void OnStopBtn(ActionEvent event) throws IOException {
		String name = nameAnswer.getText();
		String animal = animalAnswer.getText();
		String country = locationAnswer.getText();
		String thing = objectAnswer.getText();

		if (name != "" && name != null && animal != "" && animal != null && country != "" && country != null
				&& thing != "" && thing != null) {
			writer.write(":" + name + ":" + animal + ":" + country + ":" + thing + "\n");
			writer.flush();

		}

	}

	@FXML
	public void OnFinishBtn(ActionEvent event) {

	}

}
