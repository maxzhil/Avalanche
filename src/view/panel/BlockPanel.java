package view.panel;

import java.awt.Color;

import javax.swing.JPanel;

import model.GameObject;
import model.listeners.GameObjectListener;

public class BlockPanel extends JPanel implements GameObjectListener {

	private static final long serialVersionUID = 1L;

	public BlockPanel(Color color) {
		super();
		setBackground(color);
	}

	@Override
	public void update(GameObject info) {
		setLocation(info.getX(), info.getY());
		setSize(info.getWidth(), info.getHeight());

	}

}
