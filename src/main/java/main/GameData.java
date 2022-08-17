package main;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector3;

import io.sly.helix.game.Data;
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
	public static final HashMap<String, InputAdapter> INPUT_ADAPTERS = new HashMap<>();
	public final ArrayList<Mob> mobs;
	public final ArrayList<Item> items;
	
	// "Unique" Entities
	private Player player;
	private InventoryCursor cursor;
	private UI ui;
	
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

}
