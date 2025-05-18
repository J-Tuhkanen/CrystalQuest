package main;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseHandler extends MouseAdapter  {

	private final GamePanel _gamePanel;
	public boolean RightMouseKeyPressed = false;
	
	public MouseHandler(GamePanel gamePanel) {		
		_gamePanel = gamePanel;
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
	
		if(_gamePanel.gameState == GameState.Paused) {
			return;
		}
		
		int button = e.getButton();
		
		if(button == 3) {			
			this.RightMouseKeyPressed = true;		
		}
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		
		if(_gamePanel.gameState == GameState.Paused) {
			return;
		}
		
		int button = e.getButton();
		
		if(button == 3) {			
			this.RightMouseKeyPressed = false;		
		}
	}
}
