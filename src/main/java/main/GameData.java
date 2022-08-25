package main;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.InputAdapter;

import io.sly.helix.game.Data;
import io.sly.helix.utils.math.Vector2D;
import main.game.RpgGame;
import main.game.entities.Mob;
import main.game.entities.mobs.neutral.Player;
import main.game.inventory.util.InventoryCursor;
import main.game.item.Item;
import main.game.item.ItemInfo;
import main.game.ui.UI;

public final class GameData {
	
	public static final ArrayList<ItemInfo> ITEM_TYPES = new ArrayList<>();
	public static final HashMap<String, InputAdapter> INPUT_ADAPTERS = new HashMap<>();
	public final ArrayList<Mob> mobs;
	public final ArrayList<Item> items;
	
	// "Unique" Entities
	private Player player;
	private InventoryCursor cursor;
	private UI ui;
	
	public GameData(RpgGame game) {
		mobs = new ArrayList<>();
		items = new ArrayList<>();
	}

	protected void dispose() {
		items.removeIf((Item item) -> {
			return item.willDispose();
		});
		mobs.removeIf((Mob mob) -> {
			return mob.willDispose();
		});
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
		return (RpgGame)this.getGame();
	}
	
	public void setUI(UI ui) {
		this.ui = ui;
	}

	public Vector2D toGameCoords(int screenX, int screenY) {
		return null;
	}

}
