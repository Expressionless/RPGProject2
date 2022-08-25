package main.game.world;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Logger;

import io.sly.helix.utils.io.BinaryReader;
import io.sly.helix.utils.io.BinaryWriter;
import io.sly.helix.utils.io.Serializable;
import io.sly.helix.utils.math.Vector2D;
import main.constants.WorldConstants;
import main.game.Entity;
import main.game.RpgGame;
import main.game.entities.Mob;
import main.game.entities.mobs.neutral.Player;
import main.game.item.ItemSpawner;

public final class World implements Serializable {
	private static final Logger log = Logger.getLogger(World.class.getCanonicalName());

	/**
	 * Item Spawner to help with spawning items
	 */
	public final ItemSpawner itemSpawner;
	
	// Unique Entities
	private Player player;

	private Chunk[][] chunks;
	private RpgGame game;

	public World(RpgGame game, int width, int height) {
		this.game = game;
		this.itemSpawner = new ItemSpawner(game);
		chunks = new Chunk[height][width];

		initChunks();
	}

	public void step(float delta) {
		this.updateCamera();
	}

	/**
	 * Shorthand for:
	 * <br>
	 * <pre>
	 * 
	 * </pre>
	 * @param pos
	 * @return
	 */
	public Player spawnPlayer(Vector2D pos) {
		if(this.player != null)
			log.warning("PLAYER IS ALREADY CREATED IN WORLD: " + this.toString());
		this.player =  (Player)this.spawnMob(Player.class, pos);
		return this.player;
	}
	
	
	
	public <T extends Entity> T spawnEntity(Class<T> entityType, Vector2D pos) {
		Constructor<T> constructor;

		try {
			constructor = entityType.getConstructor(RpgGame.class, Vector2D.class);
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
			return null;
		}
		T newEntity;
		try {
			newEntity = constructor.newInstance(this.game, pos);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
			return null;
		}
		return newEntity;
	}

	public <T extends Mob> T spawnMob(Class<T> mobType, Vector2D pos) {
		Constructor<T> constructor;

		try {
			constructor = mobType.getConstructor(RpgGame.class, Vector2D.class);
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
			return null;
		}
		T newMob;
		try {
			newMob = constructor.newInstance(this.game, pos);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
			return null;
		}
		return newMob;
	}

	private void updateCamera() {
		// Update Camera
		if (player != null) {
			game.getData().getCurrentCamera().position.x = player.getPos().getX();
			game.getData().getCurrentCamera().position.y = player.getPos().getY();
		}
	}

	private void initChunks() {
		int x, y;
		int xPos = WorldConstants.CHUNK_WIDTH, yPos = WorldConstants.CHUNK_HEIGHT;

		for (y = 0; y < chunks.length; y++) {
			for (x = 0; x < chunks[y].length; x++) {
				chunks[y][x] = new Chunk(game, xPos, yPos);
			}
		}
	}

	// Getters and Setters
	public Player getPlayer() {
		return this.player;
	}

	public Chunk getChunk(int x, int y) {
		return chunks[y][x];
	}

	public RpgGame getGame() {
		return this.game;
	}

	public void setGame(RpgGame game) {
		this.game = game;
	}

	@Override
	public boolean write(BinaryWriter writer, int pos) {
		return false;
	}

	@Override
	public Serializable parse(BinaryReader reader, int pos) {
		return null;
	}
}
