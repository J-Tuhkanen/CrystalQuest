package main;

import java.awt.Rectangle;
import java.util.ArrayList;
import entity.Entity;
import entity.Npc;
import main.Enum.Direction;
import object.GameObject;
import tile.Tile;

public class CollisionChecker {

	public class Coordinate {
		
		public final int X, Y;
		Coordinate(int x, int y){
			X = x;
			Y = y;
		}
	}
	
	final GamePanel gp;
	
	public CollisionChecker(GamePanel gp) {
		this.gp = gp;
	}
	
	public void checkTile(Entity entity) {
	 	
		Coordinate topLeft = new Coordinate(
				entity.worldX, 
				entity.worldY + entity.collision.y);
		
		Coordinate bottomLeft = new Coordinate(
				entity.worldX, 
				entity.worldY + entity.collision.y + entity.collision.height);
		
		Coordinate topRight = new Coordinate(
				entity.worldX + entity.collision.width, 
				entity.worldY + entity.collision.y);
		
		Coordinate bottomRight = new Coordinate(
				entity.worldX + entity.collision.width, 
				entity.worldY + entity.collision.y + entity.collision.height);

		int offSet = 10;
		
		Tile leftTile, rightTile;

	    if(entity.movementDirection == Direction.Up) {
	    	
	    	int rowIndex = (topLeft.Y - entity.speed) / gp.tileSize;
	    	int leftColumn = (topLeft.X + offSet) / gp.tileSize;
	    	int rightColumn = (topRight.X - offSet) / gp.tileSize;
	    	
	        leftTile = gp.tileManager.worldMap.get(rowIndex).get(leftColumn);
	        rightTile = gp.tileManager.worldMap.get(rowIndex).get(rightColumn);		        
	        
	        if(leftTile.collision == true || rightTile.collision == true) {
	            entity.collisionOn = true;
	        }
	    }
	    
	    if(entity.movementDirection == Direction.Down) {
	    	int rowIndex = (bottomLeft.Y + entity.speed) / gp.tileSize;
	    	int leftColumn = (bottomLeft.X + offSet) / gp.tileSize;
	    	int rightColumn = (bottomRight.X - offSet) / gp.tileSize;
	    	
	        leftTile = gp.tileManager.worldMap.get(rowIndex).get(leftColumn);
	        rightTile = gp.tileManager.worldMap.get(rowIndex).get(rightColumn);		
	        
	        if(leftTile.collision == true || rightTile.collision == true) {
	            entity.collisionOn = true;
	        }
	    }
	    
	    if(entity.movementDirection == Direction.Right) {
	    	int columnIndex = (topRight.X + entity.speed) / gp.tileSize;
	        int topRow = topRight.Y / gp.tileSize;
	        int bottomRow = bottomRight.Y / gp.tileSize;
	    	
	        leftTile = gp.tileManager.worldMap.get(topRow).get(columnIndex);
	        rightTile = gp.tileManager.worldMap.get(bottomRow).get(columnIndex);	
	    	
	        if(leftTile.collision == true || rightTile.collision == true) {
	            entity.collisionOn = true;
	        }
	    }	
	    if(entity.movementDirection == Direction.Left) {
	    	int columnIndex = (topLeft.X - entity.speed) / gp.tileSize;
	        int topRow = bottomLeft.Y / gp.tileSize;
	        int bottomRow = bottomLeft.Y / gp.tileSize;
	    	
	        leftTile = gp.tileManager.worldMap.get(topRow).get(columnIndex);
	        rightTile = gp.tileManager.worldMap.get(bottomRow).get(columnIndex);	
	    	
	        if(leftTile.collision == true || rightTile.collision == true) {
	            entity.collisionOn = true;
	        }
	    }	
	}
	
	public ArrayList<GameObject> checkObjectCollision(Entity entity) {
		
		ArrayList<GameObject> collidedObjects = new ArrayList<GameObject>();

		// Run the check in reverse so we can have a clean loop with a delete within the collection.
		for (int i = gp.objects.size() - 1; i > -1 ; i--) {
			
			var gameObject = gp.objects.get(i);			
			var entitySolidArea = new Rectangle(entity.hitBox);
			var gameObjectSolidArea = new Rectangle(gameObject.solidArea);
			
			entitySolidArea.x = entity.worldX + entity.hitBox.x;
			entitySolidArea.y = entity.worldY + entity.hitBox.y;
						
			gameObjectSolidArea.x = gameObject.worldX + gameObject.solidArea.x;
			gameObjectSolidArea.y = gameObject.worldY + gameObject.solidArea.y;
			
			switch(entity.movementDirection) {
				case Up:
					entitySolidArea.y -= entity.speed;
					break;
				case Down:
					entitySolidArea.y += entity.speed;	
					break;
				case Left:
					entitySolidArea.x -= entity.speed;
					break;
				case Right:
					entitySolidArea.x += entity.speed;
					break;				
			}
			
			if(entitySolidArea.intersects(gameObjectSolidArea)) {				
				if(gameObject.collision) {
					
					entity.collisionOn = true;
				}				
				collidedObjects.add(gameObject);
			}
		}
		
		return collidedObjects;
	}
	
	public ArrayList<Npc> checkEntityCollision(Entity entity) {
		
		ArrayList<Npc> collidedEntities = new ArrayList<Npc>(); 
		
		// Run the check in reverse so we can have a clean loop with a delete within the collection.
		for (Npc npc : gp.npcs) {
			
			if(npc.equals(entity)) {
				continue;
			}
			
			var entitySolidArea = new Rectangle(entity.hitBox);
			var gameObjectSolidArea = new Rectangle(npc.hitBox);
			
			entitySolidArea.x = entity.worldX + entity.hitBox.x;
			entitySolidArea.y = entity.worldY + entity.hitBox.y;
						
			gameObjectSolidArea.x = npc.worldX + npc.hitBox.x;
			gameObjectSolidArea.y = npc.worldY + npc.hitBox.y;
			
			switch(entity.movementDirection) {
				case Up:
					entitySolidArea.y -= entity.speed;
					break;
				case Down:
					entitySolidArea.y += entity.speed;	
					break;
				case Left:
					entitySolidArea.x -= entity.speed;
					break;
				case Right:
					entitySolidArea.x += entity.speed;
					break;				
			}
			
			if(entitySolidArea.intersects(gameObjectSolidArea)) {
				collidedEntities.add(npc);
			}
		}
		
		return collidedEntities;
	}
	
	public void checkPlayerCollision(Npc npc) {
		
		var playerSolidArea = new Rectangle(gp.player.hitBox);
		var npcSolidArea = new Rectangle(npc.hitBox);
		
		playerSolidArea.x = gp.player.worldX + gp.player.hitBox.x;
		playerSolidArea.y = gp.player.worldY + gp.player.hitBox.y;
					
		npcSolidArea.x = npc.worldX + npc.hitBox.x;
		npcSolidArea.y = npc.worldY + npc.hitBox.y;
		
		switch(npc.movementDirection) {
			case Up:
				npcSolidArea.y -= npc.speed;
				break;
			case Down:
				npcSolidArea.y += npc.speed;	
				break;
			case Left:
				npcSolidArea.x -= npc.speed;
				break;
			case Right:
				npcSolidArea.x += npc.speed;
				break;				
		}
		
		if(npcSolidArea.intersects(playerSolidArea)) {
			npc.collisionOn = true;
		}
	}
}
