package main;

import entity.Entity;
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
}
