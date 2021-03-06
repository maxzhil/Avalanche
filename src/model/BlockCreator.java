package model;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.util.Random;

import model.enums.BlockType;

public class BlockCreator {
	private static Random random = new Random();
	private static BlockType[] blockTypes = BlockType.values();
	private static final int SPEED_FROM = 1;
	private static final int SPEED_TO = 3;

	private BlockCreator() {
	}

	public static Block createBlock(GameField gameField, Earth earth) {
		Block block = null;
		switch (blockTypes[random.nextInt(blockTypes.length)]) {
		case SMALL:
			block = new Block(
					new Point(getRandomX(gameField, Integer.parseInt(Resourcer
							.getString("model.block.small.width"))),
							Integer.parseInt(Resourcer
									.getString("value.start.block"))),
					new Dimension(Integer.parseInt(Resourcer
							.getString("model.block.small.width")), Integer
							.parseInt(Resourcer
									.getString("model.block.small.height"))),
					gameField, earth, getRandomForBlockSpeed());
			block.setColor(Color.BLUE);
			break;
		case MIDDLE:
			block = new Block(
					new Point(getRandomX(gameField, Integer.parseInt(Resourcer
							.getString("model.block.middle.width"))),
							Integer.parseInt(Resourcer
									.getString("value.start.block"))),
					new Dimension(Integer.parseInt(Resourcer
							.getString("model.block.middle.width")), Integer
							.parseInt(Resourcer
									.getString("model.block.middle.height"))),
					gameField, earth, getRandomForBlockSpeed());
			block.setColor(Color.ORANGE);
			break;
		case LARGE:
			block = new Block(
					new Point(getRandomX(gameField, Integer.parseInt(Resourcer
							.getString("model.block.large.width"))),
							Integer.parseInt(Resourcer
									.getString("value.start.block"))),
					new Dimension(Integer.parseInt(Resourcer
							.getString("model.block.large.width")), Integer
							.parseInt(Resourcer
									.getString("model.block.large.height"))),
					gameField, earth, getRandomForBlockSpeed());
			block.setColor(Color.DARK_GRAY);
			break;
		}
		return block;
	}

	private static int getRandomX(GameField gameField, int width) {
		return random.nextInt(gameField.getDimension().width - width);
	}

	private static int getRandomForBlockSpeed() {
		return (int) (Math.random() * (SPEED_TO - SPEED_FROM) + SPEED_FROM);
	}
}
