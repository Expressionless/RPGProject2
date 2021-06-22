package main.game.entities.mobs.ai;

import main.game.entities.Mob;
import main.game.entities.mobs.ai.state.MobState;
import main.game.entities.mobs.ai.state.StateEvent;
import main.game.entities.mobs.ai.state.StateMachine;
import main.game.entities.mobs.neutral.Player;

public abstract class AI {

	public final StateMachine stateMachine;
	
	public final Mob mob;
	public abstract boolean isMoving();
	
	protected abstract void initStates();
	protected Player player;
	
	/**
	 * Attach an AI to a mob
	 * @param attachedMob - mob to attach
	 * 
	 * @see {@link Mob}
	 */
	public AI(Mob attachedMob) {
		this.mob = attachedMob;
		this.stateMachine = new StateMachine();
		
		this.initStates();
	}
	
	public void addState(MobState state, StateEvent event) {
		System.out.println("initializing state: " + state.name());
		this.stateMachine.addState(state, event);
	}
	
	public void overwriteState(MobState state, StateEvent event) {
		this.stateMachine.overwriteState(state, event);
	}
	
	public boolean removeState(MobState state) {
		return this.removeState(state);
	}
	
	/**
	 * Update variables for the AI here
	 */
	protected abstract void step();
	/**
	 * Updates AI Variables, and then enters the next state
	 * in it's state machine
	 */
	public void update() {
		this.step();
		this.stateMachine.next();
	}
	
	// Getters and Setters
	public MobState getCurrentState() {
		return this.stateMachine.getCurrentState();
	}	
}
