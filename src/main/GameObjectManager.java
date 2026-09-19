package main;

import entity.OldManNpc;
import object.Book;
import object.GameObject;
import object.Key;
import object.Pickaxe;
import object.Sword;
import object.TreasureChest;
import object.WoodenDoor;
import ui.GamePanel;

public class GameObjectManager {

	private final GamePanel _gamePanel;
	
	public GameObjectManager(GamePanel gp) {
		
		_gamePanel = gp;
	}
	
	// Places an object into the world at the given pixel coordinates.
	public void spawn(GameObject obj, int worldX, int worldY) {
		
		obj.worldX = worldX;
		obj.worldY = worldY;
		this._gamePanel.objects.add(obj);
	}
	
	// Removes an object from the world. Returns whether it was in the world.
	public boolean despawn(GameObject obj) {
		
		return this._gamePanel.objects.remove(obj);
	}
	
	public void setObject() {
		
		int tileSize = _gamePanel.tileSize;
		int frontDoorId = 1;

		spawn(new Key(frontDoorId), tileSize * 9, tileSize * 25);
		spawn(new Key(frontDoorId), tileSize * 10, tileSize * 25);
		spawn(new TreasureChest(), tileSize * 22, tileSize * 21);
		spawn(new WoodenDoor(frontDoorId), tileSize * 22, tileSize * 25);

		// Test items for the inventory action menu, near the player's start (tile 5, 16).
		spawn(new Book("Old diary", "\"Day 12. The crystal is said to lie beyond the old door...\""), tileSize * 7, tileSize * 14);
		spawn(new Pickaxe(), tileSize * 8, tileSize * 14);
		spawn(new Sword(), tileSize * 7, tileSize * 16);
	}
	
	public void setNpc() {
		
		var oldMan = new OldManNpc(this._gamePanel);	
		oldMan.worldX = 300;
		oldMan.worldY = 300;
		this._gamePanel.npcs.add(oldMan);
	}
}
