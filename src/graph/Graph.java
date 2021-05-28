package graph;

import java.util.ArrayList;
import java.util.Random;

import javafx.scene.paint.Color;

public class Graph {
	private ArrayList<Node> categorizedNodes;
	private ArrayList<Node> uncategorizedNodes;

	// Constructor
	public Graph() {
		this.categorizedNodes  = new ArrayList<>();
	}
	
	/**
	 * Get a list of all nodes in the graph.
	 * @return nodes
	 */
	public ArrayList<Node> getNodes() {
		ArrayList<Node> nodes = new ArrayList<>();
		nodes.addAll(this.categorizedNodes);
		nodes.addAll(this.uncategorizedNodes);
		return nodes;
	}
	
	/**
	 * Get a random node.
	 * @return random node
	 */
	public Node getRandomNode() {
		Random rand = new Random();
		return this.categorizedNodes.get(rand.nextInt(categorizedNodes.size()));
	}

	/**
	 * Add a new node to the current graph.
	 * @param node
	 * @return true - if a new node is added.
	 */
	public boolean add(Node node) {
		if (node.getCategory() == Color.BLACK)
			return this.uncategorizedNodes.add(node);
		return this.categorizedNodes.add(node);
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
