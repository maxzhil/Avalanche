package model;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import model.GameField;
import model.GameObject;
import model.listeners.DeleteBlockListener;
import model.listeners.GameObjectListener;

public class Block extends GameObject implements Runnable {
	private boolean isAlive = true;
	private boolean isDropping = true;
	private Color color;
	private int fallingSpeed;
	private GameField gameField;
	private Earth earth;
	private List<GameObjectListener> listeners = new ArrayList<GameObjectListener>();
	private List<DeleteBlockListener> deleteListeners = new ArrayList<DeleteBlockListener>();

	public Block(Point point, Dimension dimension, GameField gameField,
			Earth earth, int fallingSpeed) {
		super(point, dimension);
		this.gameField = gameField;
		this.earth = earth;
		this.fallingSpeed = fallingSpeed;
	}

	@Override
	public void run() {
		while (isAlive) {
			if (!gameField.isPause()) {
				moveY();
				checkIntersect();
				notifyListeners();
			}
			try {
				Thread.sleep(15);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		notifyListenersDelete();
	}

	private void moveY() {
		if (isDropping) {
			if (getLocation().y + getDimension().height < earth.getLocation().y) {
				changeY(-fallingSpeed);
			} else {
				getLocation().y = earth.getLocation().y - getDimension().height;
				isDropping = false;
			}
		}
	}

	private void checkIntersect() {
		if (this.isDropping) {
			Rectangle rectangleThisBlock = getRectangle(this);
			for (Block block : gameField.getBlocks()) {
				Rectangle rectangleBlockFromIterator = getRectangle(block);
				if (!block.equals(this)) {
					if (rectangleThisBlock
							.intersects(rectangleBlockFromIterator)) {
						if (!block.isDropping) {
							getLocation().y = block.getLocation().y
									- getDimension().height;
							isDropping = false;
						} else {
							this.isAlive = false;
							block.isAlive = false;
						}
					}
				}
			}
		}
	}

	public void notifyListeners() {
		for (GameObjectListener object : listeners) {
			object.update(this);
		}
	}

	private void notifyListenersDelete() {
		for (DeleteBlockListener deleteBlockListener : deleteListeners) {
			deleteBlockListener.deleteBlockListener(this);
		}
		gameField.deleteBlock(this);
	}

	public List<GameObjectListener> getGameObjectListeners() {
		return listeners;
	}

	public void addListener(GameObjectListener listener) {
		listeners.add(listener);
	}

	public void addDeleteBlockListener(DeleteBlockListener deleteBlockListener) {
		deleteListeners.add(deleteBlockListener);
	}

	public boolean isDropping() {
		return isDropping;
	}

	public void setDropping(boolean isDrop) {
		this.isDropping = isDrop;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Color getColor() {
		return color;
	}
}
