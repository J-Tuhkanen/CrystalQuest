package ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class ActionPanel extends JPanel {

	private static final long serialVersionUID = 9011400371067015922L;
	private final JLabel _hintLabel = new JLabel();
	private final GamePanel _gamePanel;
	
	public ActionPanel(GamePanel gp) {
		this._gamePanel = gp;
		
		_hintLabel.setBounds(this._gamePanel.tileSize, this._gamePanel.tileSize, this._gamePanel.screenWidth, 100);
		_hintLabel.setForeground(new Color(227, 217, 209));
		_hintLabel.setFont(new Font("Ariel", 0, 35));
		this.add(_hintLabel);
		this.setOpaque(false);
	}

	@Override
	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		var collisionInfo = this._gamePanel.player.collisionInfo;
		this._hintLabel.setText(null);
		
		if(this._gamePanel.player.actionMenu.isOpen()) {
			this.drawActionMenu((Graphics2D)graphics);
		}
		else if(!collisionInfo.npcs.isEmpty() || !collisionInfo.gameObjects.isEmpty()) {
			this._hintLabel.setText("Press E for action menu");
		}
	}
	
	public void drawActionMenu(Graphics2D g2) {
		
		int menuX = this._gamePanel.tileSize;
		int menuY = this._gamePanel.tileSize;		
		int width = this._gamePanel.tileSize*7;
		var menu = this._gamePanel.player.actionMenu;
		var labels = menu.labels();
		int rowHeight = 40;
		int visibleRows = Math.min(labels.size(), (this._gamePanel.screenHeight - menuY * 2 - 80) / rowHeight);
		int height = Math.max(120, 80 + visibleRows * rowHeight);
		
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
        // Fill with white background
        g2.setColor(new Color(0,0,0, 200));
        g2.fillRoundRect(menuX, menuY, width, height, 10, 10);
        
        // Draw border
        g2.setColor(new Color(255,255,255));
        g2.setStroke(new BasicStroke(10)); // border thickness
        g2.drawRoundRect(menuX, menuY, width, height, 10, 10);
                
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 28F));

        var target = menu.selectedTarget();
        g2.setColor(Color.WHITE);
        g2.drawString(target == null ? "Choose target" : target.name(), menuX + 24, menuY + 36);
        int firstRow = Math.max(0, menu.selectedIndex() - visibleRows + 1);
        if (labels.isEmpty()) {
            g2.drawString("No available actions", menuX + 24, menuY + 80);
        }
        for (int i = firstRow; i < Math.min(labels.size(), firstRow + visibleRows); i++) {
            g2.setColor(i == menu.selectedIndex() ? new Color(190, 160, 35) : Color.WHITE);
            g2.drawString(labels.get(i), menuX + 24, menuY + 80 + (i - firstRow) * rowHeight);
        }
	}
}
