package model;

import java.util.ArrayList;
import java.util.List;

import model.Block;
import model.listeners.RemoteBlocksCountListener;

public class GameField {
	private int height;
	private int width;
	private boolean isPause = false;
	private List<Block> blocks = new ArrayList<Block>();
	private RemoteBlocksCountListener remoteBlocksCountListener;
	private int countRemoteBlocks;

	public GameField(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public synchronized List<Block> getBlocks() {
		return blocks;
	}

	public void addBlock(Block block) {
		blocks.add(block);
	}

	public synchronized void deleteBlock(Block block) {
		blocks.remove(block);
		countRemoteBlocks++;
		notifyRemoteBlocksCountListener();
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

	public boolean isPause() {
		return isPause;
	}

	public void setPause(boolean isPause) {
		this.isPause = isPause;
	}

	private void notifyRemoteBlocksCountListener() {
		if (remoteBlocksCountListener != null) {
			remoteBlocksCountListener
					.updateRemoteBlocksCount(countRemoteBlocks);
		}
	}

	public void addRemoteBlocksCountListener(
			RemoteBlocksCountListener remoteBlocksCountListener) {
		this.remoteBlocksCountListener = remoteBlocksCountListener;
	}

}
