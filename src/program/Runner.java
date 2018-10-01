package program;

import model.Model;
import view.View;
import controller.Controller;

public class Runner {
	public static void main(String[] args) {
		Model model = new Model();
		Controller controller = new Controller(model);
		View view = new View(model, controller);

		model.start();
	}
}
