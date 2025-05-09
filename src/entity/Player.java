package entity;

import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.swing.SwingUtilities;

import main.Direction;
import main.GamePanel;
import main.KeyHandler;
import object.GameObject;

public class Player extends Entity {
	
	KeyHandler keyH;
	
	GameObject inventory[] = new GameObject[8];
	int tileSize = 0;
	int spriteCounter = 0;
	public int cameraX, cameraY;
	public Boolean inventoryIsOpen = false;
	public Boolean canToggleInventory = true;

	Point mousePosition = MouseInfo.getPointerInfo().getLocation();
	
	public Player(GamePanel gp, KeyHandler keyH) {
		super(gp, "/player/boy");
		this.gp = gp;
		this.keyH = keyH;
		this.tileSize = gp.tileSize;
		this.collision = new Rectangle();
		
		this.collision.height = 10;
		this.collision.width = gp.tileSize;
		this.collision.x = 0;
		this.collision.y = gp.tileSize - this.collision.height;
		
		this.cameraX = gp.screenWidth/2;
		this.cameraY = gp.screenHeight/2;
		
		this.hitBox = new Rectangle();
		this.hitBox.x = 8;
		this.hitBox.y = 16;
		this.hitBox.width = 32;
		this.hitBox.height = 32;
		
		this.solidAreaDefaultX = this.hitBox.x;
		this.solidAreaDefaultY = this.hitBox.y;
		
		setDefaultValues();
	}
	
	public void setDefaultValues() {
		worldX = gp.tileSize * 5;
		worldY = gp.tileSize * 16;
		speed = 4;
	}	
	
	public void updateMousePosition() {
		
		this.mousePosition = MouseInfo.getPointerInfo().getLocation();
		SwingUtilities.convertPointFromScreen(mousePosition, gp);
	}
		
	public void updateMovement() {
		
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
		
		if(keyH.upPressed == false && 
		   keyH.downPressed == false && 
		   keyH.leftPressed == false && 
		   keyH.rightPressed == false) {
			return;
		}
		
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
		
		// Check collision
		collisionOn = false;
		this.gp.collisiongChecker.checkTile(this);
		
		// Check object collision
		this.gp.collisiongChecker.checkObjectCollision(this, true);
		
		int currentSpeed = calculateCurrentSpeed();
		
		if (collisionOn == false) {
			if (this.movementDirection == Direction.Down) {
			    worldY += currentSpeed;
			}
			if (this.movementDirection == Direction.Left) {
			    worldX -= currentSpeed;
			}
			if (this.movementDirection == Direction.Right) {
			    worldX += currentSpeed;
			}
			if (this.movementDirection == Direction.Up) {
			    worldY -= currentSpeed;
			}

		}
		
		this.spriteCounter++;
		
		if(this.spriteCounter > 13) {
			
			if(this.spriteIndex + 1 >= this.spriteCount) {
				this.spriteIndex = 0;
			}
			else {			
				this.spriteIndex++;
			}
			this.spriteCounter = 0;
		}
	}
	
	public void pickUpObject(GameObject obj) {
		
		for(int i = 0; i < this.inventory.length; i++) {
						
			// Find empty inventory slot
			if (this.inventory[i] == null) {
				
				this.inventory[i] = obj;
				System.out.println("pick up " + obj.name);
				gp.objects.remove(obj);
				return;
			}
		}
		System.out.println("Inventory is full.");
	}
	
	private double getMouseDegreeComparedToPlayerOnScreen() {

	    double dx = this.mousePosition.x - this.gp.player.cameraX;
	    double dy = this.mousePosition.y - this.gp.player.cameraY;
	    double deg = Math.toDegrees(Math.atan2(dy, dx));
	    double result = (deg + 450) % 360;
				
		return result;
	}
	
	private int calculateCurrentSpeed() {
		
		int speedModifyer = 0;
		
		int currentSpeed = speed + speedModifyer;
		
		return currentSpeed;
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
		
		updateMousePosition();
		updateMovement();		
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
}
