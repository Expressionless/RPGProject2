package main.game.entities;

import helix.game.GameObject;
import helix.game.Serializable;
import helix.utils.math.Point;
import main.game.Entity;
import main.game.RpgGame;
import main.game.entities.mobs.ai.AI;
import main.game.inventory.subtypes.GenericInventory;
import main.game.item.Item;
import main.game.item.ItemInfo;

public abstract class Mob extends Entity implements Serializable {
	private GenericInventory inventory;
	
	private final MobStats stats;
	
	private Point destination;
	
	/**
	 * AI to control the mob. 
	 */
	protected AI ai;
	protected abstract void updateSprite();
	
	public Mob(RpgGame game, Point pos) {
		super(game, pos);
		this.stats = new MobStats();
		this.destination = pos.copy();
		
		// Do this last, always
		game.getGameData().mobs.add(this);
	}
	
	@Override
	protected void preStep(float delta) {
		super.preStep(delta);

		if(this.getDirection().length() == 0) {
			this.getSprite().restart();
			this.getSprite().stop();
		} else {
			this.updateSprite();
		}
		
		if(this.inventory != null)
			this.inventory.update(delta);
	}
	
	@Override
	public void step(float delta) {
		if(this.isAI())
			this.ai.update();
	}

	@SuppressWarnings("unchecked")
	public <T extends Mob> T findMob(Class<T> searchClass) {
		for(GameObject object : this.getGame().getGameData().mobs) {
			if(searchClass.isInstance(object))
				return (T)object;
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public <T extends Mob> T findNearestMob(Class<T> searchClass) {
		GameObject current = null;
		for(GameObject object : this.getGame().getGameData().mobs) {
			if(!searchClass.isInstance(object))
				continue;
			if(current == null) {
				current = object;
				continue;
			}
			
			float dis1 = this.getPos().getDistTo(object.getPos());
			float dis2 = this.getPos().getDistTo(current.getPos());
			if(dis1 < dis2)
				current = object;
		}
		if(current != null)
			return (T)current;
		else return null;
	}

	public Item findItem(String name) {
		return this.findItem(ItemInfo.idOf(name));
	}
	
	public Item findItem(int ID) {
		for(Item object : this.getGame().getGameData().items) {
			if(Item.class.isInstance(object) && object.getID() == ID)
				return object;
		}
		return null;
	}
	
	public Item findNearestItem() {
		Item current = null;
		
		for(Item object : this.getGame().getGameData().items) {
			if(current == null) {
				current = object;
				continue;
			}
			
			float dis1 = this.getPos().getDistTo(object.getPos());
			float dis2 = this.getPos().getDistTo(current.getPos());
			if(dis1 < dis2)
				current = object;
		}
		
		if(current != null)
			return current;
		else return null;
	}
	
	public Item findNearestItem(String name) {
		return this.findNearestItem(ItemInfo.idOf(name));
	}
	
	public Item findNearestItem(int ID) {
		Item current = null;
		
		for(Item object : this.getGame().getGameData().items) {
			if(object.getID() != ID)
				continue;
				
			if(current == null) {
				current = object;
				continue;
			}
			
			float dis1 = this.getPos().getDistTo(object.getPos());
			float dis2 = this.getPos().getDistTo(current.getPos());
			if(dis1 < dis2)
				current = object;
		}
		
		if(current != null)
			return current;
		else return null;
	}
	
	// Getters and Setters
	public float getStat(String stat) {
		return this.stats.getStat(stat);
	}
	
	public void setStat(String stat, float val) {
		this.stats.setStat(stat, val);
	}
	
	public void addStat(String stat, float val) {
		this.stats.addStat(stat, val);
	}
	
	public void subStat(String stat, float val) {
		this.stats.subStat(stat, val);
	}

	public MobStats getMobStats() {
		return stats;
	}
	
	public GenericInventory getInventory() {
		return this.inventory;
	}
	
	public void setInventory(GenericInventory inv) {
		this.inventory = inv;
	}
	
	public Point getDest() {
		return destination;
	}
	
	public void setDest(Point point) {
		this.destination = point;
	}
	
	public boolean isAI() {
		return (this.ai != null);
	}
	
	public void setAI(AI ai) {
		this.ai = ai;
	}
	
	// Helper class
	protected class MobStats extends AttribHandler<Float> {
		public float speed = 0, maxSpeed = 1.5f;
		public float vel = 0, acc = 0;
		
		public float defence, health, maxHealth;
		public float sight, search_time;
		
		public float attack, attack_range, attack_speed;
		
		public float proj_speed, proj_range, cast_time;
		
		@Override
		public void setStat(String stat, Float val) {
			if(stat == "maxHealth")
				super.setStat("health", val);
			super.setStat(stat, val);
		}
		
		public void addStat(String stat, float val) {
			float newVal = this.getStat(stat) + val;
			this.setStat(stat, newVal);
		}
		
		public void subStat(String stat, float val) {
			this.addStat(stat, -val);
		}
	}
}
