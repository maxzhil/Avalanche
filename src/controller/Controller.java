package controller;

import model.Model;
import model.listeners.AddBlockListener;
import model.listeners.DeleteBlockListener;
import model.listeners.GameObjectListener;
import model.listeners.GameOverListener;

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

	public void pause() {
		model.pause();
	}

	public void addAvalanche() {
		model.addAvalanche();

	}

	public void addListenerEarth(GameObjectListener gameObjectListener) {
		model.getEarth().addListener(gameObjectListener);

	}

	public void addListenerCharacter(GameObjectListener gameObjectListener) {
		model.getGameCharacter().addListener(gameObjectListener);
	}

	public void addListenerAvalanche(GameObjectListener gameObjectListener) {
		model.getAvalanche().addListener(gameObjectListener);
	}

	public void addBlockListener(AddBlockListener addBlockListener) {
		model.addBlockListener(addBlockListener);
	}

	public void addGameOverListener(GameOverListener gameOverListener) {
		model.addGameOverListener(gameOverListener);

	}

	public void addDeleteBlockListener(DeleteBlockListener deleteBlockListener) {
		model.addDeleteBlockListener(deleteBlockListener);

	}

}
