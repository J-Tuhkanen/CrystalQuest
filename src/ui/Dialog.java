package ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

import main.Enum.GameState;

public class Dialog extends JPanel {

	private static final long serialVersionUID = -3208584015001474129L;
	private final GamePanel _gamePanel;
	
	public Dialog(GamePanel gp) {
		this._gamePanel = gp;
		this.setOpaque(false);
	}
	
	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		
		if(this._gamePanel.gameState == GameState.DialogState) 
			this.drawDialog(graphics);		
	}
	
	public void drawDialog(Graphics graphics) {
		
		//this.drawThemedElement((Graphics2D)graphics, 0,0, this.getWidth(), this.getHeight(), new Color(0,0,0));
	}
	
	public int getWidth() {
		return this._gamePanel.tileSize * 13;
	}
	
	public int getHeight() {
		return this._gamePanel.tileSize * 5;
	}
	
	private void drawThemedElement(Graphics2D g2, int x, int y, int width, int height, Color fillColor) {
        
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
        // Fill with white background
        g2.setColor(fillColor);
        g2.fillRoundRect(x, y, width, height, 0, 0);
        
        // Draw border
        g2.setColor(new Color(255,255,255,200));
        g2.setStroke(new BasicStroke(10)); // border thickness
        g2.drawRoundRect(x, y, width, height, 0, 0);
	}
	
}
