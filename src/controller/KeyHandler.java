package controller;

import model.Resourcer;

public class KeyHandler {
	private Controller controller;

	public KeyHandler(Controller controller) {
		this.controller = controller;
	}

	public void keyPressed(int key) {
		if (key == KeyAdapter.getKeyCode(Resourcer.getString("key.left"))) {
			controller.moveLeft();
		}
		if (key == KeyAdapter.getKeyCode(Resourcer.getString("key.right"))) {
			controller.moveRight();
		}
		if (key == KeyAdapter.getKeyCode(Resourcer.getString("key.up"))) {
			controller.jump();
		}
		if (key == 101) {
			//TODO add avalanche not pressing key
			controller.addAvalanche();
		}
		if (key == KeyAdapter.getKeyCode(Resourcer.getString("key.down"))) {
			controller.pause();
		}
	}

}
