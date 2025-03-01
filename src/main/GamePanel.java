package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import abstraction.IUpdateable;
import entity.Player;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable {

	private static final long serialVersionUID = 3169133952889093310L;

	// Screen settings
	public final int tileSize = 64;
	public final int maxScreenColums = 16;
	public final int maxScreenRows = 12;
	public final int screenWidth = tileSize * maxScreenColums;
	public final int screenHeight = tileSize * maxScreenRows;
	
	int fps = 60;
	
	private final KeyHandler keyHandler = new KeyHandler();
	private final Thread gameThread;
	
	private final List<IUpdateable> updateables = new ArrayList<IUpdateable>();
	private final TileManager tileManager = new TileManager(this, 10);
	public Player player = new Player(this, keyHandler);
	
	public GamePanel() {
		
		setPreferredSize(new Dimension(screenWidth, screenHeight));
		setBackground(Color.black);
		setDoubleBuffered(true);
		addKeyListener(keyHandler);
		setFocusable(true);
		gameThread = new Thread(this);
		
		this.updateables.add(player);
	}
	
	
	
	public void startGameThread() {
		gameThread.start();
	}

	@Override
	public void run() {
		
		double drawInterval = 1000000000 / fps;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		long timer = 0;

		while (gameThread != null) {
		    currentTime = System.nanoTime();
		    delta += (currentTime - lastTime) / drawInterval;
		    timer += (currentTime - lastTime);
		    lastTime = currentTime;

		    if (delta >= 1) {
		        update();
		        repaint();
		        delta--;
		    }

		    if (timer >= 1000000000) {
		        timer = 0;
		    }
		}
	}
	
	public void update() {
		
		for(IUpdateable u : this.updateables) {			
			u.update();
		}
	}
	
	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		
		Graphics2D g = (Graphics2D)graphics;
		tileManager.draw(g);
		player.draw(g);
		g.dispose();
	}
	
	public int getTileSize() {
		return this.tileSize;
	}
}
