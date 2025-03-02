package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import entity.Player;
import main.GamePanel;

public class TileManager {

	GamePanel gamePanel;
	public final ArrayList<ArrayList<Tile>> worldMap = new ArrayList<ArrayList<Tile>>();
	private final String[] collisionTileIds = new String[] {
		"016", 
	};
	
	public TileManager(GamePanel gamePanel, int tilesCapacity) {
		
		this.gamePanel = gamePanel;		
		
		loadMap("/maps/map.txt");
	}
	
	public void loadMap(String mapFilePath) {
		try {
			int yOffSet = 0;
			int xOffSet = 0;
			int columnIndex = 0;

			InputStream stream = getClass().getResourceAsStream(mapFilePath);
			BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
						
			String line = reader.readLine();  			
			while (line != null) {
				
				worldMap.add(new ArrayList<Tile>());
				for(String tileId : line.split(",")) {
					
					var image = ImageIO.read(getClass().getResourceAsStream(String.format("/tiles/%s.png", tileId)));
					Tile tile = new Tile(tileId, image, hasCollision(tileId));
					tile.y = yOffSet;
					tile.x = xOffSet;
					
					worldMap.get(columnIndex).add(tile);
					xOffSet += this.gamePanel.tileSize;
				}
				columnIndex++;
				yOffSet += this.gamePanel.tileSize;
				xOffSet = 0;
				line = reader.readLine();
			}
			
			reader.close();
			stream.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
		
	public void draw(Graphics2D g) {		 		
		
		int tileCount = 0;
		Player player = gamePanel.player;
		for(var list : this.worldMap) {			
			for(Tile tile : list) {	
				
				boolean shouldNotRender = 
						tile.x < player.worldX - player.cameraX - gamePanel.tileSize || 
						tile.y < player.worldY - player.cameraY - gamePanel.tileSize || 
						tile.x > player.worldX + player.cameraX || 
						tile.y > player.worldY + player.cameraY;
				
				if(shouldNotRender) {
					continue;
				}
				
				tileCount++;
				int xOffSet = tile.x - player.worldX + player.cameraX;
				int yOffSet = tile.y - player.worldY + player.cameraY;
				
				g.drawImage(tile.image, xOffSet, yOffSet, this.gamePanel.tileSize, this.gamePanel.tileSize, null);
			}		
		}
		
		System.out.println(tileCount);
	}
	
	private boolean hasCollision(String id) {
		
		for (var x : this.collisionTileIds) {
						
			if(x.equals(id)) {
				return true;
			}
		}
		
		return false;
	}
}
