package main;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class ActionHintPanel extends JPanel {

	private static final long serialVersionUID = 6302459241288572245L;

	private final JLabel _hintLabel = new JLabel();
	private final GamePanel _gamePanel;
	
	public ActionHintPanel(GamePanel gamePanel) {		
		_gamePanel = gamePanel;
		setupHintLabel();
		this.setOpaque(false);
		this.setLayout(new FlowLayout());		
	}

	public void paintComponent(Graphics graphics) {		
		super.paintComponent(graphics);
		_hintLabel.setText(this._gamePanel.actionHintText);
	}

	private void setupHintLabel() {
		_hintLabel.setBounds(0, 0, this._gamePanel.screenWidth, 100);
		_hintLabel.setHorizontalAlignment(SwingConstants.CENTER);
		_hintLabel.setForeground(new Color(227, 217, 209));
		_hintLabel.setFont(new Font("Ariel", 0, 35));
		this.add(_hintLabel);
	}	
}
