package main.game.item;

import io.sly.helix.utils.math.Vector2D;
import main.game.RpgGame;

public class ItemSpawner {

	private RpgGame game;
	
	public ItemSpawner(RpgGame game) {
		this.game = game;
	}
	
	public void spawnItem(Vector2D pos, int id, int amount) {
		new Item(game, pos, id, amount);
	}
	/**
	 * Spawn a single item of itemID: itemID with position: pos
	 * @param pos - {@link helix.utils.math.Point}
	 * @param itemID
	 */
	public void spawnItem(Vector2D pos, int itemID) {
		this.spawnItem(pos, itemID, 1);
	}

	public void spawnItem(double x, double y, int itemID) {
		this.spawnItem(new Vector2D(x, y), itemID);
	}
	
	public void spawnItem(double x, double y, int itemID, int amount) {
		this.spawnItem(new Vector2D(x, y), itemID, amount);
	}
	
	public void spawnItem(Vector2D pos, String name, int amount) {
		this.spawnItem(pos, ItemInfo.idOf(name), amount);
	}

	public void spawnItem(Vector2D pos, String name) {
		this.spawnItem(pos, name, 1);
	}

	public void spawnItem(double x, double y, String name) {
		this.spawnItem(new Vector2D(x, y), ItemInfo.idOf(name), 1);
	}
	
	public void spawnItem(double x, double y, String name, int amount) {
		this.spawnItem(new Vector2D(x, y), ItemInfo.idOf(name), amount);
	}
}
