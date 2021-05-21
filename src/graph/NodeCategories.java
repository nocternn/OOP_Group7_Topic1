package graph;

import java.util.Random;
import javafx.scene.paint.Color;

abstract class NodeCategories {
	private static Color[] categories = {
			Color.CYAN,
			Color.MAGENTA,
			Color.ORANGE,
			Color.PINK,
			Color.RED,
			Color.GREEN
	};
	
	/**
	 * Get a random category color.
	 * @return
	 */
	public static Color getRandom() {
		Random rand = new Random();
		return categories[rand.nextInt(categories.length)];
	}
}
