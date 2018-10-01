package view.panel;

import java.awt.Color;

import javax.swing.JPanel;

import model.GameObjectInfo;
import model.GameObjectListener;

public class CharacterPanel extends JPanel implements GameObjectListener {
	public CharacterPanel() {
		super();
		setBackground(Color.RED);
	}

	@Override
	public void update(GameObjectInfo info) {
		setLocation(info.getX(), info.getY());
		setSize(info.getWidth(), info.getHeight());

	}

}
