package application;

import graph.Graph;
import graph.Node;
import graphics.Brush;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.fxml.FXMLLoader;

public class Main extends Application {
	private Graph graph = new Graph();
	static Scene scene;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			TabPane root = (TabPane)FXMLLoader.load(getClass().getResource("Sample.fxml"));
			scene = new Scene(root, 1280, 720);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			primaryStage.setScene(scene);
			primaryStage.setTitle("Algorithm Simulator (Group 7 - Topic 1)");
			primaryStage.setResizable(false);
			primaryStage.show();
			
			Canvas canvas = (Canvas) scene.lookup("#canvas");
			final Brush brush = new Brush(canvas.getGraphicsContext2D());
			
			canvas.setOnMouseClicked(new EventHandler<MouseEvent>() {
				public void handle(MouseEvent event) {
					Text mouseCoord = (Text) scene.lookup("#mouseCoordinate");
					mouseCoord.setText(event.getX() + " - " + event.getY());
					graph.add(new Node(event.getX(), event.getY()));
					brush.drawPoint(event.getX(), event.getY());
				}
			});
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
