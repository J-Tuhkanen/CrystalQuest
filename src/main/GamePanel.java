package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;

import javax.swing.JPanel;

import abstraction.IUpdateable;
import entity.Player;
import object.GameObject;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable {

	private static final long serialVersionUID = 3169133952889093310L;
	private final int _fps = 60;
	private final KeyHandler _keyHandler = new KeyHandler();
	private final Thread _gameThread;
	private final ArrayList<IUpdateable> _updateables = new ArrayList<IUpdateable>();
	private final GameObjectManager _gameObjectManager = new GameObjectManager(this);

	// Screen settings
	public final int tileSize = 64;
	public final int maxScreenColums = 16;
	public final int maxScreenRows = 12;
	public final int screenWidth = tileSize * maxScreenColums;
	public final int screenHeight = tileSize * maxScreenRows;
	public final CollisionChecker collisiongChecker;
	public final TileManager tileManager = new TileManager(this, 10);
	public final UI ui = new UI(this);
	
	public Dictionary<String, Sound> sounds = new Hashtable<String, Sound>();
	
	Sound sound;
	
	// Entities and objects
	public final Player player = new Player(this, _keyHandler);
	public final ArrayList<GameObject> objects = new ArrayList<GameObject>();	
	
	public GamePanel() {
				
		setPreferredSize(new Dimension(screenWidth, screenHeight));
		setBackground(Color.black);
		setDoubleBuffered(true);
		addKeyListener(_keyHandler);
		setFocusable(true);
		_gameThread = new Thread(this);
		this.collisiongChecker = new CollisionChecker(this);
		this._updateables.add(player);
	}	

	public void setupGame() {
		_gameObjectManager.setObject();
		playMusic("gamemusic");
	}
	
	public void startGameThread() {
		_gameThread.start();
	}

	@Override
	public void run() {
		
		double drawInterval = 1000000000 / _fps;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		long timer = 0;

		while (_gameThread != null) {
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
		
		for(IUpdateable u : this._updateables) {			
			u.update();
		}
	}
	
	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		
		Graphics2D g = (Graphics2D)graphics;
		
		tileManager.draw(g);
		for(GameObject o : this.objects) {
			o.draw(g, this);
		}
		
		player.draw(g);
		ui.draw(g);
		
		g.dispose();
	}
	
	public void playMusic(String soundName) {
		sound = this.sounds.get(soundName);
		sound.play();
		sound.loop();
	}
	
	public void stopMusic() {
		if(sound == null) {
			return;
		}
		
		sound.stop();
		sound = null;
	}
	public void playSoundEffect(String soundName) {
		sound = this.sounds.get(soundName);
		sound.play();		
	}
}
