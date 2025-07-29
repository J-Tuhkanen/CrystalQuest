package main;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class UIPanel extends JPanel {

	private static final long serialVersionUID = 6302459241288572245L;

	private final JLabel _hintLabel = new JLabel();
	private final GamePanel _gamePanel;
	
	public UIPanel(GamePanel gamePanel) {
		
		_gamePanel = gamePanel;
		setupHintLabel();
		this.setOpaque(false);
		this.setLayout(null);
	}

	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
	}
	

	public void writeHint(String text) {
		_hintLabel.setText(text);
	}
	
	private void setupHintLabel() {

		_hintLabel.setLayout(new FlowLayout());
		_hintLabel.setBounds(0, (int)(this._gamePanel.screenHeight * 0.8), this._gamePanel.screenWidth, 100);
		_hintLabel.setHorizontalAlignment(SwingConstants.CENTER);
		_hintLabel.setForeground(new Color(227, 217, 209));
		_hintLabel.setFont(new Font("Ariel", 0, 35));
		this.add(_hintLabel);
	}	
}
