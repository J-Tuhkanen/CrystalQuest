package entity;

import javax.swing.SwingUtilities;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import main.ActionMenu;
import main.CollisionInformation;
import main.Inventory;
import main.KeyHandler;
import main.MouseHandler;
import main.Enum.Direction;
import object.GameObject;
import ui.GamePanel;

public final class Player extends Entity {
	
	KeyHandler keyH;
	MouseHandler mouseH;
	
	public Inventory inventory = new Inventory();
	public final ActionMenu actionMenu = new ActionMenu();
	
	private static final int WALK_SPEED = 4;
	private static final double SPRINT_MULTIPLIER = 2.5;

	public int cameraX, cameraY;
	public Boolean inventoryIsOpen = false;
	public Boolean canToggleInventory = true;
	
	Point mousePosition = MouseInfo.getPointerInfo().getLocation();
	public CollisionInformation collisionInfo = new CollisionInformation(new ArrayList<Npc>(), new ArrayList<GameObject>());

	public Player(GamePanel gp, KeyHandler keyH, MouseHandler mouseH) {
		super(true, gp, "/player/boy");
		this.gp = gp;
		this.keyH = keyH;
		this.mouseH = mouseH;
		
		this.cameraX = gp.screenWidth/2;
		this.cameraY = gp.screenHeight/2;
		
		speed = WALK_SPEED;
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
		
		this.speed = keyH.sprintPressed ? (int)(WALK_SPEED * SPRINT_MULTIPLIER) : WALK_SPEED;

		this.updateLookDirection(isMoving);
		this.collisionInfo = this.checkCollision();

		if (isMoving && collisionOn == false) {
			this.move();
			this.updateSprite();
		}		
	}
	
	public void pickUpObject(GameObject obj) {

		// Find empty inventory slot
		if (this.inventory.TryAdd(obj) && gp.objects.remove(obj)) {

			System.out.println("pick up " + obj.name);
			return;
		}

		say("Inventory is full.");
	}

	private void interactWith(ActionMenu.Target target, Action action) {
		GameObject obj = target.object();
		if (obj != null && action == Action.Pickup) {
			pickUpObject(obj);
		}
		else if (obj != null && action == Action.Use) {
			useObject(obj);
		}
		else {
			say("Not implemented yet");
		}
	}

	private void useObject(GameObject obj) {

		if (obj.use(this)) {

			gp.playSoundEffect("unlock");
			System.out.println("used " + obj.name);
		}
		else {
			say("Hmm... nothing happens.");
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
		
		boolean menuWasOpen = actionMenu.isOpen();
		int menuMovement = keyH.consumeMenuMovement();
		if (menuWasOpen) actionMenu.move(menuMovement);
		if (keyH.consumeUsePress()) {
			if (actionMenu.isOpen()) {
				Action action = actionMenu.select();
				if (action != null) {
					interactWith(actionMenu.selectedTarget(), action);
					actionMenu.close();
					collisionInfo = checkCollision();
				}
			}
			else {
				collisionInfo = checkCollision();
				actionMenu.open(collisionInfo);
			}
		}

		updateMousePosition();
		if (actionMenu.isOpen() || menuWasOpen) {
			return;
		}
		if (inventoryIsOpen) {
			inventory.updateSelectedInventorySlot(keyH);
		}
		else {
			updateMovement();
		}
	}
	
	@Override
	public void draw(Graphics2D g) {
		
		BufferedImage image = null;		
		
		switch(this.lookDirection) {
			case Direction.Up -> image = this.up[spriteIndex];
			case Direction.Down -> image = this.down[spriteIndex];
			case Direction.Left -> image = this.left[spriteIndex];
			case Direction.Right -> image = this.right[spriteIndex];
			default -> {}
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
