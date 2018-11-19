package view.panel;

import java.awt.Color;

import javax.swing.JPanel;

import model.GameObject;
import model.listeners.GameObjectListener;

public class CharacterPanel extends JPanel implements GameObjectListener {

	private static final long serialVersionUID = 1L;

	public CharacterPanel() {
		super();
		setBackground(Color.RED);
	}

	@Override
	public void update(GameObject info) {
		setLocation(info.getLocation());
		setSize(info.getDimension());
	}

}
