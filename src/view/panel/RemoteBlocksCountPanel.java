package view.panel;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Resourcer;
import model.listeners.RemoteBlocksCountListener;

public class RemoteBlocksCountPanel extends JPanel implements
		RemoteBlocksCountListener {

	private static final long serialVersionUID = 1L;
	private JLabel countText;

	public RemoteBlocksCountPanel() {
		super();
		setLocation(0, 30);
		setPreferredSize(new Dimension(150, 20));
		setLayout(null);
		setOpaque(false);
		countText = new JLabel(String.format("%s %d",
				Resourcer.getString("remote.blocks.count"), 0));
		countText.setForeground(Color.BLACK);
		countText.setLocation(0, 0);
		countText.setSize(150, 20);
		this.add(countText);
	}

	@Override
	public void updateRemoteBlocksCount(int count) {
		countText.setText(String.format("%s %d",
				Resourcer.getString("remote.blocks.count"), count));

	}
}
