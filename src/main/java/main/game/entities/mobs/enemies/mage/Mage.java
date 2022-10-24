package main.game.entities.mobs.enemies.mage;

import io.sly.helix.annotations.QueueAsset;
import io.sly.helix.utils.io.BinaryReader;
import io.sly.helix.utils.io.BinaryWriter;
import io.sly.helix.utils.io.Serializable;
import io.sly.helix.utils.math.Angle;
import io.sly.helix.utils.math.Vector2D;
import main.game.EntityManager;

import main.game.entities.mobs.RangedEnemy;
import main.game.entities.mobs.ai.archetype.aggro.RangedAttackAI;
import main.game.entities.projectiles.MageProjectile;

public class Mage extends RangedEnemy<MageProjectile> {	
	@QueueAsset(ref = "res/sprites/mob/enemy/mage/tiny_mage_up_right.png")
	public static String MAGE_UP;
	
	@QueueAsset(ref = "res/sprites/mob/enemy/mage/tiny_mage_right.png")
	public static String MAGE_DOWN;

	public Mage(EntityManager em, Vector2D pos) {
		super(em, MageProjectile.class, pos);
		this.addSprite(MAGE_UP, 4, 750);
		this.addSprite(MAGE_DOWN, 4, 750);
		
		this.setStat("speed", 0.25f);
		this.setStat("sight", 96);
		this.setStat("search_time", 5);

		this.setStat("attack", 20);
		this.setStat("attack_range", 48);
		this.setStat("attack_speed", 0.5f);

		this.setStat("proj_speed", 0.5f);
		this.setStat("proj_range", 512);
		
		this.setStat("cast_time", 0.1f);
		
		this.setAI(new RangedAttackAI(this));
	}
	
	protected void updateSprite() {
		double angle = this.getDirection().getAngle();
		boolean left = (angle >= Angle.TOP.angle && angle < Angle.BOTTOM.angle);
		boolean up = (angle < Angle.LEFT.angle && angle >= Angle.RIGHT.angle);

		if (up)
			this.setSprite(MAGE_UP);
		else
			this.setSprite(MAGE_DOWN);
		this.getSprite().flip(left);

		this.getSprite().start();

		if (!this.ai.isMoving())
			this.getSprite().reset();
	}

	@Override
	public boolean write(BinaryWriter writer, int pos) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Serializable parse(BinaryReader reader, int pos) {
		// TODO Auto-generated method stub
		return null;
	}
}
