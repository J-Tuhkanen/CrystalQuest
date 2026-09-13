package ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;

import javax.swing.JPanel;

import entity.ChatBubble;
import entity.Entity;

public class ChatBubblePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private final GamePanel _gamePanel;

	private static final int paddingX = 14;
	private static final int paddingY = 8;
	private static final int tailHeight = 10;
	private static final int gapAboveHead = 12;

	public ChatBubblePanel(GamePanel gp) {
		this._gamePanel = gp;
		this.setOpaque(false);
	}

	@Override
	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);

		Graphics2D g2 = (Graphics2D) graphics;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setFont(g2.getFont().deriveFont(20F));

		this.drawBubbleFor(g2, this._gamePanel.player);
		for (var npc : this._gamePanel.npcs) {
			this.drawBubbleFor(g2, npc);
		}
	}

	private void drawBubbleFor(Graphics2D g2, Entity entity) {

		ChatBubble bubble = entity.chatBubble;

		if (bubble == null || bubble.isExpired()) {
			return;
		}

		FontMetrics metrics = g2.getFontMetrics();
		int textWidth = metrics.stringWidth(bubble.message);
		int textHeight = metrics.getHeight();

		int bubbleWidth = textWidth + paddingX * 2;
		int bubbleHeight = textHeight + paddingY * 2;

		int entityCenterX = entity.getScreenX() + this._gamePanel.tileSize / 2;
		int bubbleX = entityCenterX - bubbleWidth / 2;
		int bubbleY = entity.getScreenY() - gapAboveHead - tailHeight - bubbleHeight;

		g2.setColor(new Color(0, 0, 0, 200));
		g2.fillRoundRect(bubbleX, bubbleY, bubbleWidth, bubbleHeight, 14, 14);

		Polygon tail = new Polygon();
		tail.addPoint(entityCenterX - 8, bubbleY + bubbleHeight);
		tail.addPoint(entityCenterX + 8, bubbleY + bubbleHeight);
		tail.addPoint(entityCenterX, bubbleY + bubbleHeight + tailHeight);
		g2.setColor(new Color(0, 0, 0, 200));
		g2.fillPolygon(tail);

		g2.setColor(new Color(255, 255, 255, 200));
		g2.setStroke(new BasicStroke(2));
		g2.drawRoundRect(bubbleX, bubbleY, bubbleWidth, bubbleHeight, 14, 14);

		g2.setColor(Color.WHITE);
		g2.drawString(bubble.message, bubbleX + paddingX, bubbleY + paddingY + metrics.getAscent());
	}
}
