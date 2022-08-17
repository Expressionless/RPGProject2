package main.game.entities;

import io.sly.helix.utils.math.Vector2D;
import main.game.Entity;
import main.game.RpgGame;
import main.game.entities.utils.RangedAttackInfo;
import main.game.enums.DamageType;

public abstract class Projectile extends Entity {

	public final RangedAttackInfo attack;
	public final Vector2D destination;
	
	public Projectile(RpgGame game, RangedAttackInfo attack, Vector2D pos, Vector2D destination, String spriteName) {
		super(game, pos);
		
		this.destination = destination;
		this.attack = attack;
		
		this.addSprite(spriteName);
	}

	@Override
	public void step(float delta) {
		float distRemaining = this.getPos().getDistTo(destination);
		if(distRemaining < this.getWidth() / 2) {
			this.queueDispose();
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
