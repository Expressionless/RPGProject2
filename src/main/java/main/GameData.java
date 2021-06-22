package main;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector3;

import helix.game.Data;
import helix.gfx.SpriteSheet;
import helix.utils.math.Point;
import main.constants.ApplicationConstants;
import main.constants.AssetConstants;
import main.constants.UIConstants;
import main.game.RpgGame;
import main.game.entities.Mob;
import main.game.entities.mobs.neutral.Player;
import main.game.inventory.Slot;
import main.game.inventory.util.InventoryCursor;
import main.game.item.Item;
import main.game.item.ItemInfo;
import main.game.ui.UI;
import main.game.ui.components.Bar;

public final class GameData extends Data {
	
	public static final ArrayList<ItemInfo> ITEM_TYPES = new ArrayList<>();
	public final ArrayList<Mob> mobs;
	public final ArrayList<Item> items;
	
	// "Unique" Entities
	private Player player;
	private InventoryCursor cursor;
	private UI ui;

	@Override
	public void init() {
		// Init Bar Sprite
		Bar.BAR_SPRITE = new SpriteSheet(this, Bar.BAR_DISP_SPRITE_REF, 8, 12);
		Bar.bar = this.createSprite(Bar.BAR_SPRITE_REF);
		
		// Init inventory data
		Slot.SPRITE = this.createSprite(Slot.SPRITE_REF);
		Slot.inventoryFont.getData().setScale(0.25f);
		
		// Init Item Data
		Item.ITEM_SHEET = new SpriteSheet(this, AssetConstants.ITEMS_DIRECTORY, 8, 8);
		
		
		Bar.left_disp = Bar.BAR_SPRITE.getSubSprite(0, 0);
		Bar.center_disp = Bar.BAR_SPRITE.getSubSprite(1, 0);
		Bar.right_disp = Bar.BAR_SPRITE.getSubSprite(2, 0);
				
		Bar.left_disp.setScale(UIConstants.BAR_SCALE);
		Bar.center_disp.setScale(UIConstants.BAR_SCALE);
		Bar.right_disp.setScale(UIConstants.BAR_SCALE);
		
		
		cursor = new InventoryCursor(this.getGame());
	}
	
	public GameData(RpgGame game) {
		super(game);
		mobs = new ArrayList<>();
		items = new ArrayList<>();
	}

	@Override
	protected void dispose() {
		items.removeIf((Item item) -> {
			return item.willDispose();
		});
		mobs.removeIf((Mob mob) -> {
			return mob.willDispose();
		});
	}
	
	/**
	 * Convert UI Coordinates to a position in the game
	 * @param x - X coord
	 * @param y - Y Coord
	 * @return
	 */
	public Point toScreenCoords(float x, float y) {
		Vector3 camPos = this.getCamera().position;
		x = (x - camPos.x + ApplicationConstants.CAMERA_WIDTH / 2) * ApplicationConstants.RATIO_X;
		y = (y - camPos.y - ApplicationConstants.CAMERA_HEIGHT / 2) * (-ApplicationConstants.RATIO_Y);
		return new Point(x, y);
	}

	/**
	 * Convert Game Coordinates to UI Coordinates
	 * @param x
	 * @param y
	 * @return
	 */
	public Point toGameCoords(float x, float y) {
		Vector3 camPos = this.getCamera().position;
		x = camPos.x - ApplicationConstants.CAMERA_WIDTH / 2 + (x / ApplicationConstants.RATIO_X);
		y = camPos.y + ApplicationConstants.CAMERA_HEIGHT / 2 + (-y / ApplicationConstants.RATIO_Y);
		return new Point(x, y);
	}
	
	// Getters and Setters

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
	
	public InventoryCursor getCursor() {
		return cursor;
	}

	public void setCursor(InventoryCursor cursor) {
		this.cursor = cursor;
	}

	public RpgGame getGame() {
		return (RpgGame)this.getBaseGame();
	}
	
	public void setUI(UI ui) {
		this.ui = ui;
	}

}
