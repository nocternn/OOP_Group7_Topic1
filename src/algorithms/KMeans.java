package algorithms;

import graphics.Animation;

public class KMeans implements Animation {

	@Override
	public void start() {
		System.out.println("[INFO] Start K-Means Clustering animation");
	}

	@Override
	public void pause() {
		System.out.println("[INFO] Pause K-Means Clustering animation");
	}

	@Override
	public void resume() {
		System.out.println("[INFO] Resume K-Means Clustering animation");
	}
	
	@Override
	public void stop() {
		System.out.println("[INFO] Stop K-Means Clustering animation");
	}

	@Override
	public void previous() {
		System.out.println("[INFO] Previous step in K-Means Clustering animation");
	}

	@Override
	public void next() {
		System.out.println("[INFO] Next step in K-Means Clustering animation");
	}

}
