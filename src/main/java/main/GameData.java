package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import io.sly.helix.utils.math.Vector2D;
import main.constants.Constants;
import main.game.RpgGame;
import main.game.entities.Mob;
import main.game.entities.mobs.neutral.Player;
import main.game.inventory.util.InventoryCursor;
import main.game.item.Item;
import main.game.item.ItemInfo;
import main.game.ui.UI;

public final class GameData {
	
	public static final List<ItemInfo> ITEM_TYPES = new ArrayList<>();
	public static final Map<String, InputAdapter> INPUT_ADAPTERS = new HashMap<>();
	public final List<Mob> mobs = new ArrayList<>();
	public final List<Item> items = new ArrayList<>();
	private static final Map<String, BitmapFont> FONTS = new HashMap<>();
	
	// "Unique" Entities
	private Player player;
	private InventoryCursor cursor;
	private UI ui;

	private RpgGame game;
	
	public GameData(RpgGame game) {
		this.game = game;
		this.addFont(Constants.FONT_DEFAULT, new BitmapFont());
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
		return this.game;
	}
	
	public void setUI(UI ui) {
		this.ui = ui;
	}

	public UI getUI() {
		return ui;
	}

	public Vector2D toGameCoords(int screenX, int screenY) {
		return new Vector2D(screenX, screenY);
	}

	public void addFont(String name, BitmapFont f) {
		FONTS.put(name, f);
	}

	public BitmapFont getFont(String name) {
		return FONTS.get(name);
	}
}
