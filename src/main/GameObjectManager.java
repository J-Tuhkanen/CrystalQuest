package main;

import object.Key;

public class GameObjectManager {

	private final GamePanel _gamePanel;
	
	public GameObjectManager(GamePanel gp) {
		
		_gamePanel = gp;
	}
	
	public void setObject() {
		
		Key key = new Key();
		key.worldX = _gamePanel.tileSize * 10;
		key.worldY = _gamePanel.tileSize * 10;
		_gamePanel.objects.add(key);
	}
}
