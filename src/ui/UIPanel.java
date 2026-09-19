package ui;

import javax.swing.JLayeredPane;

public class UIPanel extends JLayeredPane {
	
	private static final long serialVersionUID = 6302459241288572245L;
	
	private final DialogPanel _dialog;
	private final ActionPanel _hint;
	private final InventoryPanel _inventory;
	private final ChatBubblePanel _chatBubbles;

	public UIPanel(GamePanel gp) {

		_dialog = new DialogPanel(gp);
		_hint = new ActionPanel(gp);
		_inventory = new InventoryPanel(gp);
		_chatBubbles = new ChatBubblePanel(gp);

		this._chatBubbles.setBounds(
				0,
				0,
				gp.screenWidth,
				gp.screenHeight);
		
		this._hint.setBounds(
				0, 
				0,
				gp.screenWidth, 
				gp.screenHeight);
		
		this._inventory.setBounds(InventoryPanel.getInventoryBounds(gp));
		
		int dialogWidth = this._dialog.getWidth();
		int dialogHeight = this._dialog.getHeight();
		int dialogX = (gp.screenWidth - dialogWidth) / 2;
		int dialogY = (dialogHeight/10);
		this._dialog.setBounds(dialogX, dialogY, dialogWidth, dialogHeight);
		
		this.add(this._inventory, 0);
		this.add(this._hint, 1);
		this.setLayer(this._hint, 100);
		this.add(this._dialog, 2);
		this.add(this._chatBubbles, 3);
		// Above the inventory so messages from inventory actions stay visible, below the menus.
		this.setLayer(this._chatBubbles, 50);
	}
}
