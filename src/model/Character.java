package model;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import model.GameField;
import model.GameObjectInfo;
import model.GameObjectListener;

public class Character implements Runnable {
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
		if (y > middleGameField) {
			earth.changeY(-deviationFromTheMiddle);
			y = middleGameField;
		}
	}

	private void gravity() {
		if (earth.getY() + earth.getHeight() > gameField.getHeight()) {
			earth.changeY(Integer.parseInt(Resourcer
					.getString("character.move.gravity")));
		} else {
			if (y + height >= earth.getY()) {
				earth.setY(y + height);
				canJump = true;
			}
		}
	}

	private void checkBlock() {
		Rectangle characterRectangle = getRectangle(this);
		for (Block block : gameField.getBlocks()) {
			Rectangle blockRectangle = new Rectangle(block.getX(),
					block.getY(), block.getWidth(), block.getHeight());
			if (characterRectangle.intersects(blockRectangle)) {
				if (!block.isDrop()) {
					if (getY() + getHeight() >= block.getY()) {
						setY(block.getY() - getHeight());
						canJump = true;
					}
				} else {
					System.out.println("dead");
				}
			}
		}
	}

	public void jump() {
		if (canJump) {
			earth.changeY(-Integer.parseInt(Resourcer
					.getString("character.jump")));
			checkBlockForJump();
			this.canJump = false;
		}
	}

	private void checkBlockForJump() {
		Rectangle characterRectangle = getRectangle(this);
		for (Block block : gameField.getBlocks()) {
			Rectangle blockRectangle = new Rectangle(block.getX(),
					block.getY(), block.getWidth(), block.getHeight());
			if (characterRectangle.intersects(blockRectangle)) {
				if (block.getY() + block.getHeight() > getY()) {
					setY(block.getY() + block.getHeight());
					canJump = true;
				}
			}
		}
	}

	private Rectangle getRectangle(Character character) {
		Rectangle rectangle = new Rectangle(character.getX(), character.getY(),
				character.getWidth(), character.getHeight());
		return rectangle;
	}

	public void moveLeft() {
		x -= Integer.parseInt(Resourcer.getString("character.move.toward"));
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

	public void moveRight() {
		x += Integer.parseInt(Resourcer.getString("character.move.toward"));
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
