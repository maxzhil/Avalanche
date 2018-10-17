package view.panel;

import java.awt.Color;

import javax.swing.JPanel;

import model.GameObject;
import model.listeners.GameObjectListener;

public class AvalanchePanel extends JPanel implements GameObjectListener {
	
	private static final long serialVersionUID = 1L;

	public AvalanchePanel() {
		super();
		setBackground(Color.GRAY);
	}

	@Override
	public void update(GameObject info) {
		setLocation(info.getX(), info.getY());
		setSize(info.getWidth(), info.getHeight());

	}
}
