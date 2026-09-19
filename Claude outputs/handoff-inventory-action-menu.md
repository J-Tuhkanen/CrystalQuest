# Handoff: Inventory item action menu (CrystalQuest, Java/Swing)

## Status
- A design interview (grilling) is **complete**. All decisions are recorded below.
- The last message asked the user to confirm the design summary and to back up/commit first, because the project folder has no git repo that I could see. **The user has not confirmed yet.** Get their go-ahead, then implement.
- No code has been written yet.

## Where the code is
- The project is on the user's Windows machine: `C:\Users\janne\source\repos\CrystalQuest`, connected to the session as the folder "CrystalQuest".
- In `device_bash` it is mounted at `$HOME/mnt/CrystalQuest`. Sources are under `src/`.
- Edit the files in place with `device_bash` (sed or a python read-modify-write). Don't stage files into the cloud workspace.
- No build or run command was found. Check for an Eclipse project or build files before trying to compile. `javac` may not be installed on the device.

Files that matter:
- `src/entity/`: `Action.java`, `Player.java`, `Entity.java`, `Npc.java`, `OldManNpc.java`
- `src/main/`: `ActionMenu.java`, `Inventory.java`, `KeyHandler.java`, `GameObjectManager.java`, `CollisionInformation.java`
- `src/object/`: `GameObject.java`, `Key.java`, `TreasureChest.java`, `WoodenDoor.java`
- `src/ui/`: `ActionPanel.java` (draws the world menu and the "Press E" hint), `InventoryPanel.java` (the grid), `UIPanel.java` (panel layout), `GamePanel.java` (tileSize 64, screen 1024×768; `_gameObjectManager` is a private field)

## Facts found in the code
- **Examine is not implemented anywhere.** No class has a description field. `Player.interactWith` handles only `Pickup` and `Use`. Everything else prints "Not implemented yet".
- **`GameObject.use(Player)` already exists with world meaning.** `WoodenDoor.use()` searches the inventory for a `Key` whose `opensId` matches. `Player.useObject()` plays the `"unlock"` sound on any successful use.
- **Keys are handled in two places.**
  - `KeyHandler.keyPressed` handles Escape directly: it closes the action menu, otherwise toggles Paused.
  - W/S add to `menuMovement` only while `actionMenu.isOpen()`.
  - E sets `pendingUse`, which `Player.update()` reads through `consumeUsePress()`.
- **Inventory layout and navigation:**
  - The inventory has 12 slots (2 rows × 6), indexed `row*6+col`.
  - It is navigated with a time-threshold method, `Inventory.updateSelectedInventorySlot`.
  - The panel sits at (96, 128) and is 832×512. Slots are about 118px wide with about 17px margins, starting `inventoryHeight/4` from the top.
- **The world menu** is drawn at (64, 64) and is 448px wide, so it overlaps the inventory.

## Agreed design (user-confirmed answers)
1. **Actions:** add `Drop` and `Combine` to `Action`. Add `GameObject.getInventoryActions()`, which returns `{Use, Examine, Combine, Drop}` for **every** item. Using an item that does nothing says "Hmm... nothing happens."
2. **Menu:** reuse `ActionMenu`. Add `openForItem(item, slot)`, which creates a single target with no target-picking level. Add an `int inventorySlot` field to `Target` (−1 for world targets).
3. **Modes:** add `InventoryMode { Browsing, ItemMenu, Combining }`.
4. **E with the inventory open:** opens the item menu for the selected slot, but only if the slot has an item. It never opens the world menu.
5. **World menu plus inventory:** both can be open at once (world menu first, then I). While the world menu is open, **no inventory slot is highlighted**.
6. **Escape:** Combining → Browsing, ItemMenu → Browsing, and Browsing → **closes the inventory**. With no inventory open it still toggles pause.
7. **I key and after actions:** I closes the inventory from any mode and resets it to Browsing. The inventory stays open after actions.
8. **Examine:** add `protected String description` plus `examine()`, which returns it, to `GameObject` **and** `Npc`. Set the text in the subclass constructors; don't change Npc's constructor signature. Show it with `player.say()`. This also fixes Examine in the world menu.
9. **Use:** one `use(Player)` serves both world and inventory, since an item may be usable from either. Move sounds out of `Player.useObject()` into the objects (the door plays `"unlock"`). Add `player.consume(GameObject)`, which removes the item from the inventory or from the world (via despawn).
10. **Drop:** `GameObjectManager.spawn(GameObject obj, int worldX, int worldY)` in pixel coordinates. Drop at the player's exact `worldX/worldY`, with no tile snapping. Also add `despawn(obj)`, add a public getter for the manager on GamePanel, and make `setObject()` use `spawn`.
11. **Combine interaction:**
    - Choosing Combine starts Combining mode; the player picks the second item on the grid.
    - The source slot gets a distinct highlight, and a hint reads "Combine X with…".
    - E on the source slot cancels. E on an empty slot does nothing and stays in the mode.
    - On success the cursor moves to the new item. On failure the message is "I can't combine those."
12. **Combine rules:**
    - Add `CombineResult combineWith(GameObject other, Player p)` to `GameObject`, defaulting to `null`.
    - Try `a.combineWith(b)`, then `b.combineWith(a)`.
    - `CombineResult` holds the items to remove, the items to add, and an optional message.
    - Check for space first. If there's not enough, fail with "Inventory is full." and change nothing.
13. **Item menu drawing:** size the menu to its content (about 200px). Draw it to the **right** of the selected slot, and flip it to the left when it would go off screen. The world menu stays unchanged.
14. **Hint:** hide "Press E for action menu" while the inventory is open. Show the combine hint in Combining mode.
15. **Test content:**
    - **Book:** Use reads it with `say()`.
    - **Potion:** Use says "You feel refreshed" and consumes itself.
    - **Stick + Cloth → Torch:** a combine demo.
    - Use placeholder images from existing sprites, and spawn the items near the player's start (tile 5, 16) in `setObject()`.

## Suggested skills
- None are required for the implementation itself.
- Use `anthropic-skills:grilling` only if a new design question comes up that the list above doesn't answer.

## Notes
- The user is Jankizki, working in the Europe/Helsinki timezone. Keep replies concise.
- Implement in small steps and keep the existing code style: tabs, `_field` naming for private fields, and `this.` usage.
