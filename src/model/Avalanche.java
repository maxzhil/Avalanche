package model;

import java.util.ArrayList;
import java.util.List;

import model.listeners.GameObjectListener;

public class Avalanche implements Runnable {
	private int x;
	private int y;
	private int width;
	private int height;
	private List<GameObjectListener> listeners = new ArrayList<GameObjectListener>();
	private Earth earth;
	private int value = 1;

	public Avalanche(Earth earth) {
		this.earth = earth;
		this.x = Integer.parseInt(Resourcer.getString("avalanche.x"));
		this.y = earth.getY();
		this.height = Integer.parseInt(Resourcer.getString("avalanche.height"));
		this.width = earth.getWidth();
	}

	@Override
	public void run() {
		while (true) {
			notifyListeners();
			moveY();
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void moveY() {
		value++;
		this.y = earth.getY() - value;
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.height++;

	}

	public void addListener(GameObjectListener listener) {
		listeners.add(listener);
	}

	public void notifyListeners() {
		GameObject info = new GameObject(x, y, width, height);
		for (GameObjectListener object : listeners) {
			object.update(info);
		}
	}

	public void changeY(int value) {
		this.y -= value;
	}

	public int getY() {
		return y;
	}

}
