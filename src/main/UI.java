package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import main.Enum.GameState;
import object.GameObject;

public class UI {
	
	GamePanel gamePanel;
	final Font arial_plain_40 = new Font("Arial", Font.PLAIN, 40);
	BufferedImage inventoryImage;
		
	public UI(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
		try {
			
			inventoryImage = ImageIO.read(getClass().getResourceAsStream("/ui/inventory.png")); 
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void draw(Graphics2D graphics) {
		
		graphics.setFont(arial_plain_40);
		graphics.setColor(Color.white);
		
		if(this.gamePanel.player.inventoryIsOpen) {
			drawInventory(graphics, this.gamePanel.player.inventory);
		}
		if(this.gamePanel.gameState == GameState.Paused) {
			
			graphics.setFont(graphics.getFont().deriveFont(Font.BOLD, 80));
			this.drawTextOnCenter("PAUSE", graphics);
		}
	}
	
	public void drawInventory(Graphics2D graphics, Inventory inventory) {
		
		// TODO: Optimize this later with once calculated options when screen size changes
		int inventoryWidth = this.gamePanel.tileSize * 13;
		int inventoryHeight = this.gamePanel.tileSize * 8;
		int inventoryX = (this.gamePanel.screenWidth - inventoryWidth) / 2 ;
		int inventoryY = (this.gamePanel.screenHeight - inventoryHeight) / 2 ;
		
		drawThemedElement(graphics, inventoryX, inventoryY, inventoryWidth, inventoryHeight);
		
		int inventorySlotSize = inventoryWidth / 7;
		int slotMargin = (inventoryWidth - inventorySlotSize * 6) / 7;
		
		// Inventory has 12 slots; 2 rows of slots and each row has 6 slots.
		for(int i = 0; i < 2; i++) {	
			for(int y = 0; y < inventory.items.length / 2; y++) {
				
				int slotX = inventoryX + slotMargin + slotMargin * y + inventorySlotSize * y;
				int slotY = inventoryY + inventoryHeight / 4 + slotMargin + slotMargin * i + inventorySlotSize * i;
				
				boolean isSelectedSlot = inventory.selectedRowIndex == i && inventory.selectedColumnIndex == y;
				
				if(isSelectedSlot) {
					drawThemedElement(graphics, slotX, slotY, inventorySlotSize, inventorySlotSize, new Color(255, 255, 255, 230));					
				}
				else {					
					drawThemedElement(graphics, slotX, slotY, inventorySlotSize, inventorySlotSize, new Color(186, 175, 159, 230));
				}
				GameObject objInSlot = inventory.getItemAt(i, y);
				if(objInSlot != null) {
					
					int imageX = slotX + (inventorySlotSize - objInSlot.image.getWidth()) / 4 ;
					int imageY = slotY + (inventorySlotSize - objInSlot.image.getHeight()) / 4 ;
					
					graphics.drawImage(objInSlot.image, imageX, imageY, gamePanel.tileSize, gamePanel.tileSize, null, gamePanel);
				}
			}
		}
	}
	
	private void drawTextOnCenter(String text, Graphics2D graphics) {
		
		int length = (int)graphics.getFontMetrics().getStringBounds(text, graphics).getWidth();
		
		graphics.drawString(text, 
				this.gamePanel.screenWidth / 2 - length / 2,
				this.gamePanel.screenHeight / 2);
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
}