package main.game;


import io.sly.helix.utils.math.Vector2D;
import main.GameData;
import main.game.entities.mobs.neutral.Player;

public abstract class Entity extends io.sly.helix.game.entities.Entity {

	private final RpgGame game;
	
	public Entity(RpgGame game, Vector2D pos) {
		super(game.getData(), pos);
		this.game = game;
	}
	
	public RpgGame getGame() {
		return game;
	}
	
	public GameData getGameData() {
		return this.game.getGameData();
	}
	
	public Player getPlayer() {
		return this.getGameData().getPlayer();
	}

}
