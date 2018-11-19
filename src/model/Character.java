package model;

import java.awt.Dimension;
import java.awt.Point;
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
		super(new Point(x, y), new Dimension(width, height));
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
		heightScore = earth.getLocation().y
				- (getLocation().y + getDimension().height);
	}

	private void checkPositionY() {
		int middleGameField = gameField.getDimension().height / 2;
		int deviationFromTheMiddle = middleGameField - getLocation().y;
		if (getLocation().y != middleGameField) {
			earth.moveY(-deviationFromTheMiddle);
			getLocation().y = middleGameField;
		}
	}

	private void gravity() {
		if (earth.getLocation().y + earth.getDimension().height > gameField
				.getDimension().height) {
			earth.moveY(Integer.parseInt(Resourcer
					.getString("character.move.gravity")));
		} else {
			if (getLocation().y + getDimension().height >= earth.getLocation().y) {
				earth.getLocation().y = getLocation().y + getDimension().height;
				canJump = true;
			}
		}
	}

	private void checkInteractWithAvalanche() {
		if (avalanche != null) {
			if (getLocation().y + getDimension().height >= avalanche
					.getLocation().y) {
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
		if (block.getLocation().y + block.getDimension().height >= getLocation().y
				&& block.getLocation().y + block.getDimension().height <= getLocation().y
						+ getDimension().height / 2) {
			isAlive = false;
		}
	}

	private void checkInteractWithBlockDown(Block block) {
		if (getLocation().y + getDimension().height >= block.getLocation().y
				&& getLocation().y + getDimension().height <= block
						.getLocation().y + getDimension().height / 2) {
			getLocation().y = block.getLocation().y - getDimension().height;
			canJump = true;
		}
	}

	private void checkInteractWithBlockLeft(Block block) {
		if (getLocation().x <= block.getLocation().x
				+ block.getDimension().width) {
			getLocation().x = block.getLocation().x
					+ block.getDimension().width;
		}
	}

	private void checkInteractWithBlockRight(Block block) {
		if (getLocation().x + getDimension().width >= block.getLocation().x) {
			getLocation().x = block.getLocation().x - getDimension().width;
		}
	}

	private void checkInteractWithBlockUp(Block block) {
		if (getLocation().y <= block.getLocation().y
				+ block.getDimension().height
				&& getLocation().y >= block.getLocation().y
						+ block.getDimension().height / 2) {
			getLocation().y = block.getLocation().y
					+ block.getDimension().height;
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
		Rectangle rectangle = new Rectangle(gameObject.getLocation().x,
				gameObject.getLocation().y, gameObject.getDimension().width,
				gameObject.getDimension().height);
		return rectangle;
	}

	public void moveLeft() {
		getLocation().x = getLocation().x
				- Integer
						.parseInt(Resourcer.getString("character.move.toward"));
		checkGameFieldWidth(CollisionDirection.LEFT);
		checkInteractWithBlock(CollisionDirection.LEFT);
	}

	public void moveRight() {
		getLocation().x = getLocation().x
				+ Integer
						.parseInt(Resourcer.getString("character.move.toward"));
		checkGameFieldWidth(CollisionDirection.RIGHT);
		checkInteractWithBlock(CollisionDirection.RIGHT);
	}

	private void checkGameFieldWidth(CollisionDirection collisionDirection) {
		switch (collisionDirection) {
		case RIGHT:
			if (getLocation().x + getDimension().width >= gameField
					.getDimension().width) {
				getLocation().x = 0;
			}
			break;
		case LEFT:
			if (getLocation().x <= 0) {
				getLocation().x = gameField.getDimension().width
						- getDimension().width;
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
