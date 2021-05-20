package graph;

import java.util.ArrayList;
import java.util.Random;

public class Graph {
	private ArrayList<Node> nodes;

	// Constructor
	public Graph() {
		this.nodes  = new ArrayList<>();
	}

	/**
	 * Add a new node to the current graph.
	 * @param node
	 * @return true - if a new node is added.
	 */
	public boolean add(Node node) {
		return this.nodes.add(node);
	}

	/**
	 * Generate a random graph with 'number' nodes.
	 * The name of each node is its id in generation order.
	 * Each node's category is randomly selected.
	 * @param number
	 * @param maxX
	 * @param maxY
	 * @return true
	 */
	public boolean generate(int number, int maxX, int maxY) {
		Random rand = new Random();
		for (int i = 0; i < number; i++) {
			double x = rand.nextDouble() * maxX;
			double y = rand.nextDouble() * maxY;
			add(new Node(x, y, Integer.toString(i), NodeCategories.getRandom()));
		}
		return true;
	}
}
