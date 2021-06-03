package algorithms;

import graphics.Animation;

public class KNN implements Animation {

	@Override
	public void start() {
		System.out.println("[INFO] Start K-Nearest Neighbour animation");
	}

	@Override
	public void pause() {
		System.out.println("[INFO] Pause K-Nearest Neighbour animation");
	}
	
	@Override
	public void resume() {
		System.out.println("[INFO] Resume K-Nearest Neighbour animation");
	}

	@Override
	public void stop() {
		System.out.println("[INFO] Stop K-Nearest Neighbour animation");
	}

	@Override
	public void previous() {
		System.out.println("[INFO] Previous step in K-Nearest Neighbour animation");
	}

	@Override
	public void next() {
		System.out.println("[INFO] Next step in K-Nearest Neighbour animation");
	}


}
