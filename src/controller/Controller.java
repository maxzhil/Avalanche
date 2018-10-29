package controller;

import model.Model;
import model.listeners.AddBlockListener;
import model.listeners.DeleteBlockListener;
import model.listeners.GameObjectListener;
import model.listeners.GameOverListener;
import model.listeners.HeightScoreListener;

public class Controller {

	private Model model;

	public Controller(Model model) {
		this.model = model;
	}

	public void moveLeft() {
		model.moveLeft();
	}

	public void moveRight() {
		model.moveRight();
	}

	public void jump() {
		model.jump();
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

	public void addEarthListener(GameObjectListener gameObjectListener) {
		model.addEarthListener(gameObjectListener);

	}

	public void addCharacterListener(GameObjectListener gameObjectListener) {
		model.addCharacterListener(gameObjectListener);
	}

	public void addAvalancheListener(GameObjectListener gameObjectListener) {
		model.addAvalancheListener(gameObjectListener);
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

	public void addHeightScoreListener(HeightScoreListener heightScoreListener) {
		model.addHeightScoreListener(heightScoreListener);

	}

}
