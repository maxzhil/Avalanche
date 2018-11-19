package model;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import model.listeners.GameObjectListener;

public class Avalanche extends GameObject implements Runnable {
	private List<GameObjectListener> listeners = new ArrayList<GameObjectListener>();
	private Earth earth;
	private int value = 1;

	public Avalanche(Earth earth) {
		super(new Point(Integer.parseInt(Resourcer.getString("avalanche.x")),
				earth.getLocation().y + earth.getDimension().height),
				new Dimension(earth.getGameField().getDimension().width,
						Integer.parseInt(Resourcer
								.getString("avalanche.height"))));
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
		getLocation().y = earth.getLocation().y + earth.getDimension().height
				- value;
		getDimension().height++;
	}

	public void addListener(GameObjectListener listener) {
		listeners.add(listener);
	}

	public void notifyListeners() {
		for (GameObjectListener gameObject : listeners) {
			gameObject.update(this);
		}
	}

}
