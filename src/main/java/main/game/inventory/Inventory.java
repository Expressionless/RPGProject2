package main.game.inventory;

import java.util.HashSet;
import java.util.Set;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

import io.sly.helix.utils.io.Serializable;
import io.sly.helix.utils.math.Rectangle;
import io.sly.helix.utils.math.Vector2D;
import main.GameData;
import main.constants.ApplicationConstants;
import main.constants.InventoryConstants;
import main.game.Entity;
import main.game.EntityManager;
import main.game.RpgGame;
import main.game.item.ItemInfo;
import main.game.item.ItemType;

/**
 * Basic Stackable Inventory
 * @author Sly
 *
 */
public abstract class Inventory extends Entity implements Serializable {
	public static final float INV_X = 40 - ApplicationConstants.CAMERA_WIDTH / 4;
	public static final float INV_Y = 15 - ApplicationConstants.CAMERA_HEIGHT / 8;
	
	public static final Color COL = new Color(1, 1, 1, 0.6f);
	
	// Max Stack
	public final int MAX_STACK = 50;
	
	private final Slot[][] slots;
	
	private boolean visible;
	private Vector2D screenPos;
	private Rectangle bounds;
	
	private Set<ItemType> allowedTypes;

	public abstract Inventory copy();
	
	public Inventory(EntityManager em, Vector2D screenPos, int w, int h) {
		super(em, screenPos);
		this.visible = false;
		this.slots = new Slot[h][w];
		this.screenPos = screenPos;
		this.bounds = defineBounds(screenPos, w, h);
		this.allowedTypes = new HashSet<>();
		
		initSlots(w, h);
	}
	
	public Inventory(EntityManager em, int w, int h) {
		this(em, new Vector2D(INV_X, INV_Y), w, h);
	}

	private Rectangle defineBounds(Vector2D screenPos, int w, int h) {
		float x = screenPos.getX();
		float y = screenPos.getY();
		float width = w * (Slot.SPRITE.getWidth() + InventoryConstants.INVENTORY_MARGIN);
		float height = h * (Slot.SPRITE.getHeight() + InventoryConstants.INVENTORY_MARGIN);
		return new Rectangle(x, y, width, height);
		
	}
	
	public void step(float delta) {
		// Track the screen
		Vector3 camera = this.getData().getCurrentCamera().position;
		this.getBounds().setX(screenPos.getX() + camera.x);
		this.getBounds().setY(screenPos.getY() + camera.y);
		
		for(Slot[] row : slots) {
			for(Slot slot: row) {
				slot.update();
			}
		}
	}
	
	public void render(SpriteBatch b) {
		if(this.isVisible()) {			
			for(int i = 0; i < this.slots.length; i++) {
				for(int j = 0; j < this.slots[i].length; j++) {
					this.slots[this.slots.length - 1 - i][this.slots[i].length - 1 - j].render(b, COL);
				}
			}
		}
	}

	public boolean remove(Slot s, int amount) {
		return s.remove(amount);
	}
	
	public boolean remove(Slot s) {
		return this.remove(s, s.getAmount());
	}
	
	public boolean add(ItemInfo item, int amount, boolean dryRun) {
		if(!verify(item.getType()))
			return false;
		int row, column;
		Slot firstFree = null;
		
		for(row = 0; row < slots.length; row++) {
			for(column = 0; column < slots[row].length; column++) {
				Slot currentSlot = slots[row][column];
				
				// If found the first free slot, make note of it and move on
				if(firstFree == null && currentSlot.isEmpty()) {
					if(!item.getFlag("stackable")) {
						if(!dryRun) {
							this.addToSlot(currentSlot, item, amount);
						}
						return true;
					}
					firstFree = currentSlot;
				}
				// Skip the slot if it contains an item that is not the same as what we're adding
				// or if the item we're adding is not stackable
				if(!currentSlot.isEmpty())
					if(!currentSlot.contains(item) || !item.getFlag("stackable"))
						continue;
				
				// Add the item to the inv
				if(!dryRun) {
					this.addToSlot(currentSlot, item, amount);
				}
				
				return true;				
			}
		}
		
		if(firstFree != null) {
			if(!dryRun) {
				firstFree.add(item, amount);
			}
			return true;
		}
		
		return false;
	}
	
	public boolean add(ItemInfo item, int amount) {
		return this.add(item, amount, false);
	}
	
	private void addToSlot(Slot s, ItemInfo item, int amount) {
		s.add(item, amount);
	}
	
	private void initSlots(int w, int h) {

		int row, column;
		float x, y;
		for(column = 0; column < h; column++) {
			y = this.getPos().getY() - column * (Slot.SPRITE.getHeight() + InventoryConstants.INVENTORY_MARGIN);
			for(row = 0; row < w; row++) {
				x = this.getPos().getX() + row * (Slot.SPRITE.getWidth() + InventoryConstants.INVENTORY_MARGIN);
				slots[column][row] = new Slot(this, new Vector2D(x, y), h * column + row);
			}
		}
	}
	
	// Allowed ItemType handlers
	public void clearAllowedTypes() {
		this.allowedTypes.clear();
	}
	
	public void resetAllowedTypes() {
		ItemType[] vals = ItemType.values();
		for(ItemType item : vals) {
			this.addAllowedType(item.name());
		}
	}
	
	public boolean addAllowedType(String type) {
		ItemType t = ItemType.valueOf(type);
		return this.allowedTypes.add(t);
	}
	
	public boolean addAllowedTypes(String... types) {
		for(String type : types)
			if(!this.addAllowedType(type))
				return false;
		return true;
	}
	
	public void removeAllowedType(String type) {
		this.allowedTypes.remove(ItemType.valueOf(type));
	}
	
	public boolean verifyType(ItemType type) {
		for(ItemType t : this.allowedTypes)
			if(type.name() == t.name())
				return true;
		return false;
	}
	
	// Getters and Setters and condition checks
	/**
	 * Check whether the inventory is full, specifying an item ID will check the inventory for items
	 * with the same ID at the same time, and return false if they exist. More cpu efficient than checking
	 * if the inventory is full or if it contains(int ID)
	 * @param id - ID of the item to check for
	 * @return - true if the inventory is full and does not contain the specified item, false if it is empty or does contain the item
	 */
	public boolean isFull(int itemID) {
		int i, j;
		
		for(i = 0; i < this.slots.length; i++) {
			for(j = 0; j < this.slots[i].length; j++) {
				ItemInfo item = this.slots[i][j].getItem();
				if(item == null)
					return false;
				
				if(itemID != -1 && item.ID == itemID)
					return false;
			}
		}
		return true;
	}
	
	public Set<ItemType> getAllowedItemTypes() {
		return this.allowedTypes;
	}
	
	public Slot getFirstFree() {
		for(Slot[] row : this.getSlots()) {
			for(Slot slot : row)
				if(slot.isEmpty())
					return slot;
		}
		
		return null;
	}
	
	public Slot getSlot(int x, int y) {
		return slots[x][y];
	}
	
	public Slot[][] getSlots() {
		return slots;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	public RpgGame getGame() {
		return game;
	}
	
	public GameData getGameData() {
		return game.getGameData();
	}
	
	public int getInventoryWidth() {
		return slots[0].length;
	}
	
	public int getInventoryHeight() {
		return slots.length;
	}
	
	private boolean verify(ItemType type) {
		// Null checks
		if(slots.length == 0)
			return false;
		if(slots[0].length == 0)
			return false;
		
		return this.verifyType(type);
	}
	
	public Rectangle getBounds() {
		return this.bounds;
	}
	
	public Vector2D getScreenPos() {
		return screenPos;
	}
}
