package algorithms;

import graph.Node;
import java.util.Random;
import java.util.ArrayList;
import graph.Graph;
import graph.NodeCategories;
import graphics.Animation;
import graphics.Brush;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class KMeans implements Animation {
	private Brush brush;
	private Timeline timeline;
	private ArrayList<Node> categorizedNodes;
	private ArrayList<Node> centers = new ArrayList<>();
	private ArrayList<Node> newcenters = new ArrayList<>();
	
	public KMeans() {
		//Empty
	}
	
	public KMeans(int centerNum, Graph graph, Brush brush) {
		this.categorizedNodes = graph.getCategorizedNodes();
		for (Node i: categorizedNodes) {
			i.setCategory(Color.BLACK);				//Start by set all color of nodes to black (which is uncategorized yet)
		}
		brush.drawGraph(this.categorizedNodes);
		this.timeline = new Timeline();
		this.brush = brush;
		KMeansClustering(graph, centerNum);
	}

	@Override
	
	public void start() {
		this.timeline.play();
		System.out.println("[INFO] Start K-Means Clustering animation");
	}

	@Override
	public void pause() {
		this.timeline.pause();
		System.out.println("[INFO] Pause K-Means Clustering animation");
	}

	@Override
	public void resume() {
		this.timeline.play();
		System.out.println("[INFO] Resume K-Means Clustering animation");
	}
	
	@Override
	public void stop() {
		this.timeline.stop();
		this.brush.clear();
		System.out.println("[INFO] Stop K-Means Clustering animation");
	}

	@Override
	public void previous() {
		this.timeline.pause();
		double previousTime = Math.floor(this.timeline.getCurrentTime().toSeconds()) - 1;
		if (previousTime < 0) {
			this.brush.clear();
		} else {
			this.timeline.playFrom(Duration.seconds(previousTime));
			PauseTransition pause = new PauseTransition(Duration.seconds(1));
	        pause.setOnFinished((pauseEvent) -> {
	        	this.timeline.pause();
	        });
	        pause.play();
		}
		System.out.println("[INFO] Previous step in K-Means Clustering animation");
	}

	@Override
	public void next() {
		this.timeline.pause();
		double nextTime = Math.ceil(this.timeline.getCurrentTime().toSeconds());
		this.timeline.playFrom(Duration.seconds(nextTime));
		PauseTransition pause = new PauseTransition(Duration.seconds(1));
        pause.setOnFinished((pauseEvent) -> {
        	this.timeline.pause();
        });
        pause.play();
		System.out.println("[INFO] Next step in K-Means Clustering animation");
	}
	
	public void KMeansClustering(Graph graph, int centerNum) {
		double timeBetweenFrames = 0;
		Boolean check = true;
		
		KMeansInitCenters(centerNum);
		do {
			KMeansPredictLabels(categorizedNodes, centers);
			this.timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(timeBetweenFrames), (event) -> {
				System.out.println("[Step] Predict");
				this.brush.clear();
				this.brush.drawGraph(categorizedNodes);					// Display all nodes with color on canvas
				this.brush.drawGraphCenters(centers);
				timeline.pause();
				PauseTransition pause = new PauseTransition(Duration.seconds(0.5));
		        pause.setOnFinished((pauseEvent) -> {
		        	this.timeline.play();
		        });
		        pause.play();
			}));
			
			KMeansUpdateCenters(categorizedNodes, centerNum, centers);			
//			brush.clear();																// Clear canvas with old centers
//			brush.drawGraph(categorizedNodes);											// Re-draw nodes with color
//			brush.drawGraphCenters(newcenters);											// Draw the updated centers
			
			
			this.timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(timeBetweenFrames+1), (event) -> {
				System.out.println("[Step] Update");
				this.brush.clear();																// Clear canvas with old centers
				this.brush.drawGraph(categorizedNodes);											// Re-draw nodes with color
				this.brush.drawGraphCenters(this.newcenters);									// Draw the updated centers
				timeline.pause();
				PauseTransition pause = new PauseTransition(Duration.seconds(0.5));
		        pause.setOnFinished((pauseEvent) -> {
		        	this.timeline.play();
		        });
		        pause.play();
		        System.out.println("Done Step");
			}));
			
			check = KMeansConvergedCheck(centers, newcenters, centerNum) == true && timeBetweenFrames <= 100;
			
			centers.clear();
			for (Node i: newcenters) {
				centers.add(i);
			}
			
			timeBetweenFrames += 2;
			

			System.out.println("[INFO] Frame Rendered = " + timeBetweenFrames);
		}
		//Until 100 loops or updated distance between old and new centers is small enough
		while (check);
	}
	
	// Create new random Centers
	public void KMeansInitCenters(int centerNum) {
		Random rand = new Random();
		for (int i=0; i<centerNum; i++) {
			// Create a new center at random location with Color at index i from NodeCategories
			Node node = new Node(rand.nextDouble()*1000, rand.nextDouble()*650, NodeCategories.getColor(i));
//			System.out.println("[INFO] Initiate Center no." + i + ": " + node.getX() + " " + node.getY());
			centers.add(node);					// Add that center to ArrayList
		}
		brush.drawGraphCenters(centers);		// Display centers on canvas
	}
	
	// Check nearest center of nodes
	public void KMeansPredictLabels(ArrayList<Node> categorizedNodes, ArrayList<Node> centers) {
		for (Node i: categorizedNodes) {
			Double minDist = Double.MAX_VALUE;				// minDist stores minimum distance from one node to center
			for (Node j: centers) {
				double dist = distance(j, i);				// Calculate distance from one node to center
				if (dist < minDist) {
					i.setCategory(j.getCategory());			// Assign color of nearest center to that node
					minDist = dist;							// Re-assign minDist if current distance < minDist
				}
			}
		}
	}
	
	// Update new center position
	public void KMeansUpdateCenters(ArrayList<Node> categorizedNodes, int centerNum, ArrayList<Node> centers) {
		int count;
		long sumx, sumy;
		
		newcenters.clear();
		for (Node i: centers) {
			count = 0;										// Count number of node having same color as center i
			sumx = 0; sumy = 0;								// Sum of coordinates in x and y axis
			for (Node j: categorizedNodes) {
				if (j.getCategory() == i.getCategory()) {	// Check if center and node have same color
					count++;
					sumx += j.getX();
					sumy += j.getY();
				}
			}
			if (count != 0) {
				newcenters.add(new Node(sumx/count, sumy/count, i.getCategory()));	// Update centers that has nodes with same color
			} else {
				newcenters.add(new Node(i.getX(), i.getY(), i.getCategory()));		// Do not change coordinate of center has no node with same color
			}
		}
	}
	
	private boolean KMeansConvergedCheck(ArrayList<Node> centers, ArrayList<Node> newcenters, int centerNum) {
		double distmax = 0;				//calculate max distance between pre-update and after-update pair of centers
		
		for (int i = 0; i < centerNum; i++) {
			distmax = Math.max(distmax, distance(centers.get(i), newcenters.get(i)));		
			
//			System.out.println("Distance: " + distance(centers.get(i), newcenters.get(i)));			
//			System.out.println("Center: " + centers.get(i).getX() + " " + centers.get(i).getY() + " - New Center:" + newcenters.get(i).getX() + " " + newcenters.get(i).getY());
			
			if (distmax > 2) {
				return true;			//true means "Need to recalculate"
			}
		}
		return false;					//false means "Distance small enough"
	}
	
	private double distance(Node source, Node destination) {
		//Calculate distance of 2 point: source and destination
		double squareX = Math.pow(destination.getX() - source.getX(), 2);	// Calculate x^2
		double squareY = Math.pow(destination.getY() - source.getY(), 2);	// Calculate y^2
		return Math.sqrt(squareX + squareY);								// Distance = sqrt(x^2+y^2)
	}
}
