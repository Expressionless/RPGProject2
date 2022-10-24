package main.game.item;

import io.sly.helix.utils.math.Vector2D;
import main.game.EntityManager;
import main.game.world.World;

public class ItemSpawner {

	private final EntityManager entityManager;

	public ItemSpawner(EntityManager em, World world) {
		this.entityManager = em;
	}
	
	public Item spawnItem(Vector2D pos, int id, int amount) {
		Item item = new Item(entityManager, pos, id, amount);
		entityManager.add(item);
		return item;
	}
	/**
	 * Spawn a single item of itemID: itemID with position: pos
	 * @param pos - {@link helix.utils.math.Point}
	 * @param itemID
	 */
	public Item spawnItem(Vector2D pos, int itemID) {
		return this.spawnItem(pos, itemID, 1);
	}

	public Item spawnItem(double x, double y, int itemID) {
		return this.spawnItem(new Vector2D(x, y), itemID);
	}
	
	public Item spawnItem(double x, double y, int itemID, int amount) {
		return this.spawnItem(new Vector2D(x, y), itemID, amount);
	}
	
	public Item spawnItem(Vector2D pos, String name, int amount) {
		return this.spawnItem(pos, ItemInfo.idOf(name), amount);
	}

	public Item spawnItem(Vector2D pos, String name) {
		return this.spawnItem(pos, name, 1);
	}

	public Item spawnItem(double x, double y, String name) {
		return this.spawnItem(new Vector2D(x, y), ItemInfo.idOf(name), 1);
	}
	
	public Item spawnItem(double x, double y, String name, int amount) {
		return this.spawnItem(new Vector2D(x, y), ItemInfo.idOf(name), amount);
	}
}
