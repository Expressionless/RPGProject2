package main.game.entities.utils;

import main.game.enums.DamageType;

public class AttackInfo {

	public final DamageType type;
	public final float amount;
	
	public AttackInfo(DamageType type, float amount) {
		this.type = type;
		this.amount = amount;
	}
	
}
