package view.panel;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import model.Resourcer;
import model.listeners.HeightScoreListener;

public class HeightScorePanel extends JPanel implements HeightScoreListener {

	private static final long serialVersionUID = 1L;
	private JLabel heightScoreText;

	public HeightScorePanel() {
		super();
		setLocation(0, 0);
		setPreferredSize(new Dimension(100, 20));
		setLayout(null);
		setOpaque(false);
		heightScoreText = new JLabel();
		heightScoreText.setForeground(Color.BLACK);
		heightScoreText.setLocation(10, 0);
		heightScoreText.setSize(100, 20);
		this.add(heightScoreText);
	}

	@Override
	public void updateHeightScore(int heightScore) {

		heightScoreText.setText(String.format("%s %d",
				Resourcer.getString("height.score"), heightScore));
	}

}
