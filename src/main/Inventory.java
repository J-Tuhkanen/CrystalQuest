package main;

import object.GameObject;

public class Inventory {
	
	public GameObject items[] = new GameObject[12];
	public int selectedRowIndex = 0;
	public int selectedColumnIndex = 0;
	private long _lastTimeRowUpdated = 0;
	private long _lastTimeColumnUpdated = 0;
	
	public GameObject RemoveAt(int index) {
		
		GameObject object = this.items[index];
		this.items[index] = null;
		
		return object;
	}
	
	// Add item to first open slot in inventory. 
	// Return boolean indicating if open slot was found and item was added.
	public boolean TryAdd(GameObject object) {
		
		for(int i = 0; i < items.length; i++) {			
			if(this.items[i] == null) {
				
				this.items[i] = object;
				return true;
			}
		}
		return false;
	}
	
	public void updateSelectedInventorySlot(KeyHandler keyH) {
		
		// TODO: This system is good for now but might feel laggy if pressing multiple times quickly.
		// We should implement release logic into Keyhandler
		int treshold = 200000000;
		long currentTime = System.nanoTime();
		
		if(currentTime - this._lastTimeRowUpdated >= treshold && (keyH.upPressed || keyH.downPressed)) {
			this.selectedRowIndex = this.selectedRowIndex == 0 ? 1 : 0;
			this._lastTimeRowUpdated = currentTime;
		}
		
		if(currentTime - this._lastTimeColumnUpdated < treshold) {
			return;
		}
		if(keyH.leftPressed) {
			this.selectedColumnIndex = 
				this.selectedColumnIndex != 0 
				? this.selectedColumnIndex - 1
				: 5;
			this._lastTimeColumnUpdated = currentTime;
		}
		else if(keyH.rightPressed) {
			this.selectedColumnIndex = 
				this.selectedColumnIndex != 5 
				? this.selectedColumnIndex + 1
				: 0;
			this._lastTimeColumnUpdated = currentTime;
		}
	}
}
