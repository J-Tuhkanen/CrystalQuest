package entity;

import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;

import javax.swing.SwingUtilities;

import main.CollisionInformation;
import main.Inventory;
import main.KeyHandler;
import main.MouseHandler;
import main.Enum.Direction;
import object.GameObject;
import ui.GamePanel;

public class Player extends Entity {
	
	KeyHandler keyH;
	MouseHandler mouseH;
	
	public Inventory inventory = new Inventory();
	// public ActionMenu am = new()
	int actionIndexUpper = 0;
	int actionIndexDetailed = 0;
	
	private static final int walkSpeed = 4;
	private static final double sprintMultiplier = 2.5;

	int spriteCounter = 0;
	public int cameraX, cameraY;
	public Boolean inventoryIsOpen = false;
	public Boolean canToggleInventory = true;
	public Boolean actionMenuOpen = false;
	public Boolean canToggleActionMenu = true;
	
	Point mousePosition = MouseInfo.getPointerInfo().getLocation();
	public CollisionInformation collisionInfo = new CollisionInformation(new ArrayList<Npc>(), new ArrayList<GameObject>());
	public Dictionary<String, Action[]> actionDict = new Hashtable<>();
	
	public Player(GamePanel gp, KeyHandler keyH, MouseHandler mouseH) {
		super(true, gp, "/player/boy");
		this.gp = gp;
		this.keyH = keyH;
		this.mouseH = mouseH;
		
		this.cameraX = gp.screenWidth/2;
		this.cameraY = gp.screenHeight/2;
		
		speed = walkSpeed;
		teleportTo(gp.tileSize * 5, gp.tileSize * 16);
	}
	
	public void teleportTo(int worldX, int worldY) {

		this.worldX = worldX;
		this.worldY = worldY;
	}
	
	public void updateMousePosition() {
		
		this.mousePosition = MouseInfo.getPointerInfo().getLocation();
		SwingUtilities.convertPointFromScreen(mousePosition, gp);
	}
		
	public void updateMovement() {
		
		boolean isMoving = keyH.upPressed || 
				   keyH.downPressed || 
				   keyH.leftPressed || 
				   keyH.rightPressed;
		
		if(keyH.upPressed) {
			this.movementDirection = Direction.Up;
		}
		if(keyH.downPressed) {
			this.movementDirection = Direction.Down;
		}
		if(keyH.leftPressed) {
			this.movementDirection = Direction.Left;
		}
		if(keyH.rightPressed) {
			this.movementDirection = Direction.Right;
		}
		
		this.speed = keyH.sprintPressed ? (int)(walkSpeed * sprintMultiplier) : walkSpeed;

		this.updateLookDirection(isMoving);
		this.collisionInfo = this.checkCollision();
		this.actionDict = this.collisionInfo.getAsActions();

		if (isMoving && collisionOn == false) {
			this.move();
			this.updateSprite();
		}		
	}
	
	public void updateActionMenuMovement() {
		
		if(keyH.downPressed) {
			
		}
	}
	
	public void pickUpObject(GameObject obj) {

		// Find empty inventory slot
		if (this.inventory.TryAdd(obj) && gp.objects.remove(obj)) {

			System.out.println("pick up " + obj.name);
			return;
		}

		System.out.println("Inventory is full.");
	}

	private void interactWith(GameObject obj) {

		Action[] actions = obj.getActions();

		if (actions.length == 0) {
			return;
		}

		switch (actions[0]) {
			case Pickup:
				this.pickUpObject(obj);
				break;
			case Use:
				this.useObject(obj);
				break;
			default:
				break;
		}
	}

	private void useObject(GameObject obj) {

		if (obj.use(this)) {

			gp.playSoundEffect("unlock");
			System.out.println("used " + obj.name);
		}
		else {
			System.out.println("Nothing happens.");
		}
	}

	@Override
	public void update() {
		
		if (keyH.inventoryPressed && canToggleInventory) {
			canToggleInventory = false;
			this.inventoryIsOpen = !this.inventoryIsOpen;
		}
		else if(keyH.inventoryReleased) {
			canToggleInventory = true;
		}
		
		if(this.canToggleActionMenu && keyH.usePressed && (this.collisionInfo.npcs.size() > 0 || this.collisionInfo.gameObjects.size() > 0)) {

			if(this.actionMenuOpen && !this.collisionInfo.gameObjects.isEmpty()) {
				this.interactWith(this.collisionInfo.gameObjects.get(actionIndexDetailed));
			}
			this.canToggleActionMenu = false;
			this.actionMenuOpen = !this.actionMenuOpen;
		}
		else if(keyH.useReleased) {
			this.canToggleActionMenu = true;
		}
		else if ((this.collisionInfo.npcs.size() < 1 && this.collisionInfo.gameObjects.size() < 1)) {
			this.actionMenuOpen = false;
		}
		
		updateMousePosition();
		
		if(this.inventoryIsOpen) {
			this.inventory.updateSelectedInventorySlot(keyH);
		}
		if(this.actionMenuOpen) {
			
		}
		else {			
			updateMovement();		
		}
	}
	
	@Override
	public void draw(Graphics2D g) {
		
		BufferedImage image = null;		
		
		switch(this.lookDirection) {
			case Direction.Up:
				image = this.up[spriteIndex];
				break;
			case Direction.Down:
				image = this.down[spriteIndex];
				break;
			case Direction.Left:
				image = this.left[spriteIndex];
				break;
			case Direction.Right:
				image = this.right[spriteIndex];
				break;
			default:
				break;
		}
		
		g.drawImage(image, cameraX, cameraY, gp.tileSize, gp.tileSize, null);
	}
	
	private void updateLookDirection(boolean isMoving) {
		
		if(this.mouseH.RightMouseKeyPressed) {
			
			double degree = this.getMouseDegreeComparedToPlayerOnScreen();
			
			if (degree >= 45 && degree < 135) {
				this.lookDirection = Direction.Right;
			}
			else if(degree >= 135 && degree < 225) {			
				this.lookDirection = Direction.Down;		
			}
			else if(degree >= 225 && degree < 315) {			
				this.lookDirection = Direction.Left;	
			}
			else {			
				this.lookDirection = Direction.Up;
			}
		}
		else if(isMoving) {
			this.lookDirection = this.movementDirection;
		}
	}
	
	private double getMouseDegreeComparedToPlayerOnScreen() {

	    double dx = this.mousePosition.x - this.gp.player.cameraX;
	    double dy = this.mousePosition.y - this.gp.player.cameraY;
	    double deg = Math.toDegrees(Math.atan2(dy, dx));
	    double result = (deg + 450) % 360;
				
		return result;
	}
}
