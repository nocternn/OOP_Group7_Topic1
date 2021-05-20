package graph;

import java.util.Random;
import javafx.scene.paint.Color;

abstract class NodeCategories {
	private static Color[] categories = {
			Color.BLUE,
			Color.CYAN,
			Color.GRAY,
			Color.GREEN,
			Color.MAGENTA,
			Color.ORANGE,
			Color.PINK,
			Color.RED,
			Color.YELLOW,
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
