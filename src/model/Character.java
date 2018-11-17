package model;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import model.GameField;
import model.GameObject;
import model.enums.CollisionDirection;
import model.listeners.GameObjectListener;
import model.listeners.HeightScoreListener;

public class Character extends GameObject implements Runnable {
	private GameField gameField;
	private Earth earth;
	private Avalanche avalanche;
	private int heightScore;
	private boolean canJump = true;
	private boolean isAlive = true;
	private List<GameObjectListener> listeners = new ArrayList<GameObjectListener>();
	private List<HeightScoreListener> heightScoreListeners = new ArrayList<HeightScoreListener>();

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
				checkPositionY();
				gravity();
				checkInteractWithAvalanche();
				checkInteractWithBlock(CollisionDirection.DOWN);
				notifyListeners();
				notifyHeightScoreListener();
			}
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		notifyListeners();
	}

	private void getCurrentHeightScore() {
		heightScore = earth.getY() - (getY() + getHeight());
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
					checkAlive(block);
					checkInteractWithBlockDown(block);
					break;
				case LEFT:
					checkInteractWithBlockLeft(block);
					break;
				case RIGHT:
					checkInteractWithBlockRight(block);
					break;
				case UP:
					checkInteractWithBlockUp(block);
					break;
				}
			}
		}
	}

	private void checkAlive(Block block) {
		if (block.getY() + block.getHeight() >= getY()
				&& block.getY() + block.getHeight() <= getY() + getHeight() / 2) {
			isAlive = false;
		}
	}

	private void checkInteractWithBlockDown(Block block) {
		if (getY() + getHeight() >= block.getY()
				&& getY() + getHeight() <= block.getY() + getHeight() / 2) {
			setY(block.getY() - getHeight());
			canJump = true;
		}
	}

	private void checkInteractWithBlockLeft(Block block) {
		if (getX() <= block.getX() + block.getWidth()) {
			setX(block.getX() + block.getWidth());
		}
	}

	private void checkInteractWithBlockRight(Block block) {
		if (getX() + getWidth() >= block.getX()) {
			setX(block.getX() - getWidth());
		}
	}

	private void checkInteractWithBlockUp(Block block) {
		if (getY() <= block.getY() + block.getHeight()
				&& getY() >= block.getY() + block.getHeight() / 2) {
			setY(block.getY() + block.getHeight());
			canJump = true;
		}
	}

	public void jump() {
		if (!gameField.isPause()) {
			if (canJump) {
				for (int i = 0; i < Math.abs(Integer.parseInt(Resourcer
						.getString("character.jump"))); i++) {
					earth.moveY(-i);
					checkInteractWithBlock(CollisionDirection.UP);
					moveAvalanche(-i);
					this.canJump = false;
				}
			}
		}
	}

	private void moveAvalanche(int value) {
		if (avalanche != null) {
			avalanche.changeY(value);
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
		checkGameFieldWidth(CollisionDirection.LEFT);
		checkInteractWithBlock(CollisionDirection.LEFT);
	}

	public void moveRight() {
		setX(getX()
				+ Integer
						.parseInt(Resourcer.getString("character.move.toward")));
		checkGameFieldWidth(CollisionDirection.RIGHT);
		checkInteractWithBlock(CollisionDirection.RIGHT);
	}

	private void checkGameFieldWidth(CollisionDirection collisionDirection) {
		switch (collisionDirection) {
		case RIGHT:
			if (getX() + getWidth() >= gameField.getWidth()) {
				setX(0);
			}
			break;
		case LEFT:
			if (getX() <= 0) {
				setX(gameField.getWidth() - getWidth());
			}
			break;
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

	public void addHeightScoreListener(HeightScoreListener heightScoreListener) {
		this.heightScoreListeners.add(heightScoreListener);
	}

	public void notifyHeightScoreListener() {
		getCurrentHeightScore();
		for (HeightScoreListener heightScoreListener : heightScoreListeners) {
			heightScoreListener.updateHeightScore(heightScore);
		}
	}
}
