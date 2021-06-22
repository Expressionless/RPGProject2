package main.game.entities.mobs.ai.archetype.aggro;

import main.game.entities.Mob;
import main.game.entities.mobs.RangedEnemy;
import main.game.entities.mobs.ai.state.MobState;

public class RangedAttackAI extends ChaseAI {

	public static final int ATTACK_ALARM = 1, CAST_ALARM = 2;
	
	public RangedAttackAI(Mob mob) {
		super(mob);
	}

	@Override
	protected void initStates() {
		super.initStates();
		
		this.addState(MobState.ATTACK, () -> {
			if(mob.getAlarm(CAST_ALARM).isActive())
				return MobState.ATTACK;
			else {
				if(this.stateMachine.getLastState() != null)
					return this.stateMachine.getLastState();
				else return MobState.IDLE;
			}
		});
		
		this.overwriteState(MobState.CHASE, () -> {
			if(target == null)
				return MobState.IDLE;
			
			float distToTarget = mob.getPos().getDistTo(target.getPos());
			
			if(distToTarget > mob.getStat("sight")) {
				this.search();
				return MobState.SEARCHING;
			} else {
				if(distToTarget < mob.getStat("attack_range")) {
					this.cast();
					return MobState.ATTACK;
				} else {
					mob.moveTo(target.getPos(), mob.getStat("speed"));
					return MobState.CHASE;
				}
			}
		});
	}
	
	private void cast() {
		if(!(mob instanceof RangedEnemy))
			return;
		
		mob.setAlarm(CAST_ALARM, (int)mob.getStat("cast_time"), () -> {
			((RangedEnemy)mob).fireProjectile(mob.getStat("attack"), mob.getStat("proj_speed"), target.getPos());
		});
	}
}
