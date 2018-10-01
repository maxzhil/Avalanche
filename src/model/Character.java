package model;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import model.GameField;
import model.GameObjectInfo;
import model.GameObjectListener;

public class Character implements Runnable {
	private static final int JUMP_SIZE = 200;
	private static final int MOVE_SIZE = 30;
	private static final int MOVE_VALUE = 5;
	private GameField gameField;
	private Earth earth;

	private int x;
	private int y;
	private boolean canJump = true;

	private int width;
	private int height;

	private List<GameObjectListener> listeners = new ArrayList<GameObjectListener>();

	public Character(int x, int y, int width, int height, GameField gameField,
			Earth earth) {
		this.x = x;
		this.y = y;
		this.height = height;
		this.width = width;
		this.gameField = gameField;
		this.earth = earth;
	}

	public void addListener(GameObjectListener listener) {
		listeners.add(listener);
	}

	@Override
	public void run() {
		while (true) {
			checkCoordinateY();
			gravity();
			checkBlock();
			notifyListeners();
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void checkCoordinateY() {
		int middleGameField = gameField.getHeight() / 2;
		int deviationFromTheMiddle = middleGameField - y;
		if (y < middleGameField) {
			earth.changeY(-deviationFromTheMiddle);
			y = middleGameField;
		}
	}

	private void gravity() {
		if (earth.getY() + earth.getHeight() > gameField.getHeight()) {
			earth.changeY(MOVE_VALUE);
		} else {
			if (y + height >= earth.getY()) {
				earth.setY(y + height);
				canJump = true;
			}
		}
	}

	private void checkBlock() {
		Rectangle rectangle = getRectangle(this);
		for (Block block : gameField.getBlocks()) {
			Rectangle rectangle2 = new Rectangle(block.getX(), block.getY(),
					block.getWidth(), block.getHeight());
			if (rectangle.intersects(rectangle2)) {
				if (getY() + getHeight() > block.getY()) {
					setY(block.getY() - getHeight());
					canJump = true;
				}
			}
		}
	}

	public void jump() {
		if (canJump) {
			earth.changeY(-JUMP_SIZE);
			this.canJump = false;
		}
	}

	private Rectangle getRectangle(Character character) {
		Rectangle rectangle = new Rectangle(character.getX(), character.getY(),
				character.getWidth(), character.getHeight());
		return rectangle;
	}

	public void moveLeft() {
		x -= MOVE_SIZE;
		Rectangle rectangle = getRectangle(this);
		for (Block block : gameField.getBlocks()) {
			Rectangle rectangle2 = new Rectangle(block.getX(), block.getY(),
					block.getWidth(), block.getHeight());
			if (rectangle.intersects(rectangle2)) {
				if (getX() <= block.getX() + block.getWidth()) {
					setX(block.getX() + block.getWidth() + 1);
					return;
				}
			}
		}
		if (x <= 0) {
			x = gameField.getWidth() - getWidth();
		}

	}

	public void moveRigth() {
		x += MOVE_SIZE;
		Rectangle rectangle = getRectangle(this);
		for (Block block : gameField.getBlocks()) {
			Rectangle rectangle2 = new Rectangle(block.getX(), block.getY(),
					block.getWidth(), block.getHeight());
			if (rectangle.intersects(rectangle2)) {
				if (getX() + getWidth() >= block.getX()) {
					setX(block.getX() - getWidth());
				}
			}
		}
		if (x + width >= gameField.getWidth()) {
			x = 0;
		}

	}

	public void notifyListeners() {
		GameObjectInfo info = new GameObjectInfo(x, y, width, height);
		for (GameObjectListener object : listeners) {
			object.update(info);
		}
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getY() {
		return y;
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

}
