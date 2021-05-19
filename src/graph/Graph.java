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
	 * @param number
	 * @param maxX
	 * @param maxY
	 * @return true
	 */
	public boolean generate(int number, int maxX, int maxY) {
		Random rand = new Random();
		for (int i = 0; i < number; i++) {
			int x = rand.nextInt(maxX);
			int y = rand.nextInt(maxY);
			add(new Node(x, y, Integer.toString(i)));
		}
		return true;
	}

	class Node {
		int x, y;
		String name = "";

		public Node(int x, int y) {
			this.x = x;
			this.y = y;
		}
		public Node(int x, int y, String name) {
			this.x = x;
			this.y = y;
			this.name = name;
		}
	}
}
