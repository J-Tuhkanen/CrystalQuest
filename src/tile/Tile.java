package tile;

import java.awt.image.BufferedImage;

public class Tile {
	
	public int x,y;
	public final String tileId;
	public final BufferedImage image;
	public final boolean collision;
	
	public Tile(String tileId, BufferedImage image, boolean collision) {
		this.tileId = tileId;
		this.image = image;
		this.collision = collision;
	}
}
