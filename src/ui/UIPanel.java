package ui;

import javax.swing.JLayeredPane;

public class UIPanel extends JLayeredPane {
	
	private static final long serialVersionUID = 6302459241288572245L;
	
	private final Dialog _dialog;
	private final Hint _hint;
	private final Menu _menu;
	private final Inventory _inventory;
	
	public UIPanel(GamePanel gp) {
		
		_dialog = new Dialog(gp);
		_hint = new Hint(gp);
		_menu = new Menu(gp);
		_inventory = new Inventory(gp);
		
		this._hint.setBounds(
				0, 
				(int)(gp.screenHeight * 0.8), 
				gp.screenWidth, 
				(int)(gp.screenHeight * 0.2));
		
		int inventoryWidth = gp.tileSize * 13;
		int inventoryHeight = gp.tileSize * 8;
		int inventoryX = (gp.screenWidth - inventoryWidth) / 2 ;
		int inventoryY = (gp.screenHeight - inventoryHeight) / 2 ;
		this._inventory.setBounds(inventoryX, inventoryY, inventoryWidth, inventoryHeight);
		
		int dialogWidth = this._dialog.getWidth();
		int dialogHeight = this._dialog.getHeight();
		int dialogX = (gp.screenWidth - dialogWidth) / 2;
		int dialogY = (dialogHeight/10);
		this._dialog.setBounds(dialogX, dialogY, dialogWidth, dialogHeight);
		
		this.add(this._inventory, 0);		
		this.add(this._hint, 1);
		this.add(this._dialog, 2);
		this.add(this._menu, 3);
	}
}