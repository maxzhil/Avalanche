package view;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import utils.ImageWorker;
import controller.Controller;
import model.Model;
import model.Resourcer;

public class MainMenu extends JFrame {

	private static final long serialVersionUID = 1L;

	public MainMenu() {
		this.setTitle(Resourcer.getString("game.name"));
		this.setSize(Integer.parseInt(Resourcer.getString("main.menu.width")),
				Integer.parseInt(Resourcer.getString("main.menu.height")));
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
		playButton
				.setBounds((getSize().width - Integer.parseInt(Resourcer
						.getString("main.menu.button.width"))) / 2,
						getHeight() / 2, Integer.parseInt(Resourcer
								.getString("main.menu.button.width")), Integer
								.parseInt(Resourcer
										.getString("main.menu.button.height")));
		playButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent mouseEvent) {
				startGame();
			}
		});
		JButton exitButton = new JButton(Resourcer.getString("exit"));
		exitButton
				.setBounds(
						playButton.getLocation().x,
						Integer.parseInt(Resourcer
								.getString("main.menu.button.height"))
								+ Integer.parseInt(Resourcer
										.getString("main.menu.intent"))
								+ playButton.getLocation().y, Integer
								.parseInt(Resourcer
										.getString("main.menu.button.width")),
						Integer.parseInt(Resourcer
								.getString("main.menu.button.height")));
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
