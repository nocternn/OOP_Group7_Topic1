package graph;

import javafx.scene.paint.Color;

public class Node {
	double x, y;
	String name = "";
	Color category = Color.BLACK;

	public Node(double x, double y) {
		this.x = x;
		this.y = y;
	}
	public Node(double x, double y, Color category) {
		this.x = x;
		this.y = y;
		this.category = category;
	}
	public Node(double x, double y, String name) {
		this.x = x;
		this.y = y;
		this.name = name;
	}
	public Node(double x, double y, String name, Color color) {
		this.x = x;
		this.y = y;
		this.name = name;
		this.category = color;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public void setCategory(Color category) {
		this.category = category;
	}
	
	public Color getCategory() {
		return this.category;
	}
}