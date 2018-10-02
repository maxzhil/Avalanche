package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Model extends Thread {
	private boolean isPlay = true;
	private Random random = new Random();
	private Character character;
	private GameField gameField;
	private Earth earth;
	private List<Thread> threads = new ArrayList<Thread>();
	private List<AddBlockListener> listeners = new ArrayList<AddBlockListener>();

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
		Thread threadEarth = new Thread(earth);
		threadEarth.start();
		Thread thread = new Thread(character);
		thread.start();
	}

	public void addBlockListener(AddBlockListener addBlockListener) {
		listeners.add(addBlockListener);

	}

	/*
	 * public void init() { Block block = new Block(30, 400, BLOCK_WIDTH,
	 * BLOCK_HEIGHT, gameField, earth); gameField.addBlock(block); Thread thread
	 * = new Thread(block); thread.start(); threads.add(thread);
	 * notifyAddBlockListener(block);
	 * 
	 * }
	 */

	public void addBlock() {
		Block block = new Block(random.nextInt(gameField.getWidth()
				- Integer.parseInt(Resourcer.getString("model.block.width"))),
				0, Integer.parseInt(Resourcer.getString("model.block.width")),
				Integer.parseInt(Resourcer.getString("model.block.height")),
				gameField, earth);
		gameField.addBlock(block);
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

	public Earth getEarth() {
		return this.earth;

	}

	public GameField getGameField() {
		return gameField;
	}

}
