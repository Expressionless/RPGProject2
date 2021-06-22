package main.game.world;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import helix.game.Serializable;
import helix.utils.io.BinaryReader;
import helix.utils.io.BinaryWriter;
import helix.utils.math.Point;
import helix.utils.math.Rectangle;
import main.constants.WorldConstants;
import main.game.Entity;
import main.game.RpgGame;

public final class Chunk implements Serializable {

	private Rectangle bounds;
	private ArrayList<Entity> entities;

	public Chunk(RpgGame game, Point pos) {
		this(game, pos.getX(), pos.getY());
	}

	public Chunk(RpgGame game, float x, float y) {
		this.bounds = new Rectangle(x, y, WorldConstants.CHUNK_WIDTH * WorldConstants.TILE_WIDTH,
				WorldConstants.CHUNK_HEIGHT * WorldConstants.TILE_HEIGHT);
		this.entities = new ArrayList<>();
	}

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
	public boolean parse(BinaryReader reader, int pos) {
		return true;
	}

}
