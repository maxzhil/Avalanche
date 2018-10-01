package controller;

import model.Model;

public class Controller {

	private Model model;

	public Controller(Model model) {
		this.model = model;
	}

	public void moveLeft() {
		model.getGameCharacter().moveLeft();
	}

	public void moveRigth() {
		model.getGameCharacter().moveRigth();
	}

	public void jump() {
		model.getGameCharacter().jump();
	}

	public void addBlock() {
		model.addBlock();
	}

}
