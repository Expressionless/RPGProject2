package main.game.inventory;

import io.sly.helix.utils.math.Vector2D;
import main.game.EntityManager;
import main.game.inventory.subtypes.GenericInventory;

public class InventoryBuilder {

	private boolean visible;
	private Vector2D screenPos;
	private int width, height;
	
	private String[] allowedTypes;
	
	private EntityManager entityManager;
	
	public InventoryBuilder(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	public InventoryBuilder setVisible(boolean visible) {
		this.visible = visible;
		return this;
	}
	
	public InventoryBuilder setScreenPos(Vector2D screenPos) {
		this.screenPos = screenPos.copy();
		return this;
	}
	
	public InventoryBuilder setSize(int width, int height) {
		this.width = width;
		this.height = height;
		return this;
	}
	
	public InventoryBuilder setWidth(int width) {
		this.width = width;
		return this;
	}
	
	public InventoryBuilder setAllowedTypes(String... allowedTypes) {
		this.allowedTypes = allowedTypes;
		return this;
	}
	
	public Inventory build() {
		System.out.println("Building inventory");
		GenericInventory inventory = new GenericInventory(entityManager, screenPos, width, height);
		inventory.setVisible(visible);
		if(allowedTypes != null) {
			inventory.clearAllowedTypes();
			inventory.addAllowedTypes(allowedTypes);
		}
		return inventory;
	}
	
}
