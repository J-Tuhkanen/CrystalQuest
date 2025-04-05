package main;

import entity.Entity;
import entity.Player;
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

	    if(entity.direction == Direction.Up) {
	    	
	    	int rowIndex = (topLeft.Y - entity.speed) / gp.tileSize;
	    	int leftColumn = (topLeft.X + offSet) / gp.tileSize;
	    	int rightColumn = (topRight.X - offSet) / gp.tileSize;
	    	
	        leftTile = gp.tileManager.worldMap.get(rowIndex).get(leftColumn);
	        rightTile = gp.tileManager.worldMap.get(rowIndex).get(rightColumn);		        
	        
	        if(leftTile.collision == true || rightTile.collision == true) {
	            entity.collisionOn = true;
	        }
	    }
	    
	    if(entity.direction == Direction.Down) {
	    	int rowIndex = (bottomLeft.Y + entity.speed) / gp.tileSize;
	    	int leftColumn = (bottomLeft.X + offSet) / gp.tileSize;
	    	int rightColumn = (bottomRight.X - offSet) / gp.tileSize;
	    	
	        leftTile = gp.tileManager.worldMap.get(rowIndex).get(leftColumn);
	        rightTile = gp.tileManager.worldMap.get(rowIndex).get(rightColumn);		
	        
	        if(leftTile.collision == true || rightTile.collision == true) {
	            entity.collisionOn = true;
	        }
	    }
	    
	    if(entity.direction == Direction.Right) {
	    	int columnIndex = (topRight.X + entity.speed) / gp.tileSize;
	        int topRow = topRight.Y / gp.tileSize;
	        int bottomRow = bottomRight.Y / gp.tileSize;
	    	
	        leftTile = gp.tileManager.worldMap.get(topRow).get(columnIndex);
	        rightTile = gp.tileManager.worldMap.get(bottomRow).get(columnIndex);	
	    	
	        if(leftTile.collision == true || rightTile.collision == true) {
	            entity.collisionOn = true;
	        }
	    }	
	    if(entity.direction == Direction.Left) {
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
	
	public void checkObject(Entity entity, boolean player) {
						
		for (int i = 0; i < gp.objects.size(); i++) {
			
			GameObject gameObject = gp.objects.get(i);
			
			entity.solidArea.x = entity.worldX + entity.solidArea.x;
			entity.solidArea.y = entity.worldY + entity.solidArea.y;
						
			gameObject.solidArea.x = gameObject.worldX + gameObject.solidArea.x;
			gameObject.solidArea.y = gameObject.worldY + gameObject.solidArea.y;
			
			switch(entity.direction) {
				case Up:
					entity.solidArea.y -= entity.speed;
					break;
				case Down:
					entity.solidArea.y += entity.speed;	
					break;
				case Left:
					entity.solidArea.x -= entity.speed;
					break;
				case Right:
					entity.solidArea.x += entity.speed;
					break;
				
			}
			if(entity.solidArea.intersects(gameObject.solidArea)) {
				System.out.println("Collision");
				
				if(gameObject.collision) {
					
					entity.collisionOn = true;
				}
				else if(player) {
					((Player)entity).pickUpObject(i);
				}
			}
			
			entity.solidArea.x = entity.solidAreaDefaultX;
			entity.solidArea.y = entity.solidAreaDefaultY;
			gameObject.solidArea.x = gameObject.solidAreaDefaultX;
			gameObject.solidArea.y = gameObject.solidAreaDefaultY;
		}
	}
}
