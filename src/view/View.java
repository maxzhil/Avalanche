package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import view.panel.AvalanchePanel;
import view.panel.BlockPanel;
import view.panel.CharacterPanel;
import view.panel.EarthPanel;
import view.panel.HeightScorePanel;
import view.panel.RemoteBlocksCountPanel;
import controller.Controller;
import controller.KeyHandler;
import model.Block;
import model.Model;
import model.Resourcer;
import model.listeners.AddBlockListener;
import model.listeners.DeleteBlockListener;
import model.listeners.GameObjectListener;
import model.listeners.GameOverListener;

public class View extends JFrame implements AddBlockListener,
		DeleteBlockListener, GameOverListener {

	private static final long serialVersionUID = 1L;
	private Model model;
	private Controller controller;
	private JPanel gameFieldPanel;
	private JPanel informationPanel;
	private CharacterPanel characterPanel;
	private EarthPanel earthPanel;
	private AvalanchePanel avalanchePanel;
	private HeightScorePanel heightScorePanel;
	private RemoteBlocksCountPanel remoteBlocksCountPanel;

	private List<BlockPanel> blockPanels = new ArrayList<BlockPanel>();

	public View(Model model, final Controller controller) {
		super();
		this.model = model;
		this.controller = controller;
		this.setTitle(Resourcer.getString("view.title"));
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setLayout(new BorderLayout());
		characterPanel = new CharacterPanel();
		earthPanel = new EarthPanel();
		avalanchePanel = new AvalanchePanel();
		remoteBlocksCountPanel = new RemoteBlocksCountPanel();
		heightScorePanel = new HeightScorePanel();
		initializeGameFieldPanel();
		initializeInformationPanel();
		this.getContentPane().add(informationPanel, BorderLayout.NORTH);
		this.getContentPane().add(gameFieldPanel, BorderLayout.CENTER);

		this.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				new KeyHandler(controller).keyPressed(e.getKeyCode());
			}
		});

		initializeListeners();
		pack();
		this.setLocationRelativeTo(null);
	}

	@Override
	public void addBlock(Block block) {
		BlockPanel blockPanel = new BlockPanel(block.getColor());
		block.addListener(blockPanel);
		blockPanels.add(blockPanel);
		gameFieldPanel.add(blockPanel);
		gameFieldPanel.repaint();
	}

	@Override
	public void deleteBlockListener(Block block) {
		BlockPanel blockPanel = null;
		for (GameObjectListener gameObjectListener : block
				.getGameObjectListeners()) {
			blockPanel = (BlockPanel) gameObjectListener;
		}
		gameFieldPanel.remove(blockPanel);
		gameFieldPanel.repaint();
		blockPanels.remove(blockPanel);
	}

	@Override
	public void gameOver() {
		JOptionPane.showMessageDialog(null, Resourcer.getString("view.game.over"));
		this.setVisible(false);
		new MainMenu();

	}

	private void initializeGameFieldPanel() {
		gameFieldPanel = new JPanel();
		gameFieldPanel.setPreferredSize(new Dimension(model.getGameField()
				.getDimension().width,
				model.getGameField().getDimension().height));
		gameFieldPanel.add(characterPanel);
		gameFieldPanel.add(earthPanel);
		gameFieldPanel.add(avalanchePanel);
	}

	private void initializeInformationPanel() {
		informationPanel = new JPanel();
		informationPanel.setBackground(Color.WHITE);
		informationPanel.add(new JLabel(Resourcer.getString("view.info")));
		informationPanel.add(heightScorePanel);
		informationPanel.add(remoteBlocksCountPanel);
	}

	private void initializeListeners() {
		controller.addHeightScoreListener(heightScorePanel);
		controller.addRemoteBlocksCountListener(remoteBlocksCountPanel);
		controller.addEarthListener(earthPanel);
		controller.addCharacterListener(characterPanel);
		controller.addAvalancheListener(avalanchePanel);
		controller.addBlockListener(this);
		controller.addGameOverListener(this);
		controller.addDeleteBlockListener(this);
	}
}
