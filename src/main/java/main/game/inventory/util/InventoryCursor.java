package main.game.inventory.util;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import helix.utils.math.Point;
import main.game.Entity;
import main.game.RpgGame;
import main.game.inventory.Slot;
import main.game.item.ItemInfo;

public final class InventoryCursor extends Entity {

	private ItemInfo item;
	private int amount;

	private boolean quickShift = false;

	public InventoryCursor(RpgGame game) {
		super(game, new Point(0, 0));
		this.item = null;
		this.amount = 0;
	}

	@Override
	public void draw(SpriteBatch b) {
		if (this.item != null) {
			this.item.getSprite().draw(b, getPos().getX(), getPos().getY());
		}
	}

	@Override
	protected void preStep(float delta) {
		super.preStep(delta);
		// Keep the bugs away
		if (this.item == null)
			this.amount = 0;
		if (this.amount == 0)
			this.item = null;
	}

	@Override
	public void step(float delta) {
	}

	public boolean take(Slot s) {
		return this.take(s, s.getAmount());
	}

	public boolean take(Slot s, int amount) {
		if (s.isEmpty())
			return false;

		this.setItem(s.getItem());
		this.setAmount(s.getAmount());

		s.remove(amount);

		return true;
	}

	public boolean swap(Slot s) {
		ItemInfo item = s.getItem();
		int amount = s.getAmount();
		System.out.println(item.ID + " " + amount);

		if (item.ID == this.item.ID && this.item.getFlag("stackable")) {
			if (!this.place(s, this.amount + amount))
				return false;
		} else {
			s.setItem(this.item, this.amount);
			//System.out.println(this.place(s));
			/*
			 * if(!this.place(s)) return false;
			 */
			this.setItem(item, amount);
		}

		return true;
	}

	public boolean place(Slot s, int amount) {
		if (!s.getInventory().verifyType(this.item.getType()))
			return false;
		if (!s.add(this.item, this.amount))
			return false;
		this.setItem(null);

		return true;
	}

	public boolean place(Slot s) {
		return place(s, this.amount);
	}

	// Getters and Setters
	public boolean hasNothing() {
		return (this.item == null && this.amount == 0);
	}

	public void setItem(ItemInfo item, int amount) {
		System.out.println("setting cursor item to: " + ((item != null) ? item.name : "") + " " + amount);
		this.item = item;
		this.amount = amount;
	}

	public void setItem(int id, int amount) {
		this.setItem(ItemInfo.get(id));
	}

	public void setItem(int id) {
		this.setItem(id, 1);
	}

	public void setItem(String name, int amount) {
		this.setItem(ItemInfo.idOf(name), amount);
	}

	public void setItem(ItemInfo item) {
		this.setItem(item, 1);
	}

	public ItemInfo getItem() {
		return this.item;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public boolean getQuickShift() {
		return quickShift;
	}

	public void setQuickShift(boolean quick) {
		this.quickShift = quick;
	}
}
