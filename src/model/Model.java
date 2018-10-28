package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import model.listeners.AddBlockListener;
import model.listeners.DeleteBlockListener;
import model.listeners.GameObjectListener;
import model.listeners.GameOverListener;

public class Model extends Thread {
	private boolean isPause = false;
	private Random random = new Random();
	private Character character;
	private GameField gameField;
	private Earth earth;
	private Avalanche avalanche;
	private List<Thread> threads = new ArrayList<Thread>();
	private List<AddBlockListener> listeners = new ArrayList<AddBlockListener>();
	private List<DeleteBlockListener> deleteBlockListeners = new ArrayList<DeleteBlockListener>();
	private GameOverListener gameOverListener;

	public Model() {
		gameField = new GameField(Integer.parseInt(Resourcer
				.getString("model.gamefield.width")),
				Integer.parseInt(Resourcer.getString("model.gamefield.height")));
		earth = new Earth(gameField);
		avalanche = new Avalanche(earth);
		character = new Character(
				Integer.parseInt(Resourcer.getString("model.character.x")),
				Integer.parseInt(Resourcer.getString("model.character.y")),
				Integer.parseInt(Resourcer.getString("model.character.width")),
				Integer.parseInt(Resourcer.getString("model.character.height")),
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
		Thread threadEarth = new Thread(earth);
		threadEarth.start();
	}

	private void createCharacterThread() {
		Thread threadCharacter = new Thread(character);
		threadCharacter.start();
	}

	public void addBlock() {
		Block block = BlockCreator.createBlock(gameField, earth);
		gameField.addBlock(block);
		block.addDeleteBlockListener(deleteBlockListeners.get(0));
		Thread thread = new Thread(block);
		thread.start();
		threads.add(thread);
		notifyAddBlockListener(block);
	}

	private void notifyGameOverListener() {
		gameOverListener.gameOver();
	}

	public void notifyAddBlockListener(Block block) {
		for (AddBlockListener addBlockListener : listeners) {
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
		Thread thread = new Thread(avalanche);
		thread.start();
		character.addAvalanche(avalanche);
		threads.add(thread);
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
		listeners.add(addBlockListener);
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
}
