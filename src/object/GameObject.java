package object;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import entity.Action;
import entity.Player;
import ui.GamePanel;

public abstract class GameObject {

	public BufferedImage image;
	public String name;
	public int id;
	public boolean collision = false;
	public int worldX, worldY;
	public Rectangle solidArea = new Rectangle(0,0,48,48);

	public void draw(Graphics2D graphics, GamePanel gp) {
		
		if (worldX + gp.tileSize > gp.player.worldX - gp.player.cameraX &&
		    worldX - gp.tileSize < gp.player.worldX + gp.player.cameraX &&
		    worldY + gp.tileSize > gp.player.worldY - gp.player.cameraY &&
		    worldY - gp.tileSize < gp.player.worldY + gp.player.cameraY) {
		
			int screenX = worldX - gp.player.worldX + gp.player.cameraX;
			int screenY = worldY - gp.player.worldY + gp.player.cameraY;
			
			graphics.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
		}		
	}
	
	public Action[] getActions() {
		return new Action[0];
	}

	// Attempt to use this object, e.g. unlocking a door with a key from the player's inventory.
	// Returns whether the use had an effect.
	public boolean use(Player player) {
		return false;
	}
}
