package main.game.item;

import helix.utils.math.Point;
import main.game.RpgGame;

public class ItemSpawner {

	private RpgGame game;
	
	public ItemSpawner(RpgGame game) {
		this.game = game;
	}
	
	public void spawnItem(Point pos, int id, int amount) {
		new Item(game, pos, id, amount);
	}
	/**
	 * Spawn a single item of itemID: itemID with position: pos
	 * @param pos - {@link helix.utils.math.Point}
	 * @param itemID
	 */
	public void spawnItem(Point pos, int itemID) {
		this.spawnItem(pos, itemID, 1);
	}

	public void spawnItem(double x, double y, int itemID) {
		this.spawnItem(new Point(x, y), itemID);
	}
	
	public void spawnItem(double x, double y, int itemID, int amount) {
		this.spawnItem(new Point(x, y), itemID, amount);
	}
	
	public void spawnItem(Point pos, String name, int amount) {
		this.spawnItem(pos, ItemInfo.idOf(name), amount);
	}

	public void spawnItem(Point pos, String name) {
		this.spawnItem(pos, name, 1);
	}

	public void spawnItem(double x, double y, String name) {
		this.spawnItem(new Point(x, y), ItemInfo.idOf(name), 1);
	}
	
	public void spawnItem(double x, double y, String name, int amount) {
		this.spawnItem(new Point(x, y), ItemInfo.idOf(name), amount);
	}
}
