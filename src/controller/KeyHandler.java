package controller;

public class KeyHandler {
	private Controller controller;

	public KeyHandler(Controller controller) {
		this.controller = controller;
	}

	public void keyPressed(int key) {
		if (key == 100) {
			controller.moveLeft();
		}
		if (key == 102) {
			controller.moveRigth();
		}
		if (key == 104) {
			controller.jump();
		}
		if (key == 101) {
			controller.addBlock();
		}

	}

}
