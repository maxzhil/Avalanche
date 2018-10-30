package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.Controller;

import model.ImageWorker;
import model.Model;
import model.Resourcer;

public class MainMenu extends JFrame {
	private static final int BUTTON_WIDTH = 100;
	private static final int BUTTON_HEIGHT = 30;
	private static final int INTENT = 30;
	private static final long serialVersionUID = 1L;

	public MainMenu() {
		this.setTitle(Resourcer.getString("game.name"));
		this.setSize(610, 550);
		this.setPreferredSize(this.getSize());
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setLayout(new BorderLayout());
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(null);

		JLabel backgroundImageLabel = new JLabel(
				ImageWorker.getBackgroundImage(getSize()));
		backgroundImageLabel
				.setSize(backgroundImageLabel.getIcon().getIconWidth(),
						backgroundImageLabel.getIcon().getIconHeight());
		JButton playButton = new JButton(Resourcer.getString("play"));
		playButton.setBounds((getSize().width - BUTTON_WIDTH) / 2,
				getHeight() / 2, BUTTON_WIDTH, BUTTON_HEIGHT);
		playButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent mouseEvent) {
				startGame();
			}
		});
		JButton exitButton = new JButton(Resourcer.getString("exit"));
		exitButton.setBounds(playButton.getLocation().x, BUTTON_HEIGHT + INTENT
				+ playButton.getLocation().y, BUTTON_WIDTH, BUTTON_HEIGHT);
		exitButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent mouseEvent) {
				System.exit(0);
			}
		});
		mainPanel.add(playButton);
		mainPanel.add(exitButton);

		mainPanel.add(backgroundImageLabel);
		this.getContentPane().add(mainPanel);
		this.pack();
	}

	private void startGame() {
		this.setVisible(false);
		Model model = new Model();
		Controller controller = new Controller(model);
		new View(model, controller);
		model.start();
	}
}
