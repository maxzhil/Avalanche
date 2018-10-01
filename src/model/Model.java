package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Model extends Thread {

	private static final int GAME_FIELD_WIDTH = 600;
	private static final int GAME_FIELD_HEIGHT = 600;
	private static final int BLOCK_WIDTH = 150;
	private static final int BLOCK_HEIGHT = 150;
	private static final int CHARACTER_WIDTH = 60;
	private static final int CHARACTER_HEIGHT = 60;
	private boolean isPlay = true;
	private Random random = new Random();
	private Character character;
	private GameField gameField;
	private Earth earth;
	private List<Thread> threads = new ArrayList<Thread>();
	private List<AddBlockListener> listeners = new ArrayList<AddBlockListener>();

	public Model() {
		gameField = new GameField(GAME_FIELD_WIDTH, GAME_FIELD_HEIGHT);
		earth = new Earth(gameField);
		character = new Character(150, 300, CHARACTER_WIDTH, CHARACTER_HEIGHT,
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

	public void init() {
		Block block = new Block(30, 400, BLOCK_WIDTH, BLOCK_HEIGHT, gameField,
				earth);
		gameField.addBlock(block);
		Thread thread = new Thread(block);
		thread.start();
		threads.add(thread);
		notifyAddBlockListener(block);

	}

	public void addBlock() {
		Block block = new Block(random.nextInt(gameField.getWidth()
				- BLOCK_WIDTH), 0, BLOCK_WIDTH, BLOCK_HEIGHT, gameField, earth);
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
