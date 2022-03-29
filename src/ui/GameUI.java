package ui;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
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

public class GameUI implements Initializable{
	
	//------------------VENTANA 1

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
    
    
    
    
    
    
    //---------------------------VENTANA2
    
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
    
    //----------------------------------------------------------
    
    private Socket socket;
	private String id;
	
	private BufferedReader reader;
	private BufferedWriter writer;
	
	public GameUI(Socket socket) {
		this.socket = socket;
		id = UUID.randomUUID().toString();

	}

	/*@Override
	public void run() {
		try {
			writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			while(true) {
				String line = reader.readLine();
				System.out.println(line);
				listener.onSend(line);
			}
			
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}*/
    

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		new Thread(() -> {
			try {
				writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
				reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				while (true) {
					String line = reader.readLine();
					if(line=="start") {
						FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("VentanaA.fxml"));
						 fxmlLoader.setController(this);
					}
					
					
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}).start();
		
	}
	
	
	@FXML
	public void OnStopBtn(ActionEvent event) {
		String name = nameAnswer.getText();
		String animal = animalAnswer.getText();
		String country = locationAnswer.getText();
		String thing = objectAnswer.getText();
		
		if(name!="" && name!=null &&
				animal!="" && animal!=null &&
				country!="" && country!=null &&
				thing!="" && thing!=null) {
		}
		
    }
    
	@FXML
    public void OnFinishBtn(ActionEvent event) {

    }
    
	
}
