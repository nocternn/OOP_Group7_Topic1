package algorithms;

import graph.Graph;
import graph.Node;
import graphics.Animation;
import graphics.Brush;

public class MeanShift implements Animation {
	private Graph graph;
	private Brush brush;
	private int bandwidth;

	public MeanShift() { /* Empty constructor */ }
	public MeanShift(int bandwidth, Graph graph, Brush brush) {
		this.bandwidth = bandwidth;
		this.graph = graph;
		this.brush = brush;
	}

	@Override
	public void start() {
		Node node = graph.getUncategorizedNode();
		brush.drawCircle(node.getX(), node.getY(), bandwidth * 10);
		System.out.println("[INFO] Start Mean Shift Clustering animation");
	}

	@Override
	public void pause() {
		System.out.println("[INFO] Pause Mean Shift Clustering animation");
	}
	
	@Override
	public void resume() {
		System.out.println("[INFO] Resume Mean Shift Clustering animation");
	}

	@Override
	public void stop() {
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
	
	

}
