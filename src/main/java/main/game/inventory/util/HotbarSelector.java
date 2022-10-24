package main.game.inventory.util;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import io.sly.helix.annotations.QueueAsset;
import io.sly.helix.utils.math.NumberUtils;
import io.sly.helix.utils.math.Vector2D;
import main.constants.InventoryConstants;
import main.game.Entity;
import main.game.EntityManager;

import main.game.inventory.Inventory;
import main.game.inventory.Slot;
import main.game.inventory.subtypes.HotbarInventory;
import main.game.item.ItemInfo;

public class HotbarSelector extends Entity {

	@QueueAsset(ref="res/sprites/UI/inventory/selector.png")
	public static String SPRITE_REF;
	
	private HotbarInventory focusedInventory;
	private int currentSlot = 5;
	
	public HotbarSelector(EntityManager entityManager, Vector2D pos, HotbarInventory inventory) {
		super(entityManager, pos);
		this.focusedInventory = inventory;
		this.addSprite(SPRITE_REF);
	}

	@Override
	public void step(float delta) {
		// Keep it within range of the inventory
		currentSlot = (int)NumberUtils.loop(currentSlot, 0, this.focusedInventory.getWidth() - 1);
		
		Slot slot = this.getCurrentSlot();
		this.setPos(slot.getPos().copy());
	}
	
	@Override
	protected void draw(SpriteBatch b) {
		Slot currentSlot = this.getCurrentSlot();
		ItemInfo item = currentSlot.getItem();
		if(item != null) {
			item.getSprite().draw(b, this.getPos().getX() + InventoryConstants.INV_ITEM_OFFSET_X, this.getPos().getY() + InventoryConstants	.INV_ITEM_OFFSET_Y);
			if(item.getFlag("stackable"))
				Slot.inventoryFont.draw(b, Integer.toString(currentSlot.getAmount()), currentSlot.getPos().getX() + Slot.SPRITE.getWidth() - 3, currentSlot.getPos().getY() + 3);
		}
	}

	// Getters and Setters
	public Inventory getFocusedInventory() {
		return focusedInventory;
	}

	public void setFocusedInventory(HotbarInventory focusedInventory) {
		this.focusedInventory = focusedInventory;
	}
	
	public void setCurrentSlot(int pos, boolean relative) {
		if(relative)
			pos = this.currentSlot + pos;
		this.currentSlot = pos;
	}
	
	public int getCurrentSlotPos() {
		return currentSlot;
	}
	
	public Slot getCurrentSlot() {
		return this.focusedInventory.getSlot(currentSlot);
	}

}
