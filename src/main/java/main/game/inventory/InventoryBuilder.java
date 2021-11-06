package main.game.inventory;

import helix.utils.math.Point;
import main.game.RpgGame;
import main.game.inventory.subtypes.GenericInventory;

public class InventoryBuilder {

	private boolean visible;
	private Point screenPos;
	private int width, height;
	
	private String[] allowedTypes;
	
	private RpgGame rpgGame;
	
	public InventoryBuilder(RpgGame game) {
		this.rpgGame = game;
	}
	
	public InventoryBuilder setVisible(boolean visible) {
		this.visible = visible;
		return this;
	}
	
	public InventoryBuilder setScreenPos(Point screenPos) {
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
		GenericInventory inventory = new GenericInventory(rpgGame, screenPos, width, height);
		inventory.setVisible(visible);
		if(allowedTypes != null) {
			inventory.clearAllowedTypes();
			inventory.addAllowedTypes(allowedTypes);
		}
		return inventory;
	}
	
}
