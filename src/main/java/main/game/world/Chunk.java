package main.game.world;

import io.sly.helix.utils.io.BinaryReader;
import io.sly.helix.utils.io.BinaryWriter;
import io.sly.helix.utils.io.Serializable;
import io.sly.helix.utils.math.Rectangle;
import io.sly.helix.utils.math.Vector2D;
import main.constants.WorldConstants;
import main.game.RpgGame;

public final class Chunk implements Serializable {

	private Rectangle bounds;

	public Chunk(RpgGame game, Vector2D pos) {
		this(game, pos.getX(), pos.getY());
	}

	public Chunk(RpgGame game, float x, float y) {
		this.bounds = new Rectangle(x, y, WorldConstants.CHUNK_WIDTH_PX,
				WorldConstants.CHUNK_HEIGHT_PX);
	}

	// Getters and Setters
	public Rectangle getBounds() {
		return bounds;
	}
	
	// Serialization
	@Override
	public boolean write(BinaryWriter writer, int pos) {
		return true;
	}

	@Override
	public Serializable parse(BinaryReader reader, int pos) {
		return null;
	}

}
