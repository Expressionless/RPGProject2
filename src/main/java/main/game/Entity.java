package main.game;

import io.sly.helix.game.entities.HelixEntity;
import io.sly.helix.utils.math.Vector2D;
import main.GameData;
import main.game.entities.mobs.neutral.Player;

public abstract class Entity extends HelixEntity {

	protected final RpgGame game;
	protected final EntityManager entityManager;

	private boolean isActive = true;
	
	public Entity(EntityManager em, Vector2D pos) {
		super(em.getGame().getData(), pos);
		this.game = em.getGame();
		this.entityManager = em;
	}
	
	public RpgGame getGame() {
		return game;
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	public GameData getGameData() {
		return this.game.getGameData();
	}
	
	public Player getPlayer() {
		return this.getGameData().getPlayer();
	}

	public void setActive(boolean active) {
		this.isActive = active;
	}

	public boolean isActive() {
		return isActive;
	}

	@Override
	public boolean equals(Object other) {
		if(!(other instanceof Entity))
			return false;

		Entity e = (Entity)other;
		return e.id == this.id;
	}

}
