package model;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import model.GameField;
import model.GameObject;
import model.listeners.DeleteBlockListener;
import model.listeners.GameObjectListener;

public class Block extends GameObject implements Runnable {
	private boolean isAlive = true;
	private Color color;
	private boolean isDropping = true;
	private Random random = new Random();
	private GameField gameField;
	private Earth earth;
	private List<GameObjectListener> listeners = new ArrayList<GameObjectListener>();
	private List<DeleteBlockListener> delListeners = new ArrayList<DeleteBlockListener>();

	public Block(int x, int y, int width, int height, GameField gameField,
			Earth earth) {
		super(x, y, width, height);
		this.gameField = gameField;
		this.earth = earth;
	}

	public List<GameObjectListener> geGameObjectListener() {
		return listeners;
	}

	public void addListener(GameObjectListener listener) {
		listeners.add(listener);
	}

	public void notifyListeners() {
		GameObject info = new GameObject(getX(), getY(), getWidth(),
				getHeight());
		for (GameObjectListener object : listeners) {
			object.update(info);
		}
	}

	private void moveY() {
		if (isDropping) {
			if (getY() + getHeight() < earth.getY()) {
				changeY(random.nextInt(5));
			} else {
				setY(earth.getY() - getHeight());
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
					if (!block.isDropping) {
						if (rectangleThisBlock
								.intersects(rectangleBlockFromIterator)) {
							setY(block.getY() - getHeight());
							isDropping = false;
						}
					} else {
						if (rectangleThisBlock
								.intersects(rectangleBlockFromIterator)) {
							this.isAlive = false;
							block.isAlive = false;
							System.out.println("Столкновение");
						}
					}
				}
			}
		}

	}

	private Rectangle getRectangle(Block block) {
		return new Rectangle(block.getX(), block.getY(), block.getWidth(),
				block.getHeight());
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
				Thread.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		notifyListenersDelete();
	}

	public void addDeleteBlockListener(DeleteBlockListener deleteBlockListener) {
		delListeners.add(deleteBlockListener);
	}

	private void notifyListenersDelete() {
		for (DeleteBlockListener object : delListeners) {
			object.deleteBlockListener(this);
		}
		gameField.deleteBlock(this);
	}

	public boolean isDrop() {
		return isDropping;
	}

	public void setDrop(boolean isDrop) {
		this.isDropping = isDrop;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Color getColor() {
		return color;
	}

}
