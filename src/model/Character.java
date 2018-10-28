package model;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

import model.GameField;
import model.GameObject;
import model.enums.CollisionDirection;
import model.listeners.GameObjectListener;

public class Character extends GameObject implements Runnable {
	private GameField gameField;
	private Earth earth;
	private Avalanche avalanche;
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
				try {
					checkPositionY();
					gravity();
					checkInteractWithAvalanche();
					checkInteractWithBlock(CollisionDirection.DOWN);
					notifyListeners();
				} catch (ConcurrentModificationException e) {
					System.out.print("");
				}
			}
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void checkPositionY() {
		int middleGameField = gameField.getHeight() / 2;
		int deviationFromTheMiddle = middleGameField - getY();
		if (getY() != middleGameField) {
			earth.moveY(-deviationFromTheMiddle);
			setY(middleGameField);
		}
	}

	private void gravity() {
		if (earth.getY() + earth.getHeight() > gameField.getHeight()) {
			earth.moveY(Integer.parseInt(Resourcer
					.getString("character.move.gravity")));
		} else {
			if (getY() + getHeight() >= earth.getY()) {
				earth.setY(getY() + getHeight());
				canJump = true;
			}
		}
	}

	private void checkInteractWithAvalanche() {
		if (avalanche != null) {
			if (getY() + getHeight() >= avalanche.getY()) {
				isAlive = false;
			}
		}
	}

	private void checkInteractWithBlock(CollisionDirection collisionDirection) {
		Rectangle characterRectangle = getRectangle(this);
		for (Block block : gameField.getBlocks()) {
			Rectangle blockRectangle = getRectangle(block);
			if (characterRectangle.intersects(blockRectangle)) {
				switch (collisionDirection) {
				case DOWN:
					if (!block.isDropping()) {
						if (getY() + getHeight() >= block.getY()
								&& getY() + getHeight() <= block.getY()
										+ getHeight() / 2) {
							setY(block.getY() - getHeight());
							canJump = true;
						}
					} else {
						if (canJump) {
							isAlive = false;
						} else {
							if (getY() + getHeight() >= block.getY()
									&& getY() + getHeight() <= block.getY()
											+ getHeight() / 2) {
								setY(block.getY() - getHeight());
								
							}
						}
					}
					break;
				case LEFT:
					if (getX() <= block.getX() + block.getWidth()) {
						setX(block.getX() + block.getWidth());
						return;
					}
					break;
				case RIGHT:
					if (getX() + getWidth() >= block.getX()) {
						setX(block.getX() - getWidth());
					}
					break;
				case UP:
					if (getY() <= block.getY() + block.getHeight()
							&& getY() >= block.getY() + block.getHeight() / 2) {
						System.out.println("collision with block");
						setY(block.getY() + block.getHeight());
						canJump = true;
					}
					break;
				}
			}
		}
	}

	public void jump() {
		if (!gameField.isPause()) {
			if (canJump) {
				for (int i = 0; i < Math.abs(Integer.parseInt(Resourcer
						.getString("character.jump"))); i++) {
					earth.moveY(-i);
					checkInteractWithBlock(CollisionDirection.UP);
					if (avalanche != null) {
						avalanche.changeY(-i);
					}
					this.canJump = false;
				}
			}
		}
	}

	private Rectangle getRectangle(GameObject gameObject) {
		Rectangle rectangle = new Rectangle(gameObject.getX(),
				gameObject.getY(), gameObject.getWidth(),
				gameObject.getHeight());
		return rectangle;
	}

	public void moveLeft() {
		setX(getX()
				- Integer
						.parseInt(Resourcer.getString("character.move.toward")));
		checkInteractWithBlock(CollisionDirection.LEFT);
		if (getX() <= 0) {
			setX(gameField.getWidth() - getWidth());
		}
	}

	public void moveRight() {
		setX(getX()
				+ Integer
						.parseInt(Resourcer.getString("character.move.toward")));
		checkInteractWithBlock(CollisionDirection.RIGHT);
		if (getX() + getWidth() >= gameField.getWidth()) {
			setX(0);
		}
	}

	public void notifyListeners() {
		for (GameObjectListener object : listeners) {
			object.update(this);
		}
	}

	public boolean isAlive() {
		return isAlive;
	}

	public void addAvalanche(Avalanche avalanche) {
		this.avalanche = avalanche;
	}
}
