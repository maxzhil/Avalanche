package controller;

public class KeyHandler {
	private Controller controller;

	public KeyHandler(Controller controller) {
		this.controller = controller;
	}

	public void keyPressed(int key) {
		if (key == KeyAdapter.getKeyCode("key.left")) {
			controller.moveLeft();
		}
		if (key == KeyAdapter.getKeyCode("key.right")) {
			controller.moveRight();
		}
		if (key == KeyAdapter.getKeyCode("key.up")) {
			controller.jump();
		}
		if (key == 101) {
			//controller.addBlock();
			controller.addAvalanche();
		}
		if (key == KeyAdapter.getKeyCode("key.down")) {
			controller.pause();
		}
	}

}
