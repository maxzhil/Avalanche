package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import model.listeners.AddBlockListener;
import model.listeners.DeleteBlockListener;

public class Model extends Thread {
	private boolean isPause = false;
	private Random random = new Random();
	private Character character;
	private GameField gameField;
	private Earth earth;
	private List<Thread> threads = new ArrayList<Thread>();
	private List<AddBlockListener> listeners = new ArrayList<AddBlockListener>();
	private List<DeleteBlockListener> deleteBlockListeners = new ArrayList<DeleteBlockListener>();


	public Model() {
		gameField = new GameField(Integer.parseInt(Resourcer
				.getString("model.gamefield.width")),
				Integer.parseInt(Resourcer.getString("model.gamefield.height")));
		earth = new Earth(gameField);
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
		System.out.println("Game Over");
	}

	public void addBlockListener(AddBlockListener addBlockListener) {
		listeners.add(addBlockListener);
	}

	public void addDeleteBlockListener(DeleteBlockListener addBlockListener) {
		deleteBlockListeners.add(addBlockListener);
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

	public void notifyAddBlockListener(Block block) {
		for (AddBlockListener addBlockListener : listeners) {
			addBlockListener.addBlock(block);
		}
	}

	public Character getGameCharacter() {
		return character;
	}

	private void createEarthThread() {
		Thread threadEarth = new Thread(earth);
		threadEarth.start();
	}

	private void createCharacterThread() {
		Thread threadCharacter = new Thread(character);
		threadCharacter.start();
	}

	public Earth getEarth() {
		return this.earth;
	}

	public GameField getGameField() {
		return gameField;
	}

	public void pause() {
		this.isPause = !isPause;
		gameField.setPause(isPause);
	}
}
