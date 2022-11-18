package main.game.item;

import java.util.Random;

import io.sly.helix.gfx.Sprite;
import io.sly.helix.gfx.SpriteSheet;
import io.sly.helix.utils.math.Vector2D;
import main.constants.AssetConstants;
import main.constants.PlayerConstants;
import main.game.EntityManager;

import main.game.entities.Doodad;
import main.game.entities.mobs.neutral.Player;
import main.game.inventory.Inventory;
import main.game.inventory.subtypes.GenericInventory;
import main.game.inventory.subtypes.HotbarInventory;

public class Item extends Doodad {
	public static SpriteSheet ITEM_SHEET;

	private float animOffset, animPeriodOffset;
	private int amount;


	public final ItemInfo item;

	public Item(EntityManager em, Vector2D pos, int itemID, int amount) {
		super(em, pos);
		this.item = ItemInfo.get(itemID);
		this.attachItemSprite();

		game.getGameData().items.add(this);

		Random r = new Random();
		
		this.animOffset = (float) (r.nextFloat() * (AssetConstants.ITEM_BREATHE_LENGTH / 2 * Math.PI));
		this.animPeriodOffset = (float) (r.nextFloat() * (AssetConstants.ITEM_BREATHE_LENGTH / 2) + AssetConstants.ITEM_BREATHE_LENGTH / 2);
		this.amount = amount;
	}

	public Item(EntityManager em, Vector2D pos, String itemName) {
		this(em, pos, ItemInfo.idOf(itemName), 1);
	}

	public static int[] IDtoImageIndex(int ID) {
		int items_width = ITEM_SHEET.getWidth(); // 16
		int items_height = ITEM_SHEET.getHeight(); // 16

		int x = ID % items_height; // x = 2
		int y = (int) Math.floor(ID / items_width); // y = 0

		return new int[] { x, y };
	}

	private void attachItemSprite() {
		if (this.getSprite() == null) {
			int[] index = IDtoImageIndex(this.getID());
			Sprite s = ITEM_SHEET.getSubSprite(index[0], index[1]).copy();
			s.setName(this.item.name);
			this.setSprite(s);
		}
	}

	@Override
	public void step(float delta) {
		float XScale = (float) (Math
				.sin((double) (getGame().getTicks() + animOffset) / (double) (this.animPeriodOffset)) * 0.2 + 1);
		float YScale = XScale;
		// OSCILLATE BETWEEN 0.8 - 1.2
		if (this.getSprite() != null) {
			this.getSprite().setScale(XScale, YScale);
		}
		if(this.getGame().getGameData().getPlayer() == null)
			return;
		Inventory pInv = this.getGame().getGameData().getPlayer().getInventory();

		if (this.distTo(this.getGame().getGameData().getPlayer()) < AssetConstants.ITEM_SUCK_DISTANCE) {
			if (pInv.add(item, amount, true)) {
				if (this.distTo(this.getGame().getGameData().getPlayer()) <= PlayerConstants.PICKUP_DISTANCE) {
					
					Player player = this.getGame().getGameData().getPlayer();
					HotbarInventory hotbar = player.getHotbar();
					GenericInventory mainInv = player.getInventory();
					
					if (hotbar.add(this.item, this.amount))
						this.entityManager.destroy(this);
					else if(mainInv.add(this.item, this.amount))
						this.entityManager.destroy(this);
				}
				this.moveTo(this.getGame().getGameData().getPlayer(), AssetConstants.ITEM_SPEED * delta);
			}
		}
	}

	// Getters and Setters

	public int getID() {
		return this.item.ID;
	}

	public String getName() {
		return this.item.toString();
	}

	public String toString() {
		return "Item [name=" + item.toString() + ",ID=" + item.ID + ",pos=" + this.getPos().toString() + ",tex="
				+ ((this.getSprite() != null) ? this.getSprite().toString() : "null") + "]";
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}
}
