package model;

import java.util.ArrayList;
import java.util.List;

public class Earth implements Runnable {
	private int x;
	private int y;
	private int width;
	private int height;
	private List<GameObjectListener> listeners = new ArrayList<GameObjectListener>();
	GameField gameField;

	public Earth(GameField gameField) {
		this.gameField = gameField;
		this.x = 0;
		this.y = 360;
		this.height = 240;
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
			block.setY(block.getY() - value);
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
		GameObjectInfo info = new GameObjectInfo(x, y, width, height);
		for (GameObjectListener object : listeners) {
			object.update(info);
		}
	}

}
