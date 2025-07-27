package main;

import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class UIPanel extends JPanel {

	private static final long serialVersionUID = 6302459241288572245L;

	private final JLabel _hintLabel = new JLabel("terveppä terve");
	
	
	private GridLayout gridLayoutManager = new GridLayout(3, 1);
	
	public UIPanel() {
		
		
		
		
		this.setLayout(gridLayoutManager);
		
		setupHintLabel();
	}

	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
	
	
	}
	
	private void setupHintLabel() {
		
		var y = (int)(this.getHeight() * 0.8);
		
		_hintLabel.setBounds(100, 100, this.getWidth(), (int)(this.getHeight() * 0.2));
		_hintLabel.setLayout(gridLayoutManager);
		add(_hintLabel);
		add(_hintLabel);
		add(_hintLabel);
		
	}
}
