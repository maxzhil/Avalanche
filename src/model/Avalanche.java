package model;

import java.util.ArrayList;
import java.util.List;

import model.listeners.GameObjectListener;

public class Avalanche extends GameObject implements Runnable {
	private List<GameObjectListener> listeners = new ArrayList<GameObjectListener>();
	private Earth earth;
	private int value = 1;

	public Avalanche(Earth earth) {
		super(Integer.parseInt(Resourcer.getString("avalanche.x")), earth
				.getY() + earth.getHeight(), earth.getGameField().getWidth(),
				Integer.parseInt(Resourcer.getString("avalanche.height")));
		this.earth = earth;
	}

	@Override
	public void run() {
		while (true) {
			notifyListeners();
			moveY();
			try {
				Thread.sleep(150);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}

	private void moveY() {
		value++;
		setY(earth.getY() + earth.getHeight() - value);
		setHeight(getHeight() + 1);
	}

	public void addListener(GameObjectListener listener) {
		listeners.add(listener);
	}

	public void notifyListeners() {
		for (GameObjectListener object : listeners) {
			object.update(this);
		}
	}

}
