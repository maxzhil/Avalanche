package model;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import model.GameField;
import model.GameObject;
import model.listeners.GameObjectListener;

public class Character extends GameObject implements Runnable {
	private GameField gameField;
	private Earth earth;
	private boolean canJump = true;
	private boolean isAlive = true;
	private List<GameObjectListener> listeners = new ArrayList<GameObjectListener>();

	public Character(int x, int y, int width, int height, GameField gameField,
			Earth earth) {
		super(x, y, width, height);
		this.gameField = gameField;
		this.earth = earth;
	}

	public void addListener(GameObjectListener listener) {
		listeners.add(listener);
	}

	@Override
	public void run() {
		while (isAlive) {
			if (!gameField.isPause()) {
				checkCoordinateY();
				gravity();
				checkBlock();
				notifyListeners();
			}
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void checkCoordinateY() {
		int middleGameField = gameField.getHeight() / 2;
		int deviationFromTheMiddle = middleGameField - getY();
		if (getY() != middleGameField) {
			earth.changeY(-deviationFromTheMiddle);
			setY(middleGameField);
		}
	}

	private void gravity() {
		if (earth.getY() + earth.getHeight() > gameField.getHeight()) {
			earth.changeY(Integer.parseInt(Resourcer
					.getString("character.move.gravity")));
		} else {
			if (getY() + getHeight() >= earth.getY()) {
				earth.setY(getY() + getHeight());
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
					isAlive = false;
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
		setX(getX()- Integer.parseInt(Resourcer.getString("character.move.toward")));
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
		if (getX() <= 0) {
			setX(gameField.getWidth() - getWidth());
		}
	}

	public void moveRight() {
		setX(getX()+ Integer.parseInt(Resourcer.getString("character.move.toward")));
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
		if (getX() + getWidth() >= gameField.getWidth()) {
			setX(0);
		}
	}

	public void notifyListeners() {
		GameObject info = new GameObject(getX(), getY(), getWidth(), getHeight());
		for (GameObjectListener object : listeners) {
			object.update(info);
		}
	}
	public boolean isAlive() {
		return isAlive;
	}

}
