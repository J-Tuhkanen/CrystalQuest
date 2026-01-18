package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class Hint extends JPanel {

	private static final long serialVersionUID = 9011400371067015922L;
	private final JLabel _hintLabel = new JLabel();
	private final GamePanel _gamePanel;
	
	public Hint(GamePanel gp) {
		this._gamePanel = gp;
		
		_hintLabel.setForeground(new Color(227, 217, 209));
		_hintLabel.setFont(new Font("Ariel", 0, 35));
		this.add(_hintLabel);
		this.setOpaque(false);
	}

	public void paintComponent(Graphics graphics) {		
		super.paintComponent(graphics);
		_hintLabel.setText(this._gamePanel.actionHintText);
	}
}
