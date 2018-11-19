package model;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import model.listeners.AddBlockListener;
import model.listeners.DeleteBlockListener;
import model.listeners.GameObjectListener;
import model.listeners.GameOverListener;
import model.listeners.HeightScoreListener;
import model.listeners.RemoteBlocksCountListener;

public class Model extends Thread {
	private boolean isPause = false;
	private Random random = new Random();
	private Character character;
	private GameField gameField;
	private Earth earth;
	private Avalanche avalanche;
	// private List<Thread> threads = new ArrayList<Thread>();
	private List<AddBlockListener> addBlockListeners = new ArrayList<AddBlockListener>();
	private List<DeleteBlockListener> deleteBlockListeners = new ArrayList<DeleteBlockListener>();
	private GameOverListener gameOverListener;

	public Model() {
		gameField = new GameField(
				new Dimension(Integer.parseInt(Resourcer
						.getString("model.gamefield.width")),
						Integer.parseInt(Resourcer
								.getString("model.gamefield.height"))));
		earth = new Earth(gameField);
		avalanche = new Avalanche(earth);
		character = new Character(new Point(Integer.parseInt(Resourcer
				.getString("model.character.x")), Integer.parseInt(Resourcer
				.getString("model.character.y"))),
				new Dimension(Integer.parseInt(Resourcer
						.getString("model.character.width")),
						Integer.parseInt(Resourcer
								.getString("model.character.height"))),
				gameField, earth);

	}

	@Override
	public void run() {
		createEarthThread();
		createCharacterThread();
		while (character.isAlive()) {
			if (!isPause) {
				addBlock();
			}
			try {
				sleep(random.nextInt(1500));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		notifyGameOverListener();
	}

	private void createEarthThread() {
		new Thread(earth).start();
	}

	private void createCharacterThread() {
		new Thread(character).start();
	}

	public void addBlock() {
		Block block = BlockCreator.createBlock(gameField, earth);
		gameField.addBlock(block);
		block.addDeleteBlockListener(deleteBlockListeners.get(0));
		new Thread(block).start();
		notifyAddBlockListener(block);
	}

	private void notifyGameOverListener() {
		gameOverListener.gameOver();
	}

	public void notifyAddBlockListener(Block block) {
		for (AddBlockListener addBlockListener : addBlockListeners) {
			addBlockListener.addBlock(block);
		}
	}

	public GameField getGameField() {
		return gameField;
	}

	public void pause() {
		this.isPause = !isPause;
		gameField.setPause(isPause);
	}

	public void addAvalanche() {
		new Thread(avalanche).start();
		character.addAvalanche(avalanche);
	}

	public void moveRight() {
		character.moveRight();
	}

	public void moveLeft() {
		character.moveLeft();
	}

	public void jump() {
		character.jump();
	}

	public void addBlockListener(AddBlockListener addBlockListener) {
		addBlockListeners.add(addBlockListener);
	}

	public void addDeleteBlockListener(DeleteBlockListener addBlockListener) {
		deleteBlockListeners.add(addBlockListener);
	}

	public void addGameOverListener(GameOverListener gameOverListener) {
		this.gameOverListener = gameOverListener;
	}

	public void addEarthListener(GameObjectListener gameObjectListener) {
		earth.addListener(gameObjectListener);
	}

	public void addCharacterListener(GameObjectListener gameObjectListener) {
		character.addListener(gameObjectListener);
	}

	public void addAvalancheListener(GameObjectListener gameObjectListener) {
		avalanche.addListener(gameObjectListener);
	}

	public void addHeightScoreListener(HeightScoreListener heightScoreListener) {
		character.addHeightScoreListener(heightScoreListener);
	}

	public void addRemoteBlocksCountListener(
			RemoteBlocksCountListener remoteBlocksCountListener) {
		gameField.addRemoteBlocksCountListener(remoteBlocksCountListener);
	}

}
