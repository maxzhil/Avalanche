package view;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import controller.Controller;

import model.Model;

public class MainMenu extends JFrame {

	private static final int BUTTON_WIDTH = 100;
	private static final int BUTTON_HEIGHT = 25;
	private static final long serialVersionUID = 1L;

	public MainMenu() {
		this.setSize(500, 500);
		this.setPreferredSize(this.getSize());
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setLayout(new BorderLayout());
		JPanel mainPanel = new JPanel();
		JButton playButton = new JButton("Play");
		playButton.setBounds((getSize().width - BUTTON_WIDTH) / 2,
				getHeight() / 2, BUTTON_WIDTH, BUTTON_HEIGHT);
		playButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent mouseEvent) {
				loadGame();
			}
		});
		JButton exitButton = new JButton("Exit");
		exitButton.setBounds(playButton.getLocation().x, BUTTON_HEIGHT
				+ playButton.getLocation().y, BUTTON_WIDTH, BUTTON_HEIGHT);
		exitButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent mouseEvent) {
				System.exit(0);
			}
		});

		mainPanel.add(playButton);
		mainPanel.add(exitButton);
		this.getContentPane().add(mainPanel);

		this.pack();
	}

	private void loadGame() {
		this.setVisible(false);
		Model model = new Model();
		Controller controller = new Controller(model);
		new View(model, controller);
		model.start();
	}
}
