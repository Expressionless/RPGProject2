package main.game.world;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import java.util.logging.Logger;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import io.sly.helix.game.entities.GameObject;
import io.sly.helix.utils.io.BinaryReader;
import io.sly.helix.utils.io.BinaryWriter;
import io.sly.helix.utils.io.Serializable;
import io.sly.helix.utils.math.Vector2D;

import main.constants.WorldConstants;
import main.game.Entity;
import main.game.EntityManager;
import main.game.RpgGame;
import main.game.entities.Mob;
import main.game.entities.mobs.neutral.Player;
import main.game.inventory.util.InventoryCursor;
import main.game.item.ItemSpawner;

public final class World implements Serializable {
	private static final Logger log = Logger.getLogger(World.class.getCanonicalName());

	/**
	 * Item Spawner to help with spawning items
	 */
	public final ItemSpawner itemSpawner;

	public final int width, height;

	private static EntityManager entityManager;
	// Unique Entities
	private Player player;

	private ShapeRenderer shapeRenderer;

	private Chunk[][] chunks;
	private RpgGame game;

	public World(RpgGame game, int width, int height) {
		this.game = game;
		entityManager = new EntityManager(this);
		game.getGameData().setCursor(new InventoryCursor(entityManager));
		this.itemSpawner = new ItemSpawner(entityManager, this);
		chunks = new Chunk[height][width];
		this.width = width;
		this.height = height;

		initChunks();
		shapeRenderer = new ShapeRenderer();
		// System.out.println(this.getGame().getData().getViewport().getCamera().combined);
		shapeRenderer.setProjectionMatrix(this.getGame().getData().getCurrentCamera().combined);
	}

	/**
	 * Get the {@link Chunk} a {@link GameObject} is in
	 * @param o
	 * @return
	 */
	public Chunk getObjectChunk(GameObject o) {
		int x = (int)Math.floor(o.getPos().getX() / WorldConstants.CHUNK_WIDTH_PX);
		int y = (int)Math.floor(o.getPos().getY() / WorldConstants.CHUNK_HEIGHT_PX);
		if(x < 0 || x > width) {
			return chunks[0][0];
		}

		if(y < 0 || y > height) {
			return chunks[0][0];
		}

		return chunks[y][x];
	}

	public Chunk getPlayerChunk() {
		return getObjectChunk(player);
	}

	public void step(float delta) {
		this.updateCamera();
		// player.update(delta);
		entityManager.update(delta);
		// for(Entity entity : entities) {
		// 	entity.update(delta);
		// }
		
	}

	public void render(SpriteBatch sb, float delta) {
		// only render the current chunk
		// player.render(sb);
		// BitmapFont font = getGame().getGameData().getFont(Constants.FONT_DEFAULT);
		entityManager.render(sb);
		// for(Entity entity : entities) {
		// 	entity.render(sb);
		// }
		// sb.end();
		// shapeRenderer.begin(ShapeType.Line); //I'm using the Filled ShapeType, but remember you have three of them 
		// for(Chunk[] chunkRow : chunks) {
		// 	for(Chunk chunk : chunkRow) {
		// 		// if(chunk.equals(getPlayerChunk()))
		// 		// 	font.draw(sb, "Hello", chunk.getBounds().getX(), chunk.getBounds().getY());
		// 		renderRect(new Rectangle(0, 0, 128, 128));
		// 		// font.draw(sb, "0,0", 0, 0);
		// 	}
		// // }
		// shapeRenderer.end(); 
		// sb.begin();
		// player.render(sb);

		// Chunk chunk = getPlayerChunk();
		// font.draw(sb, "Hello", chunk.getBounds().getX(), chunk.getBounds().getY());
		// renderRect(chunk.getBounds());
		// renderRect(new Rectangle(32, 32, 32, 32));
		// System.out.println(this.getPlayer().getPos());
		// Gdx.gl.glClearColor(0, 0, 0.2f, 1); 
		// Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); 
	 
		
	}
	
	// private void renderRect(Rectangle rectangle) {
		
	// 	float x = rectangle.getX();
	// 	float y = rectangle.getY();

	// 	float width = rectangle.getWidth();
	// 	float height = rectangle.getHeight();
	// 	shapeRenderer.rect(x, y, width, height); //assuming you have created those x, y, width and height variables 
	// }

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
		this.player =  this.spawnMob(Player.class, pos);
		this.game.getGameData().setPlayer(this.player);
		log.info("SPAWNED PLAYER: " + player.getPos());
		return this.player;
	}
	
	public <T extends Entity> T spawnEntity(Class<T> entityType, Vector2D pos) {
		Constructor<T> constructor;

		try {
			constructor = entityType.getConstructor(EntityManager.class, Vector2D.class);
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
			return null;
		}
		T newEntity;
		try {
			newEntity = constructor.newInstance(entityManager, pos);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
			return null;
		}
		log.info("Spawned " + newEntity.getClass());
		entityManager.add(newEntity);
		return newEntity;
	}

	public <T extends Mob> T spawnMob(Class<T> mobType, Vector2D pos) {
		return spawnEntity(mobType, pos);
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
		float xPos = WorldConstants.CHUNK_WIDTH_PX, yPos = WorldConstants.CHUNK_HEIGHT_PX;

		for (y = 0; y < chunks.length; y++) {
			for (x = 0; x < chunks[y].length; x++) {
				chunks[y][x] = new Chunk(game, xPos * x, yPos * y);
			}
		}
	}

	public void addEntity(Entity e) {
		entityManager.add(e);
	}

	public void removeEntity(Entity e) {
		entityManager.destroy(e);
	}

	// Getters and Setters
	public Player getPlayer() {
		return this.player;
	}

	public void setPlayer(Player p) {
		this.player = p;
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

	public static EntityManager getEntityManager() {
		return entityManager;
	}
}
