package model;

import java.awt.Color;
import java.util.Random;

public class BlockCreator {
	private static Random random = new Random();
	private static BlockType[] blockTypes = BlockType.values();

	private BlockCreator() {
	}

	public static Block createBlock(GameField gameField, Earth earth) {
		Block block = null;
		switch (blockTypes[random.nextInt(3)]) {
		case SMALL:
			block = new Block(getRandomX(gameField, Integer.parseInt(Resourcer
					.getString("model.block.small.width"))), -150,
					Integer.parseInt(Resourcer
							.getString("model.block.small.width")),
					Integer.parseInt(Resourcer
							.getString("model.block.small.height")), gameField,
					earth, random.nextInt(3));
			block.setColor(Color.BLUE);
			break;
		case MIDDLE:
			block = new Block(getRandomX(gameField, Integer.parseInt(Resourcer
					.getString("model.block.middle.width"))), -150,
					Integer.parseInt(Resourcer
							.getString("model.block.middle.width")),
					Integer.parseInt(Resourcer
							.getString("model.block.middle.height")),
					gameField, earth, random.nextInt(3));
			block.setColor(Color.ORANGE);
			break;
		case LARGE:
			block = new Block(getRandomX(gameField, Integer.parseInt(Resourcer
					.getString("model.block.large.width"))),-150,
					Integer.parseInt(Resourcer
							.getString("model.block.large.width")),
					Integer.parseInt(Resourcer
							.getString("model.block.large.height")), gameField,
					earth, random.nextInt(3));
			block.setColor(Color.DARK_GRAY);
			break;
		}
		return block;
	}

	private static int getRandomX(GameField gameField, int width) {
		return random.nextInt(gameField.getWidth() - width);
	}
}
