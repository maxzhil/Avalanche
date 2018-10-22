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

import model.Model;
import model.Resourcer;

public class MainMenu extends JFrame {
	private static String libraryPath = "src" + File.separator + "resources";
	private static final int BUTTON_WIDTH = 100;
	private static final int BUTTON_HEIGHT = 30;
	private static final int INTENT = 30;
	private static final long serialVersionUID = 1L;

	public MainMenu() {
		this.setTitle("Avalanche");
		this.setSize(610, 550);
		this.setPreferredSize(this.getSize());
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setLayout(new BorderLayout());
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(null);

		JLabel label = new JLabel(getLogo(getSize()));
		label.setSize(label.getIcon().getIconWidth(), label.getIcon()
				.getIconHeight());
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

		mainPanel.add(label);
		this.getContentPane().add(mainPanel);
		this.pack();
	}

	private static ImageIcon getLogo(Dimension size) {
		Image image = getImage(Resourcer.getString("images.menu.background"));

		return new ImageIcon(image);
	}

	public static Image getImage(String fileName) {
		File file = new File(libraryPath + File.separator + fileName);
		Image image = null;
		try {
			image = ImageIO.read(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}

	private void loadGame() {
		this.setVisible(false);
		Model model = new Model();
		Controller controller = new Controller(model);
		new View(model, controller);
		model.start();
	}
}
