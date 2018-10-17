package model;

import java.util.ArrayList;
import java.util.List;

import model.listeners.GameObjectListener;

public class Earth implements Runnable {
	private int x;
	private int y;
	private int width;
	private int height;
	private List<GameObjectListener> listeners = new ArrayList<GameObjectListener>();
	private GameField gameField;

	public Earth(GameField gameField) {
		this.gameField = gameField;
		this.x = Integer.parseInt(Resourcer.getString("earth.x"));
		this.y = Integer.parseInt(Resourcer.getString("earth.y"));
		this.height = Integer.parseInt(Resourcer.getString("earth.height"));
		this.width = gameField.getWidth();
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void changeY(int value) {
		this.y -= value;
		for (Block block : gameField.getBlocks()) {
			if (!block.isDropping()) {
				block.setY(block.getY() - value);
			}
		}
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	@Override
	public void run() {
		while (true) {
			notifyListeners();
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void addListener(GameObjectListener listener) {
		listeners.add(listener);
	}

	private void notifyListeners() {
		GameObject info = new GameObject(x, y, width, height);
		for (GameObjectListener object : listeners) {
			object.update(info);
		}
	}
}
