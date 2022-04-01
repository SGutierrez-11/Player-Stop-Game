package ui;

import events.Starting;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{
	
	private static GameUI game;
	private static Starting start;
	
	public static Stage st;

	public static void main(String[] args) {
		game = new GameUI();
		setStart(game);
		launch(args);
		
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		System.out.print("START");
		FXMLLoader loader = new FXMLLoader(Main.class.getResource("Inicio.fxml"));
		
		Parent p = (Parent) loader.load();
		
		Scene scene = new Scene(p);
		st = primaryStage;
		st.setScene(scene);
		st.show();
		start.initialize();
	}
	
	public Main() {
		
	}

	public static void setStart(Starting start) {
		Main.start = start;
	}

}
