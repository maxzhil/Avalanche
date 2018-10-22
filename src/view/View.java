package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import view.panel.AvalanchePanel;
import view.panel.BlockPanel;
import view.panel.CharacterPanel;
import view.panel.EarthPanel;
import controller.Controller;
import controller.KeyHandler;
import model.Block;
import model.Model;
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
	private CharacterPanel characterPanel;
	private EarthPanel earthPanel;
	private AvalanchePanel avalanchePanel;
	private List<BlockPanel> blockPanels = new ArrayList<BlockPanel>();

	public View(Model model, final Controller controller) {
		super();
		this.model = model;
		this.controller = controller;
		this.setTitle("01-06-Zhilenko");
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameFieldPanel = new JPanel();
		gameFieldPanel.setPreferredSize(new Dimension(model.getGameField()
				.getWidth(), model.getGameField().getHeight()));
		this.setLayout(new BorderLayout());
		this.getContentPane().add(gameFieldPanel, BorderLayout.CENTER);

		characterPanel = new CharacterPanel();
		earthPanel = new EarthPanel();
		avalanchePanel = new AvalanchePanel();
		gameFieldPanel.add(characterPanel);
		gameFieldPanel.add(earthPanel);
		gameFieldPanel.add(avalanchePanel);
		this.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				new KeyHandler(controller).keyPressed(e.getKeyCode());
			}
		});
		controller.addListenerEarth(earthPanel);
		controller.addListenerCharacter(characterPanel);
		controller.addListenerAvalanche(avalanchePanel);
		controller.addBlockListener(this);
		controller.addGameOverListener(this);
		controller.addDeleteBlockListener(this);
		pack();
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
		System.out.println("delete Block");
	}

	@Override
	public void gameOver() {
		JOptionPane.showMessageDialog(null, "Game over");
		System.exit(0);
	}
}
