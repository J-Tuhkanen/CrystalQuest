package main;

import entity.Entity;
import tile.Tile;

public class CollisionChecker {

	final GamePanel gp;
	
	public CollisionChecker(GamePanel gp) {
		this.gp = gp;
	}
	
	public void checkTile(Entity entity) {
		
		int entityLeftWorldX = entity.worldX + (gp.tileSize - entity.collision.width);
		int entityRightWorldX = entity.worldX + (entity.collision.width + entity.collision.width - gp.tileSize);
		int entityTopWorldY = entity.worldY + entity.collision.y;
		//int entityBottomWorldY = entity.worldY + entity.collision.y + entity.collision.height;

		int entityLeftCol = entityLeftWorldX / gp.tileSize;
		int entityRightCol = entityRightWorldX / gp.tileSize;
		int entityTopRow = entityTopWorldY / gp.tileSize;
		//int entityBottomRow = entityBottomWorldY / gp.tileSize;

		Tile tileNum1, tileNum2;

		switch(entity.direction) {
		    case Direction.Up:
		        entityTopRow = (entityTopWorldY - entity.speed) / gp.tileSize;
		        tileNum1 = gp.tileManager.worldMap.get(entityTopRow).get(entityLeftCol);
		        tileNum2 = gp.tileManager.worldMap.get(entityTopRow).get(entityRightCol);
		        
		        
		        
		        if(tileNum1.collision == true || tileNum2.collision == true) {
		            entity.collisionOn = true;
		        }
		        break;
			case Down:
				break;
			case Left:
				break;
			case Right:
				break;
			default:
				break;

		}

	}
}
