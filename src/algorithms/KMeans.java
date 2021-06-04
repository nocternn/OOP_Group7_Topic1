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
	
	public KMeans() {
		//Empty
	}
	
	public KMeans(int centerNum, Graph graph, Brush brush) {
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
		PauseTransition pause = new PauseTransition(Duration.seconds(0.5));
        pause.setOnFinished((pauseEvent) -> {
        	this.timeline.pause();
        });
        pause.play();
		System.out.println("[INFO] Next step in K-Means Clustering animation");
	}
	
	public void KMeansClustering(Graph graph, int centerNum) {
		ArrayList<Node> categorizedNodes = new ArrayList<>();
		ArrayList<Node> centers = new ArrayList<>();
		double timeBetweenFrames = 0;
		
		this.timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(timeBetweenFrames), (event) -> {
			this.brush.clear();
		}));

		for (Node node: graph.getCategorizedNodes()) {
			categorizedNodes.add(new Node(node.getX(), node.getY(), Color.BLACK)); //Start by set all color of nodes to black (which is uncategorized yet)
			this.timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(timeBetweenFrames), (event) -> {
					this.brush.drawPoint(node.getX(), node.getY(), Color.BLACK);
			}));
		}

		// Generate random centroids
		Random rand = new Random();
		for (int i = 0; i < centerNum; i++) {
			// Create a new center at random location with Color at index i from NodeCategories
			Node node = new Node(rand.nextDouble()*1000, rand.nextDouble()*650, NodeCategories.getColor(i));
			centers.add(node);	// Add that center to ArrayList
			this.timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(timeBetweenFrames), (event) -> {
				this.brush.drawCenter(node.getX(), node.getY(), node.getCategory());
			}));
		}
		
		timeBetweenFrames = 1;

		double SSE = Double.MAX_VALUE;
		for (int iteration = 0; iteration < 100; iteration++) {
			// Assign each node to the nearest centroid
			this.timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(timeBetweenFrames), (event) -> {
				this.brush.clear();
			}));
			for (Node node: categorizedNodes) {
				double minDist = Double.MAX_VALUE;			// minDist stores minimum distance from one node to center
				Node nearest = null;
				for (Node center: centers) {
					double dist = distance(node, center);	// Calculate distance from one node to center
					if (dist < minDist) {
						nearest = center;					// Assign color of nearest center to that node
						minDist = dist;						// Re-assign minDist if current distance < minDist
					}
					this.timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(timeBetweenFrames), (event) -> {
						this.brush.drawCenter(center.getX(), center.getY(), center.getCategory());
					}));
				}
				node.setCategory(nearest.getCategory());
				this.timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(timeBetweenFrames), (event) -> {
					this.brush.drawPoint(node.getX(), node.getY(), node.getCategory());
				}));
			}
		
			// Shift centroids to average of their clusters
			this.timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(timeBetweenFrames + 1), (event) -> {
				this.brush.clear();
			}));
			int count;
			long sumx, sumy;
			for (Node center: centers) {
				count = 0;										// Count number of node having same color as center i
				sumx = 0; sumy = 0;								// Sum of coordinates in x and y axis
				for (Node node: categorizedNodes) {
					if (center.getCategory().equals(node.getCategory())) {	// Check if center and node have same color
						count++;
						sumx += node.getX();
						sumy += node.getY();
					}
					this.timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(timeBetweenFrames + 1), (event) -> {
						this.brush.drawPoint(node.getX(), node.getY(), node.getCategory());
					}));
				}
				if (count != 0) {
					center = new Node(sumx/count, sumy/count, center.getCategory());	// Update centers that has nodes with same color
				} else {
					center = new Node(center.getX(), center.getY(), center.getCategory());		// Do not change coordinate of center has no node with same color
				}
				final Node centerCopy = center;
				this.timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(timeBetweenFrames + 1), (event) -> {
					this.brush.drawCenter(centerCopy.getX(), centerCopy.getY(), centerCopy.getCategory());
				}));
			}
			
			// Check to continue
			double newSSE = 0.0;
	        for(Node center : centers) {
	        	for(Node node : categorizedNodes){
	                if(center.getCategory().equals(node.getCategory())){
	                	newSSE += distance(center, node);
	                }
	            }
	        }
            if(SSE - newSSE <= 0.00005){
                break;
            }
            SSE = newSSE;
			
			timeBetweenFrames += 2;
		}
	}
	
	private double distance(Node source, Node destination) {
		//Calculate distance of 2 point: source and destination
		double squareX = Math.pow(destination.getX() - source.getX(), 2);	// Calculate x^2
		double squareY = Math.pow(destination.getY() - source.getY(), 2);	// Calculate y^2
		return Math.sqrt(squareX + squareY);								// Distance = sqrt(x^2+y^2)
	}
}
