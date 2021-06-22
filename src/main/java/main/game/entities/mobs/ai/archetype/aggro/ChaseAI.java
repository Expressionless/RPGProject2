package main.game.entities.mobs.ai.archetype.aggro;

import main.GameData;
import main.game.entities.Mob;
import main.game.entities.mobs.ai.AI;
import main.game.entities.mobs.ai.state.MobState;

public class ChaseAI extends AI {

	protected static final int SEARCH_ALARM = 0;
	protected float distToPlayer;
	protected Mob target;
	protected GameData gameData;
	
	public ChaseAI(Mob mob) {
		super(mob);
		
		this.gameData = mob.getGameData();
		this.player = this.gameData.getPlayer();
	}

	@Override
	protected void initStates() {
		System.out.println("Initializing States");
		this.addState(MobState.IDLE, () -> {
			if(target == null) {
				if(distToPlayer < mob.getStat("sight")) {
					target = player;
					return MobState.CHASE;
				} else {
					
					// Insert IDLE move code here
					
				}
			} else return MobState.CHASE;
			
			return MobState.IDLE;
		});
		
		this.addState(MobState.SEARCHING, () -> {
			// If no target, go back to being idle
			if(this.target == null)
				return MobState.IDLE;
			
			// If ran out of search time, go back to being idle
			if(!mob.getAlarm(SEARCH_ALARM).isActive()) {
				this.target = null;
				return MobState.IDLE;
			}
			
			float distToTarget = mob.getPos().getDistTo(this.target.getPos());
			
			if(distToTarget < mob.getStat("sight")) {
				mob.getAlarm(SEARCH_ALARM).cancel();
				return MobState.CHASE;
			} else mob.moveTo(target, mob.getStat("speed"));
			
			return MobState.SEARCHING;
		});
		
		this.addState(MobState.CHASE, () -> {
			if(target == null)
				return MobState.IDLE;
			
			float distToTarget = mob.getPos().getDistTo(target.getPos());
			
			if(distToTarget > mob.getStat("sight")) {
				this.search();
				return MobState.SEARCHING;
			} else {
				mob.moveTo(target.getPos(), mob.getStat("speed"));
				return MobState.CHASE;
			}
		});
	}
	
	protected void search() {
		mob.setAlarm(SEARCH_ALARM, (int)mob.getStat("search_time"), () -> {
			this.target = null;
		});
	}

	@Override
	protected void step() {
		distToPlayer = mob.getPos().getDistTo(player.getPos());
	}

	public boolean isSearching() {
		return mob.getAlarm(SEARCH_ALARM).isActive();
	}

	@Override
	public boolean isMoving() {
		boolean isChasing = (this.getCurrentState() == MobState.CHASE);
		boolean isSearching = (this.getCurrentState() == MobState.SEARCHING);
		return (isChasing || isSearching);
	}

}
