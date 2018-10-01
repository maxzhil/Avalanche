package view;

import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import view.panel.BlockPanel;
import view.panel.CharacterPanel;
import view.panel.EarthPanel;

import controller.Controller;
import controller.KeyHandler;

import model.AddBlockListener;
import model.Block;
import model.Model;

public class View extends JFrame implements AddBlockListener {

	private Model model;
	private Controller controller;
	private JPanel mainPanel;
	private CharacterPanel characterPanel;
	private EarthPanel earthPanel;
	private List<BlockPanel> blockPanels = new ArrayList<BlockPanel>();

	public View(Model model, final Controller controller) {
		super();
		this.model = model;
		this.controller = controller;
		this.setTitle("01-01-Zhilenko");
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainPanel = new JPanel();
		mainPanel.setPreferredSize(new Dimension(model.getGameField()
				.getWidth(), model.getGameField().getHeight()));
		add(mainPanel);

		characterPanel = new CharacterPanel();
		earthPanel = new EarthPanel();
		mainPanel.add(characterPanel);
		mainPanel.add(earthPanel);

		this.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				new KeyHandler(controller).keyPressed(e.getKeyCode());
			}
		});
		model.getEarth().addListener(earthPanel);
		model.getGameCharacter().addListener(characterPanel);
		model.addBlockListener(this);
		pack();
	}

	@Override
	public void addBlock(Block block) {
		BlockPanel blockPanel = new BlockPanel();
		block.addListener(blockPanel);
		blockPanels.add(blockPanel);
		mainPanel.add(blockPanel);

	}

}
