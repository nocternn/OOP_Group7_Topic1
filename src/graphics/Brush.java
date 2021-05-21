package graphics;

import java.util.ArrayList;

import graph.Node;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Brush {
	private GraphicsContext graphicsContext;
	
	public Brush(GraphicsContext graphicsContext) {
		this.graphicsContext = graphicsContext;
		graphicsContext.setLineWidth(1);
	}
	
	/**
	 * Draw the given nodes list.
	 * @param nodes
	 */
	public void drawGraph(ArrayList<Node> nodes) {
		for (Node node : nodes) {
			drawPoint(node.getX(), node.getY(), node.getCategory());
		}
	}
	
	/**
	 * Draw a non-categorized point on the canvas at (x, y)
	 * with width = height = 5 and arc width = arc height = 1.
	 * @param x
	 * @param y
	 */
	public void drawPoint(double x, double y, Color color) {
		graphicsContext.setFill(color);
		graphicsContext.fillRoundRect(x, y, 5, 5, 1, 1);
	}
	
	public void drawCircle(double x, double y, int radius) {
		graphicsContext.setStroke(Color.BLUE);
		graphicsContext.strokeOval(x - radius, y - radius, radius * 2, radius * 2);
	}
}
