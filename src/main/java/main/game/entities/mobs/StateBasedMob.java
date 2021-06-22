package main.game.entities.mobs;

import helix.utils.math.Point;
import main.game.RpgGame;
import main.game.entities.Mob;
import main.game.entities.mobs.ai.state.MobState;
import main.game.entities.mobs.ai.state.StateEvent;
import main.game.entities.mobs.ai.state.StateMachine;

public abstract class StateBasedMob extends Mob {
	private final StateMachine stateMachine;

	public StateBasedMob(RpgGame game, Point pos) {
		super(game, pos);
		this.stateMachine = new StateMachine();	
	}

	@Override
	public void step(float delta) {
		this.getStateMachine().next();
	}
	
	public StateMachine getStateMachine() {
		return this.stateMachine;
	}
	
	public boolean addState(MobState state, StateEvent event) {
		return this.stateMachine.addState(state, event);
	}

	public MobState getLastState() {
		return this.stateMachine.getLastState();
	}
	
	public MobState getCurrentState() {
		return this.stateMachine.getCurrentState();
	}
	
}
