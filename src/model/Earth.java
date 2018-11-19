package model;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import model.listeners.GameObjectListener;

public class Earth extends GameObject implements Runnable {
	private List<GameObjectListener> listeners = new ArrayList<GameObjectListener>();
	private GameField gameField;

	public Earth(GameField gameField) {
		super(new Point(Integer.parseInt(Resourcer.getString("earth.x")),
				Integer.parseInt(Resourcer.getString("earth.y"))),
				new Dimension(gameField.getDimension().width
						- Integer.parseInt(Resourcer
								.getString("earth.value.indent")),
						Integer.parseInt(Resourcer.getString("earth.height"))));
		this.gameField = gameField;
	}

	public void moveY(int value) {
		for (Block block : gameField.getBlocks()) {
			block.getLocation().y -= value;
		}
		changeY(value);
	}

	@Override
	public void run() {
		while (true) {
			notifyListeners();
			try {
				Thread.sleep(15);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void addListener(GameObjectListener listener) {
		listeners.add(listener);
	}

	private void notifyListeners() {
		for (GameObjectListener object : listeners) {
			object.update(this);
		}
	}

	public GameField getGameField() {
		return gameField;
	}
}
