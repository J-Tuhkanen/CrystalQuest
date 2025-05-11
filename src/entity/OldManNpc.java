package entity;

import java.util.Random;

import main.Direction;
import main.GamePanel;

public class OldManNpc extends Npc {

	public OldManNpc(GamePanel gp) {
		
		super(gp, "/npc/oldman");
		this.speed = 1;
	}
	
	@Override 
	public void updateAction() {
		
		--this.actionLockCounter;
		
		// Do nothing if action lock counter is not 0.
		if(this.actionLockCounter > 0) {
			return;
		}
		
		Random random = new Random();
		int rnd = random.nextInt(100)+1; // Random in range of 100-400
		System.out.println(rnd);
		this.actionLockCounter = random.nextInt(100)+1;
		
		if(rnd > 95) {
			this.movementDirection = Direction.Up;
			this.lookDirection = Direction.Up;
		}
		else if(rnd > 90) {
			this.movementDirection = Direction.Left;
			this.lookDirection = Direction.Left;
		}
		else if(rnd > 85) {
			this.movementDirection = Direction.Down;
			this.lookDirection = Direction.Down;
		}
		else if(rnd > 80) {
			this.movementDirection = Direction.Right;
			this.lookDirection = Direction.Right;
		}
		else {
			this.movementDirection = null;
		}
	}
	
	@Override
	public void update() {
		
		if(this.movementDirection != null) {
			this.updateMovement();
		}
	}
	
	private void updateMovement() {
		
		this.checkCollision();
		
		if (collisionOn == false) {
			this.move();
		}
		else {			
			this.actionLockCounter = -1;
		}
		
		this.updateSprite();
	}
}
