package main.game.world;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import io.sly.helix.utils.io.BinaryReader;
import io.sly.helix.utils.io.BinaryWriter;
import io.sly.helix.utils.io.Serializable;
import io.sly.helix.utils.math.Rectangle;
import io.sly.helix.utils.math.Vector2D;
import main.constants.WorldConstants;
import main.game.Entity;
import main.game.RpgGame;

public final class Chunk implements Serializable {

	private Rectangle bounds;
	private List<Entity> entities;

	public Chunk(RpgGame game, Vector2D pos) {
		this(game, pos.getX(), pos.getY());
	}

	public Chunk(RpgGame game, float x, float y) {
		this.bounds = new Rectangle(x, y, WorldConstants.CHUNK_WIDTH * WorldConstants.TILE_WIDTH,
				WorldConstants.CHUNK_HEIGHT * WorldConstants.TILE_HEIGHT);
		this.entities = new ArrayList<>();
	}

	// TODO: Stop concurrentmodification error here somehow
	public void step(float delta) {
		while(entities.size() > WorldConstants.MAX_ENTITIES_PER_CHUNK)
			entities.remove(entities.get(entities.size() - 1));
	}

	public void draw(SpriteBatch batch) {

	}
	
	// Getters and Setters
	public Rectangle getBounds() {
		return bounds;
	}
	
	public void addEntity(Entity entity) {
		if(entities.size() >= WorldConstants.MAX_ENTITIES_PER_CHUNK)
			return;
		entities.add(entity);
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
