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
import main.Enum.InventoryMode;
import object.CombineResult;
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
	public InventoryMode inventoryMode = InventoryMode.Browsing;
	// Slot of the item being combined while in Combining mode, otherwise -1.
	public int combineSourceSlot = -1;
	
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
		if (this.inventory.TryAdd(obj) && gp.getGameObjectManager().despawn(obj)) {

			System.out.println("pick up " + obj.name);
			return;
		}

		say("Inventory is full.");
	}

	// Removes an object from the game: from the inventory if it's there, otherwise from the world.
	public void consume(GameObject obj) {

		if (!this.inventory.remove(obj)) {
			gp.getGameObjectManager().despawn(obj);
		}
	}

	public void playSoundEffect(String soundName) {
		gp.playSoundEffect(soundName);
	}

	private void interactWith(ActionMenu.Target target, Action action) {
		GameObject obj = target.object();
		if (obj != null && action == Action.Pickup) {
			pickUpObject(obj);
		}
		else if (obj != null && action == Action.Use) {
			useObject(obj);
		}
		else if (action == Action.Examine) {
			say(obj != null ? obj.examine() : target.npc().examine());
		}
		else {
			say("Not implemented yet");
		}
	}

	private void interactWithItem(ActionMenu.Target target, Action action) {

		GameObject item = target.object();
		int slot = target.inventorySlot();

		// The item may have moved or disappeared since the menu was opened.
		if (this.inventory.items[slot] != item) {
			return;
		}

		switch (action) {
			case Use -> useObject(item);
			case Examine -> say(item.examine());
			case Drop -> dropItem(slot);
			case Combine -> {
				this.inventoryMode = InventoryMode.Combining;
				this.combineSourceSlot = slot;
			}
			default -> say("Not implemented yet");
		}
	}

	private void useObject(GameObject obj) {

		var bubbleBefore = this.chatBubble;

		if (obj.use(this)) {
			System.out.println("used " + obj.name);
		}
		// Only fall back to the generic message if the object didn't explain itself.
		else if (this.chatBubble == bubbleBefore) {
			say("Hmm... nothing happens.");
		}
	}

	private void dropItem(int slot) {

		GameObject item = this.inventory.RemoveAt(slot);

		if (item != null) {
			gp.getGameObjectManager().spawn(item, this.worldX, this.worldY);
		}
	}

	private void combineItems(int sourceSlot, int otherSlot) {

		GameObject source = this.inventory.items[sourceSlot];
		GameObject other = this.inventory.items[otherSlot];
		this.inventoryMode = InventoryMode.Browsing;
		this.combineSourceSlot = -1;

		CombineResult result = source.combineWith(other, this);
		if (result == null) {
			result = other.combineWith(source, this);
		}
		if (result == null) {
			say("I can't combine those.");
			return;
		}

		// Check for space before changing anything.
		int freeSlots = this.inventory.freeSlotCount();
		for (GameObject removed : result.removed()) {
			if (this.inventory.indexOf(removed) >= 0) {
				freeSlots++;
			}
		}
		if (result.added().size() > freeSlots) {
			say("Inventory is full.");
			return;
		}

		for (GameObject removed : result.removed()) {
			consume(removed);
		}

		int firstNewSlot = -1;
		for (GameObject added : result.added()) {
			this.inventory.TryAdd(added);
			if (firstNewSlot < 0) {
				firstNewSlot = this.inventory.indexOf(added);
			}
		}
		if (firstNewSlot >= 0) {
			this.inventory.setSelectedIndex(firstNewSlot);
		}
		if (result.message() != null) {
			say(result.message());
		}
	}

	// E with the inventory open (and no menu open).
	private void handleInventoryUse() {

		int slot = this.inventory.getSelectedIndex();
		GameObject item = this.inventory.items[slot];

		if (this.inventoryMode == InventoryMode.Combining) {
			if (slot == this.combineSourceSlot) {
				this.inventoryMode = InventoryMode.Browsing;
				this.combineSourceSlot = -1;
			}
			else if (item != null) {
				combineItems(this.combineSourceSlot, slot);
			}
			return;
		}

		if (item != null) {
			this.actionMenu.openForItem(item, slot);
			this.inventoryMode = InventoryMode.ItemMenu;
		}
	}

	// Escape steps back one level: menu -> inventory mode -> closed inventory.
	private void handleEscape() {

		if (this.actionMenu.isOpen()) {
			this.actionMenu.close();
			if (this.inventoryMode == InventoryMode.ItemMenu) {
				this.inventoryMode = InventoryMode.Browsing;
			}
		}
		else if (this.inventoryIsOpen) {
			if (this.inventoryMode == InventoryMode.Browsing) {
				this.inventoryIsOpen = false;
			}
			this.inventoryMode = InventoryMode.Browsing;
			this.combineSourceSlot = -1;
		}
	}

	private void toggleInventory() {

		// Closing the inventory also closes the item menu, but leaves a world menu alone.
		if (this.actionMenu.isItemMenu()) {
			this.actionMenu.close();
		}
		this.inventoryIsOpen = !this.inventoryIsOpen;
		this.inventoryMode = InventoryMode.Browsing;
		this.combineSourceSlot = -1;
	}

	@Override
	public void update() {
		
		if (keyH.inventoryPressed && canToggleInventory) {
			canToggleInventory = false;
			toggleInventory();
		}
		else if(keyH.inventoryReleased) {
			canToggleInventory = true;
		}
		
		if (keyH.consumeEscapePress()) {
			handleEscape();
		}
		
		boolean menuWasOpen = actionMenu.isOpen();
		int menuMovement = keyH.consumeMenuMovement();
		if (menuWasOpen) actionMenu.move(menuMovement);
		if (keyH.consumeUsePress()) {
			if (actionMenu.isOpen()) {
				Action action = actionMenu.select();
				if (action != null) {
					var target = actionMenu.selectedTarget();
					actionMenu.close();
					if (target.inventorySlot() >= 0) {
						this.inventoryMode = InventoryMode.Browsing;
						interactWithItem(target, action);
					}
					else {
						interactWith(target, action);
					}
					collisionInfo = checkCollision();
				}
			}
			else if (inventoryIsOpen) {
				handleInventoryUse();
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
