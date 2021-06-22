package main.game.entities.utils;

import main.game.enums.DamageType;

public class RangedAttackInfo extends AttackInfo {

	public final float speed;
	
	public RangedAttackInfo(DamageType type, float amount, float speed) {
		super(type, amount);
		
		this.speed = speed;
	}

}
