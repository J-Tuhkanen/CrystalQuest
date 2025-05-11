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
		int rnd = random.nextInt(400)+100; // Random in range of 100-400
		
		this.actionLockCounter = random.nextInt(100)+1;
		
		if(rnd <= 25) {
			this.movementDirection = Direction.Up;
			this.lookDirection = Direction.Up;
		}
		else if(rnd <= 50) {
			this.movementDirection = Direction.Left;
			this.lookDirection = Direction.Left;
		}
		else if(rnd <= 75) {
			this.movementDirection = Direction.Down;
			this.lookDirection = Direction.Down;
		}
		else {
			this.movementDirection = Direction.Right;
			this.lookDirection = Direction.Right;
		}
	}
	
	@Override
	public void update() {
		
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
