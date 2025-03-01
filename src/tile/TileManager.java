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
	final ArrayList<Tile> worldMap = new ArrayList<Tile>();
	
	public TileManager(GamePanel gamePanel, int tilesCapacity) {
		
		this.gamePanel = gamePanel;		
		
		loadMap("/maps/map.txt");
	}
	
	public void loadMap(String mapFilePath) {
		try {
			int yOffSet = 0;
			int xOffSet = 0;

			InputStream stream = getClass().getResourceAsStream(mapFilePath);
			BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
						
			String line = reader.readLine();  			while (line != null) {
				
				for(String tileId : line.split(",")) {
					
					Tile tile = new Tile();
					tile.x = xOffSet;
					tile.y = yOffSet;
					tile.image = ImageIO.read(getClass().getResourceAsStream(String.format("/tiles/%s.png", tileId)));
					
					worldMap.add(tile);
					xOffSet += this.gamePanel.tileSize;
				}
				
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
		for(Tile tile : this.worldMap) {	
			
			boolean shouldNotRender = 
					tile.x < player.worldX - gamePanel.screenWidth / 2 || 
					tile.y < player.worldY - gamePanel.screenHeight / 2 || 
					tile.x > player.worldX + gamePanel.screenWidth || 
					tile.y > player.worldY + gamePanel.screenHeight;
			
			if(shouldNotRender) {
				continue;
			}
			
			tileCount++;
			int xOffSet = tile.x - player.worldX;
			int yOffSet = tile.y - player.worldY;
			
			g.drawImage(tile.image, xOffSet, yOffSet, this.gamePanel.tileSize, this.gamePanel.tileSize, null);
		}		
		
		System.out.println(tileCount);
	}
}
