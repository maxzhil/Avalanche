package model;

import java.util.ArrayList;
import java.util.List;

import model.Block;

public class GameField {
	private int width;
	private int height;
	private List<Block> blocks = new ArrayList<Block>();

	public GameField(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public List<Block> getBlocks() {
		return blocks;
	}

	public void addBlock(Block block) {
		blocks.add(block);
	}

	public void setBlocks(List<Block> blocks) {
		this.blocks = blocks;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

}
