package model;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import model.GameField;
import model.GameObjectInfo;
import model.GameObjectListener;

public class Block extends GameObjectInfo implements Runnable {
	private boolean isDrop = true;
	private Random random = new Random();
	private GameField gameField;
	private Earth earth;
	private List<GameObjectListener> listeners = new ArrayList<GameObjectListener>();
	private List<Block> blocks = new ArrayList<Block>();

	public Block(int x, int y, int width, int height, GameField gameField,
			Earth earth) {
		super(x, y, width, height);
		this.gameField = gameField;
		this.earth = earth;
	}

	public void addListener(GameObjectListener listener) {
		listeners.add(listener);
	}

	public void notifyListeners() {
		GameObjectInfo info = new GameObjectInfo(getX(), getY(), getWidth(),
				getHeight());
		for (GameObjectListener object : listeners) {
			object.update(info);
		}
	}

	public void moveY() {
		if (isDrop) {
			if (getY() + getHeight() < earth.getY()) {
				changeY(random.nextInt(5));
			} else {
				setY(earth.getY() - getHeight());
				isDrop = false;
			}
		}
		/*
		 * if (blocks.isEmpty()) { if (getY() + getHeight() <= earth.getY()) {
		 * changeY(random.nextInt(5));
		 * 
		 * } else { setY(earth.getY() - getHeight()); isDrop = false; } } else {
		 * Rectangle rectangle = getRectangle(this); for (Block block : blocks)
		 * { Rectangle rectangle2 = getRectangle(block); if
		 * (!rectangle.intersects(rectangle2)) { isDrop = true; } } }
		 */

	}

	private void checkIntersect() {
		if (this.isDrop) {
			Rectangle rectangle = getRectangle(this);
			for (Block block : gameField.getBlocks()) {
				if (!block.equals(this)) {
					if (!block.isDrop) {
						Rectangle rectangle2 = getRectangle(block);
						if (rectangle.intersects(rectangle2)) {
							setY(block.getY() - getHeight());
							this.blocks.add(block);
							isDrop = false;
						}
					}
				}
			}
		}
	}

	private Rectangle getRectangle(Block block) {
		Rectangle rectangle = new Rectangle(block.getX(), block.getY(),
				block.getWidth(), block.getHeight());
		return rectangle;
	}

	@Override
	public void run() {
		while (true) {
			moveY();
			checkIntersect();
			notifyListeners();
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean isDrop() {
		return isDrop;
	}

	public void setDrop(boolean isDrop) {
		this.isDrop = isDrop;
	}

}
