package application;
	
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.fxml.FXMLLoader;

public class Main extends Application {
	static Scene scene;
	@Override
	public void start(Stage primaryStage) {
		try {
			TabPane root = (TabPane)FXMLLoader.load(getClass().getResource("Sample.fxml"));
			scene = new Scene(root, 1280, 720);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void getxy() {
	}
	
	public static void main(String[] args) {
		launch(args);
		PointerInfo info = MouseInfo.getPointerInfo();
		Point p = info.getLocation();
		Text mouseCoord = (Text) scene.lookup("mouseCoordinate");
		AnchorPane canvas = (AnchorPane) scene.lookup("canvas");
		canvas.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>(){
			public void handle(MouseEvent e) {
				mouseCoord.setText(p.x + ", " + p.y);
			}
		});
	}
	
	
}
