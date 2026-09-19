package object;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Action;
import entity.Player;
import ui.GamePanel;

public abstract class GameObject {

	private static final Action[] INVENTORY_ACTIONS = new Action[] {
		Action.Use,
		Action.Examine,
		Action.Combine,
		Action.Drop
	};

	public BufferedImage image;
	public String name;
	public int id;
	public boolean collision = false;
	public int worldX, worldY;
	public Rectangle solidArea = new Rectangle(0,0,48,48);
	protected String description = "Nothing special about it.";

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
	
	// Actions offered when the object is in the world.
	public Action[] getActions() {
		return new Action[0];
	}

	// Actions offered when the object is selected in the inventory.
	public Action[] getInventoryActions() {
		return INVENTORY_ACTIONS;
	}

	public String examine() {
		return this.description;
	}

	// Attempt to use this object, either in the world or from the inventory,
	// e.g. unlocking a door with a key from the player's inventory.
	// Returns whether the use had an effect.
	public boolean use(Player player) {
		return false;
	}

	// Attempt to combine this object with another one.
	// Returns null when the two can't be combined.
	public CombineResult combineWith(GameObject other, Player player) {
		return null;
	}

	protected static BufferedImage loadImage(String path) {
		try {
			return ImageIO.read(GameObject.class.getResourceAsStream(path));
		}
		catch(IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
