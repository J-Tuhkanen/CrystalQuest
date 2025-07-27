package main;

import object.GameObject;

public class Inventory {
	
	public GameObject items[] = new GameObject[12];
	public int selectedIndex = 0;
	
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
}
