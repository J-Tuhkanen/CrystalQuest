package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import main.Enum.GameState;
import object.GameObject;

public class UIPanel extends JPanel {

	private static final long serialVersionUID = 6302459241288572245L;

	private final JLabel _hintLabel = new JLabel();
	private final GamePanel _gamePanel;
	
	public UIPanel(GamePanel gamePanel) {		
		_gamePanel = gamePanel;

		_hintLabel.setBounds(0, 0, this._gamePanel.screenWidth, 100);
		_hintLabel.setHorizontalAlignment(SwingConstants.CENTER);
		_hintLabel.setForeground(new Color(227, 217, 209));
		_hintLabel.setFont(new Font("Ariel", 0, 35));
		this.add(_hintLabel);
		
		this.setOpaque(false);
		this.setLayout(new FlowLayout());		
	}

	public void paintComponent(Graphics graphics) {		
		super.paintComponent(graphics);
		_hintLabel.setText(this._gamePanel.actionHintText);
		
		if(this._gamePanel.player.inventoryIsOpen) {
			this.drawInventory(graphics);
		}
		if(this._gamePanel.gameState == GameState.Paused) {
			this.drawGameMenu(graphics);
		}
	}
	
	private void drawThemedElement(Graphics2D g2, int x, int y, int width, int height) {
        
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		int dialogAlpha = 230;
        int arcWidth = 30, arcHeight = 30; // corner radius
		
        // Fill with white background
        g2.setColor(new Color(148, 100, 33, dialogAlpha));
        g2.fillRoundRect(x, y, width, height, arcWidth, arcHeight);
        
        // Draw black border
        g2.setColor(new Color(0,0,0,dialogAlpha));
        g2.setStroke(new BasicStroke(2)); // border thickness
        g2.drawRoundRect(x, y, width, height, arcWidth, arcHeight);
	}
	
	private void drawThemedElement(Graphics2D g2, int x, int y, int width, int height, Color fillColor) {
        
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		int dialogAlpha = 230;
        int arcWidth = 30, arcHeight = 30; // corner radius
		
        // Fill with white background
        g2.setColor(fillColor);
        g2.fillRoundRect(x, y, width, height, arcWidth, arcHeight);
        
        // Draw black border
        g2.setColor(new Color(0,0,0,dialogAlpha));
        g2.setStroke(new BasicStroke(2)); // border thickness
        g2.drawRoundRect(x, y, width, height, arcWidth, arcHeight);
	}
	
	private void drawInventory(Graphics g) {
		
		Graphics2D graphics = (Graphics2D)g;
		
		// TODO: Optimize this later with once calculated options when screen size changes
		int inventoryWidth = this._gamePanel.tileSize * 13;
		int inventoryHeight = this._gamePanel.tileSize * 8;
		int inventoryX = (this._gamePanel.screenWidth - inventoryWidth) / 2 ;
		int inventoryY = (this._gamePanel.screenHeight - inventoryHeight) / 2 ;
		
		drawThemedElement(graphics, inventoryX, inventoryY, inventoryWidth, inventoryHeight);
		
		int inventorySlotSize = inventoryWidth / 7;
		int slotMargin = (inventoryWidth - inventorySlotSize * 6) / 7;
		
		// Inventory has 12 slots; 2 rows of slots and each row has 6 slots.
		for(int i = 0; i < 2; i++) {	
			for(int y = 0; y < _gamePanel.player.inventory.items.length / 2; y++) {
				
				int slotX = inventoryX + slotMargin + slotMargin * y + inventorySlotSize * y;
				int slotY = inventoryY + inventoryHeight / 4 + slotMargin + slotMargin * i + inventorySlotSize * i;
				
				boolean isSelectedSlot = _gamePanel.player.inventory.selectedRowIndex == i && _gamePanel.player.inventory.selectedColumnIndex == y;
				
				if(isSelectedSlot) {
					drawThemedElement(graphics, slotX, slotY, inventorySlotSize, inventorySlotSize, new Color(255, 255, 255, 230));					
				}
				else {					
					drawThemedElement(graphics, slotX, slotY, inventorySlotSize, inventorySlotSize, new Color(186, 175, 159, 230));
				}
				GameObject objInSlot = _gamePanel.player.inventory.getItemAt(i, y);
				if(objInSlot != null) {
					
					int imageX = slotX + (inventorySlotSize - objInSlot.image.getWidth()) / 4 ;
					int imageY = slotY + (inventorySlotSize - objInSlot.image.getHeight()) / 4 ;
					
					graphics.drawImage(objInSlot.image, imageX, imageY, this._gamePanel.tileSize, this._gamePanel.tileSize, null, this._gamePanel);
				}
			}
		}
	}
	

	
	private void drawGameMenu(Graphics graphics) {
		this._hintLabel.setText("Draw game menu here");
	}
}
