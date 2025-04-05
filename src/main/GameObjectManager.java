package main;

import object.Key;
import object.TreasureChest;
import object.WoodenDoor;

public class GameObjectManager {

	private final GamePanel _gamePanel;
	
	public GameObjectManager(GamePanel gp) {
		
		_gamePanel = gp;
	}
	
	public void setObject() {
		
		Key key = new Key();
		key.worldX = _gamePanel.tileSize * 9;
		key.worldY = _gamePanel.tileSize * 25;
		_gamePanel.objects.add(key);
		
		TreasureChest chest = new TreasureChest();
		chest.worldX = _gamePanel.tileSize * 22;
		chest.worldY = _gamePanel.tileSize * 21;
		_gamePanel.objects.add(chest);
		
		WoodenDoor door = new WoodenDoor();
		door.worldX = _gamePanel.tileSize * 22;
		door.worldY = _gamePanel.tileSize * 25;
		_gamePanel.objects.add(door);
		
	}
}
