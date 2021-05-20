package graphics;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Brush {
	GraphicsContext graphicsContext;
	
	public Brush(GraphicsContext graphicsContext) {
		this.graphicsContext = graphicsContext;
		graphicsContext.setFill(Color.BLACK);
		graphicsContext.setStroke(Color.BLACK);
		graphicsContext.setLineWidth(1);
	}
	
	/**
	 * Draw a non-categorized point on the canvas at (x, y)
	 * with width = height = 5 and arc width = arc height = 1.
	 * @param x
	 * @param y
	 */
	public void drawPoint(double x, double y) {
		graphicsContext.fillRoundRect(x, y, 5, 5, 1, 1);
	}
}
