package model;

import java.awt.Dimension;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import model.Block;
import model.listeners.RemoteBlocksCountListener;

public class GameField {
	private Dimension dimension;
	private boolean isPause = false;
	private List<Block> blocks = new CopyOnWriteArrayList<Block>();
	private RemoteBlocksCountListener remoteBlocksCountListener;
	private int countRemoteBlocks;

	public GameField(Dimension dimension) {
		this.dimension = dimension;
	}

	public List<Block> getBlocks() {
		return blocks;
	}

	public void addBlock(Block block) {
		blocks.add(block);
	}

	public void deleteBlock(Block block) {
		blocks.remove(block);
		calculateRemoteBlocks();
	}

	public void setBlocks(List<Block> blocks) {
		this.blocks = blocks;
	}

	public Dimension getDimension() {
		return dimension;
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

	private void calculateRemoteBlocks() {
		countRemoteBlocks++;
		notifyRemoteBlocksCountListener();
	}

}
