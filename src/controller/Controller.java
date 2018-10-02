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

	public void moveRight() {
		model.getGameCharacter().moveRight();
	}

	public void jump() {
		model.getGameCharacter().jump();
	}

	public void addBlock() {
		model.addBlock();
	}

}
