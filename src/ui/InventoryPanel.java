package ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import main.Enum.GameState;
import main.Enum.InventoryMode;
import object.GameObject;

public class InventoryPanel extends JPanel {

	private static final long serialVersionUID = 2865297731120918515L;
	BufferedImage inventoryImage;
	private final GamePanel _gamePanel;
	
	public InventoryPanel(GamePanel gp) {
		
		this._gamePanel = gp;
		try {
			// Load images here
			inventoryImage = ImageIO.read(getClass().getResourceAsStream("/ui/inventory.png")); 
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	// Screen-space bounds of the inventory panel.
	public static Rectangle getInventoryBounds(GamePanel gp) {
		
		int inventoryWidth = gp.tileSize * 13;
		int inventoryHeight = gp.tileSize * 8;
		int inventoryX = (gp.screenWidth - inventoryWidth) / 2;
		int inventoryY = (gp.screenHeight - inventoryHeight) / 2;
		return new Rectangle(inventoryX, inventoryY, inventoryWidth, inventoryHeight);
	}
	
	// Bounds of a slot, relative to the inventory panel.
	public static Rectangle getSlotBounds(GamePanel gp, int rowIndex, int columnIndex) {
		
		int inventoryWidth = gp.tileSize * 13;
		int inventoryHeight = gp.tileSize * 8;
		int inventorySlotSize = inventoryWidth / 7;
		int slotMargin = (inventoryWidth - inventorySlotSize * 6) / 7;
		
		int slotX = slotMargin + slotMargin * columnIndex + inventorySlotSize * columnIndex;
		int slotY = inventoryHeight / 4 + slotMargin + slotMargin * rowIndex + inventorySlotSize * rowIndex;
		return new Rectangle(slotX, slotY, inventorySlotSize, inventorySlotSize);
	}
	
	public void paintComponent(Graphics graphics) {
		
		boolean inventoryIsOpenAndGameIsRunning = 
				this._gamePanel.player.inventoryIsOpen && 
				this._gamePanel.gameState == GameState.Running;
		
		if(inventoryIsOpenAndGameIsRunning) {
			this.drawInventory(graphics);
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
		
		drawThemedElement(graphics, 0, 0, inventoryWidth, inventoryHeight);
		
		var player = this._gamePanel.player;
		// While the world menu is open, no slot is highlighted.
		boolean worldMenuOpen = player.actionMenu.isOpen() && !player.actionMenu.isItemMenu();
		int combineSourceSlot = player.inventoryMode == InventoryMode.Combining ? player.combineSourceSlot : -1;
		
		// Inventory has 12 slots; 2 rows of slots and each row has 6 slots.
		for(int i = 0; i < 2; i++) {	
			for(int y = 0; y < _gamePanel.player.inventory.items.length / 2; y++) {
				
				Rectangle slot = getSlotBounds(this._gamePanel, i, y);
				int slotX = slot.x;
				int slotY = slot.y;
				int inventorySlotSize = slot.width;
				
				boolean isSelectedSlot = !worldMenuOpen && player.inventory.selectedRowIndex == i && player.inventory.selectedColumnIndex == y;
				boolean isCombineSource = i * 6 + y == combineSourceSlot;
				
				if(isCombineSource) {
					drawThemedElement(graphics, slotX, slotY, inventorySlotSize, inventorySlotSize, new Color(214, 180, 60, 230));
					if(isSelectedSlot) {
						graphics.setColor(Color.WHITE);
						graphics.setStroke(new BasicStroke(4));
						graphics.drawRoundRect(slotX, slotY, inventorySlotSize, inventorySlotSize, 30, 30);
					}
				}
				else if(isSelectedSlot) {
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
}
