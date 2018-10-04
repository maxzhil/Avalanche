package view.panel;

import java.awt.Color;

import javax.swing.JPanel;

import model.GameObjectInfo;
import model.listeners.GameObjectListener;

public class EarthPanel extends JPanel implements GameObjectListener {
	public EarthPanel() {
		super();
		setBackground(Color.BLACK);
	}

	@Override
	public void update(GameObjectInfo info) {
		setLocation(info.getX(), info.getY());
		setSize(info.getWidth(), info.getHeight());

	}
}
