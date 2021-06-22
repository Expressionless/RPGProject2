package main.game.entities;

import helix.utils.math.Point;
import main.game.Entity;
import main.game.RpgGame;
import main.game.entities.utils.RangedAttackInfo;
import main.game.enums.DamageType;

public abstract class Projectile extends Entity {

	public final RangedAttackInfo attack;
	public final Point destination;
	
	public Projectile(RpgGame game, RangedAttackInfo attack, Point pos, Point destination, String spriteName) {
		super(game, pos);
		
		this.destination = destination;
		this.attack = attack;
		
		this.addSprite(spriteName);
	}

	@Override
	public void step(float delta) {
		float distRemaining = this.getPos().getDistTo(destination);
		if(distRemaining < this.getWidth() / 2) {
			this.dispose();
		}
		else
			this.moveTo(destination, attack.speed);
	}
	
	@SuppressWarnings("unused")
	private class ProjectileFlags extends AttribHandler<Object> {
		private DamageType damage_type;
		
		
		public void setDamageType(DamageType type) {
			this.damage_type = type;
		}
	}
}
