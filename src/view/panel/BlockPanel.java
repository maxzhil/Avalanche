package view.panel;

import java.awt.Color;

import javax.swing.JPanel;

import model.GameObjectInfo;
import model.GameObjectListener;

public class BlockPanel extends JPanel implements GameObjectListener {
	public BlockPanel() {
		super();
		setBackground(Color.BLUE);
	}

	@Override
	public void update(GameObjectInfo info) {
		setLocation(info.getX(), info.getY());
		setSize(info.getWidth(), info.getHeight());
	}

}
