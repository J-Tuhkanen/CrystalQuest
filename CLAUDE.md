# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project overview

CrystalQuest is a 2D top-down tile-based adventure game written in plain Java + Swing (no game engine, no framework). It's an Eclipse-managed project (`.project`/`.classpath`) with no Maven/Gradle/Ant build file and no test suite.

## Build and run

There is no build tool — compile and run directly with `javac`/`java`. The project targets **JDK 21** (`.classpath` declares `JavaSE-21`) and actually requires it: the code uses qualified enum constants in `switch` case labels (e.g. `case Direction.Up:` in [Player.java](src/entity/Player.java) and [Npc.java](src/entity/Npc.java)), a Java 21 language feature. Compiling with an older `javac` (e.g. a JDK 17 on PATH) fails with "an enum switch case label must be the unqualified name of an enumeration constant". If `javac -version` on PATH isn't 21, point at a JDK 21 install explicitly (e.g. `C:\Program Files\Microsoft\jdk-21.0.12.101-hotspot\bin\javac.exe` if present).

Source (`src/`) and non-code resources (`assets/`) are both configured as Eclipse source folders, and code loads images/sounds/maps via `getClass().getResourceAsStream("/subfolder/file.ext")` — so resources must land on the runtime classpath at the same relative paths they have under `assets/`. From the repo root, PowerShell:

```powershell
Get-ChildItem -Recurse -Filter *.java src | ForEach-Object { $_.FullName } | Out-File sources.txt -Encoding utf8
javac -d bin -cp src "@sources.txt"
Copy-Item assets\* bin -Recurse -Force
java -cp bin main.Main
```

(`bin/` is gitignored.) There's also a VS Code launch config ([.vscode/launch.json](.vscode/launch.json)) targeting `main.Main` for use with the Java extension instead of the CLI.

**Do not delete `bin/`.** It looks like a disposable scratch output dir, but `.classpath` declares it as the project's `output` path, so it's also where the VS Code Java extension (redhat.java/JDT) incrementally compiles `.class` files as you edit — the same directory the "Main" launch config in `.vscode/launch.json` runs from. Deleting it doesn't make the extension recompile before the next Run/Debug, so `java -cp bin main.Main` (via the launch config or otherwise) fails with `Error: Could not find or load main class main.Main` until something repopulates it. If it does get deleted (e.g. as part of a CLI build-and-test cycle), rebuild it immediately afterward with the `javac`/`Copy-Item` commands above before handing control back — don't leave it empty.

No linter, formatter, or automated tests exist in this repo.

### In-game controls (for manually verifying changes)
WASD to move, right mouse button to aim/look independent of movement, `E` to open the action/interact menu when standing near an NPC or object, `I` to toggle inventory, `Escape` to pause or close a menu. See [KeyHandler.java](src/main/KeyHandler.java) / [MouseHandler.java](src/main/MouseHandler.java).

## Architecture

**Entry point**: [Main.java](src/main/Main.java) builds a `JFrame` containing a `JLayeredPane` with two panels stacked as layers: `GamePanel` (layer 0, the game world) and `UIPanel` (layer 1, HUD overlay). It also preloads named `Sound` clips into `GamePanel.sounds` before starting the game loop.

**Game loop**: [GamePanel.java](src/ui/GamePanel.java) is the hub — it implements `Runnable` and drives a fixed-60-FPS delta-timed loop (`update()` + `repaint()`). It owns/wires together `KeyHandler`, `MouseHandler`, `CollisionChecker`, `TileManager`, `GameObjectManager`, the `Player`, and the live `objects`/`npcs` lists, plus the `GameState` enum (`Running` / `Paused` / `DialogState`) that gates whether `update()` does anything. `setupGame()` (called once from `Main`) delegates initial world population to `GameObjectManager`.

**Camera model**: the player's `worldX`/`worldY` is the authoritative world-space position; `player.cameraX`/`cameraY` is fixed at screen center and never changes. Everything else (tiles, NPCs, objects) is drawn at `entityWorldPos - player.worldPos + player.cameraPos`, i.e. the world scrolls under a stationary player sprite.

**Entity hierarchy** ([src/entity/](src/entity/)): abstract `Entity` holds shared position/collision/sprite-animation state and loads directional sprite sheets by a naming convention — `imagePrefix + "_{up,down,left,right}_{1,2}.png"` (2 animation frames per direction). `Player` and `Npc` extend it. `Npc` is itself abstract (`updateAction()` is the AI hook); `OldManNpc` is the one concrete example, implementing simple randomized wandering. `Action` is a shared enum (`Examine`, `Talk`, `Pickup`, `Use`) that both NPCs and game objects advertise as their available interactions.

**Collision** ([CollisionChecker.java](src/main/CollisionChecker.java)): per-entity, direction-aware, look-ahead collision checks against three things — the tile grid (`TileManager.worldMap`, with tile-collision determined by a hardcoded list of tile IDs), other `GameObject`s (AABB via each object's `solidArea`), and other NPCs/the player (AABB via each entity's `hitBox`). Results are packaged into a `CollisionInformation` (lists of colliding NPCs + GameObjects), which `Player` converts into the action-menu contents via `getAsActions()` (a name → `Action[]` map).

**World objects** ([src/object/](src/object/)): abstract `GameObject` (image, world position, solid area, optional collision) with concrete `Key`, `TreasureChest`, `WoodenDoor`, each declaring its own `Action[]`. `GameObjectManager` ([src/main/GameObjectManager.java](src/main/GameObjectManager.java)) is where initial world objects and NPCs are spawned at hardcoded coordinates — this is the place to add new starting content.

**Inventory**: [Inventory.java](src/main/Inventory.java) is a fixed 12-slot array (2 rows × 6 columns) on `Player`, with keyboard-navigable slot selection and `TryAdd`/`RemoveAt`.

**Tiles/world map**: [TileManager.java](src/tile/TileManager.java) loads `assets/maps/map.txt`, a CSV grid of tile IDs, resolving each ID to `assets/tiles/<id>.png` and to a collision flag from a hardcoded `collisionTileIds` list.

**UI overlay** ([src/ui/](src/ui/), excluding `GamePanel`): `UIPanel` composes `DialogPanel`, `ActionPanel` (interaction hint text + the action menu), and `InventoryPanel` as layered children above `GamePanel`. These are plain Swing panels that read live state off `GamePanel`/`Player` in `paintComponent` — they're not part of the `update()` loop, Swing's own repaint cycle drives them.
