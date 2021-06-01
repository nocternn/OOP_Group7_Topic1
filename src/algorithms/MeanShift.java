package algorithms;

import graph.Graph;
import graph.Node;
import graphics.Animation;
import graphics.Brush;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class MeanShift implements Animation {
	private static double EPSILON = 0.00005;
	private Graph graph;
	private Brush brush;
	private int bandwidth;
	private Timeline timeline;
	private double timeBetweenFrames = 1;
	
	public MeanShift() { /* Empty constructor */ }
	public MeanShift(int bandwidth, Graph graph, Brush brush) {
		this.bandwidth = bandwidth;
		this.graph = graph;
		this.brush = brush;
		this.timeline = new Timeline();
	}

	@Override
	public void start() {
		drawStep(graph.getUncategorizedNode());
		// Shift the uncategorized node towards a center
		Node currentNode = null, newNode = null;
		do {
			currentNode = graph.getUncategorizedNode();
			double shiftX = 0, shiftY = 0, scaleFactor = 0;
			for (Node originalNode : graph.getCategorizedNodes()) {
				// Calculate the distance
				double distance = distance(currentNode, originalNode);
				if (distance <= this.bandwidth) {
					double weight = kernel(distance, this.bandwidth);
					if (weight > 0) {
						// Calculate the numerator
						shiftX += originalNode.getX() * weight;
						shiftY += originalNode.getY() * weight;
						// Calculate the denominator
						scaleFactor += weight;
					}
				}
			}
			shiftX = shiftX / scaleFactor;
			shiftY = shiftY / scaleFactor;
			// Set the new shifted point
			final Node node = new Node(shiftX, shiftY, Color.BLACK);
			graph.setUncategorizedNode(node);
			// Set animation
			this.timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(timeBetweenFrames), (event) -> {
				drawStep(node);
				timeline.pause();
				PauseTransition pause = new PauseTransition(Duration.seconds(0.5));
	            pause.setOnFinished((pauseEvent) -> {
	            	brush.clear();
		            drawStep(node);
	            	timeline.play();
	            });
	            pause.play();
			}));
			// Set new node variable
			newNode = node;
			timeBetweenFrames += 1;
		} while (distance(currentNode, newNode) > EPSILON);
		// Start animation
		this.timeline.play();
		// Log
		System.out.println("[INFO] Start Mean Shift Clustering animation");
	}

	@Override
	public void pause() {
		this.timeline.pause();
		System.out.println("[INFO] Pause Mean Shift Clustering animation");
	}
	
	@Override
	public void resume() {
		this.timeline.play();
		System.out.println("[INFO] Resume Mean Shift Clustering animation");
	}

	@Override
	public void stop() {
		this.timeline.stop();
		System.out.println("[INFO] Stop Mean Shift Clustering animation");
	}

	@Override
	public void previous() {
		System.out.println("[INFO] Previous step in Mean Shift Clustering animation");
	}

	@Override
	public void next() {
		System.out.println("[INFO] Next step in Mean Shift Clustering animation");
	}
	
	/**
	 * Calculate the Euclidean distance between two nodes.
	 * @param source
	 * @param destination
	 * @return distance
	 */
	private double distance(Node source, Node destination) {
		double squareX = Math.pow(destination.getX() - source.getX(), 2);	// Calculate x^2
		double squareY = Math.pow(destination.getY() - source.getY(), 2);	// Calculate y^2
		return Math.sqrt(squareX + squareY);
	}
	
	/**
	 * Calculate the kernel using the Gaussian kernel function.
	 * @param distance
	 * @param bandwidth
	 * @return kernel
	 */
	private double kernel(double distance, int bandwidth) {
		double squareDistance = Math.pow(distance, 2);
		double squareBandwidth = Math.pow(bandwidth, 2);
		return Math.pow(Math.E, -0.5 * (squareDistance / squareBandwidth));
	}
	
	private void drawStep(Node node) {
		// Draw current step on top of previous step
		brush.drawPoint(node.getX(), node.getY(), Color.BLACK);
		brush.drawCircle(node.getX(), node.getY(), this.bandwidth);
	}
}
