package application;

import algorithms.KMeans;
import algorithms.KNN;
import algorithms.MeanShift;
import graph.Graph;
import graph.Node;
import graphics.Animation;
import graphics.Brush;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.fxml.FXMLLoader;

public class Main extends Application {
	private static Graph graph = null;
	private static Scene scene = null;
	private Animation currentAnimation = null;

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
					graph.add(new Node(event.getX(), event.getY()));
					brush.drawPoint(event.getX(), event.getY(), Color.BLACK);
				}
			});
			
			// random graph
			graph = new Graph();
			graph.generate(100, 1000, 650);
			brush.drawGraph(graph.getNodes());
			
			// Implementation of MeanShift button function
			Button btnKNN = (Button) scene.lookup("#btnKNN");
			btnKNN.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					// Dialog config
					TextInputDialog getBandwidthDialog = new TextInputDialog();
					getBandwidthDialog.setTitle("Get K-value Dialog");
					getBandwidthDialog.setHeaderText("Enter K-value (must be an integer)");
					getBandwidthDialog.show();
					// Input check: disable OK button if input is invalid
					Button okButton = (Button) getBandwidthDialog.getDialogPane().lookupButton(ButtonType.OK);
					TextField inputField = getBandwidthDialog.getEditor();
					BooleanBinding isValid = Bindings.createBooleanBinding(() -> !isValid(inputField.getText()), inputField.textProperty());
					okButton.disableProperty().bind(isValid);
					// Set action on OK button click - change the current animation to animate Mean Shift algorithm
					okButton.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent arg0) {
							currentAnimation = new KNN();
						}
					});
				}
				/**
				 * Check whether the dialog input is an integer or not.
				 * @param text
				 * @return true - if text is integer
				 */
				private boolean isValid(String text) {
					try {
						Integer.parseInt(text);
					} catch (Exception e) {
						return false;
					}
					return true;
				}
			});
			// Implementation of MeanShift button function
			Button btnKMeans = (Button) scene.lookup("#btnKMeans");
			btnKMeans.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					// Dialog config
					TextInputDialog getBandwidthDialog = new TextInputDialog();
					getBandwidthDialog.setTitle("Get K-value Dialog");
					getBandwidthDialog.setHeaderText("Enter K-value (must be an integer)");
					getBandwidthDialog.show();
					// Input check: disable OK button if input is invalid
					Button okButton = (Button) getBandwidthDialog.getDialogPane().lookupButton(ButtonType.OK);
					TextField inputField = getBandwidthDialog.getEditor();
					BooleanBinding isValid = Bindings.createBooleanBinding(() -> !isValid(inputField.getText()), inputField.textProperty());
					okButton.disableProperty().bind(isValid);
					// Set action on OK button click - change the current animation to animate Mean Shift algorithm
					okButton.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent arg0) {
							currentAnimation = new KMeans();
						}
					});
				}
				/**
				 * Check whether the dialog input is an integer or not.
				 * @param text
				 * @return true - if text is integer
				 */
				private boolean isValid(String text) {
					try {
						Integer.parseInt(text);
					} catch (Exception e) {
						return false;
					}
					return true;
				}
			});
			// Implementation of MeanShift button function
			Button btnMeanShift = (Button) scene.lookup("#btnMeanShift");
			btnMeanShift.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					// Dialog config
					TextInputDialog getBandwidthDialog = new TextInputDialog();
					getBandwidthDialog.setTitle("Get Bandwidth Dialog");
					getBandwidthDialog.setHeaderText("Enter bandwidth value (must be an integer)");
					getBandwidthDialog.show();
					// Input check: disable OK button if input is invalid
					Button okButton = (Button) getBandwidthDialog.getDialogPane().lookupButton(ButtonType.OK);
					TextField inputField = getBandwidthDialog.getEditor();
					BooleanBinding isValid = Bindings.createBooleanBinding(() -> !isValid(inputField.getText()), inputField.textProperty());
					okButton.disableProperty().bind(isValid);
					// Set action on OK button click - change the current animation to animate Mean Shift algorithm
					okButton.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent arg0) {
							currentAnimation = new MeanShift(Integer.parseInt(inputField.getText()), graph, brush);
						}
					});
				}
				/**
				 * Check whether the dialog input is an integer or not.
				 * @param text
				 * @return true - if text is integer
				 */
				private boolean isValid(String text) {
					try {
						Integer.parseInt(text);
					} catch (Exception e) {
						return false;
					}
					return true;
				}
			});
			
			// Implementation of Previous Step button function
			Button btnPreviousStep = (Button) scene.lookup("#btnPreviousStep");
			btnPreviousStep.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					if (currentAnimation != null) {
						currentAnimation.previous();
					}
				}
			});
			// Implementation of Next Step button function
			Button btnNexStep = (Button) scene.lookup("#btnNextStep");
			btnNexStep.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					if (currentAnimation != null) {
						currentAnimation.next();
					}
				}
			});
			Button btnStart = (Button) scene.lookup("#btnStart");
			Button btnPause = (Button) scene.lookup("#btnPause");
			Button btnResume = (Button) scene.lookup("#btnResume");
			Button btnStop = (Button) scene.lookup("#btnStop");
			// Implementation of Start button function
			btnStart.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					if (currentAnimation != null) {
						currentAnimation.start();
						btnPause.setVisible(true);
						btnStop.setVisible(true);
						btnStart.setVisible(false);
					}
				}
			});
			// Implementation of Pause button function
			btnPause.setVisible(false);
			btnPause.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					currentAnimation.pause();
					btnResume.setVisible(true);
					btnPause.setVisible(false);
				}
			});
			// Implementation of Resume button function
			btnResume.setVisible(false);
			btnResume.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					currentAnimation.resume();
					btnPause.setVisible(true);
					btnResume.setVisible(false);
				}
			});
			// Implementation of Stop Step button function
			btnStop.setVisible(false);
			btnStop.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					currentAnimation.stop();
					btnStart.setVisible(true);
					btnPause.setVisible(false);
					btnResume.setVisible(false);
					btnStop.setVisible(false);
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
