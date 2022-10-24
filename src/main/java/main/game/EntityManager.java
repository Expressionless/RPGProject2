package main.game;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import main.GameData;
import main.game.entities.mobs.neutral.Player;
import main.game.world.World;

public class EntityManager {
    
    private List<Entity> addBuffer = new ArrayList<>();
    private List<Entity> removeBuffer = new ArrayList<>();
	public List<Entity> entities = new ArrayList<>();

    private World world;
    private Player player;

    public EntityManager(World world) {
        this.world = world;
    }

    public void render(SpriteBatch sb) {
		for(Entity entity : entities) {
            if(entity.isActive())
                entity.render(sb);
        }
    }

    public void update(float delta) {
        addEntities();
        updateEntities(delta);
        removeEntities();
    }

    public void add(Entity e) {
        addBuffer.add(e);
    }

    public void remove(Entity e) {
        this.destroy(e);
    }

    public void destroy(Entity e) {
        e.setActive(false);
        e.dispose();
        removeBuffer.add(e);
    }

    private void addEntities() {
        while(!addBuffer.isEmpty()) {
            Entity e = addBuffer.get(0);
            e.setActive(true);
            entities.add(e);
            addBuffer.remove(0);
        }
    }

    private void removeEntities() {
        while(!removeBuffer.isEmpty()) {
            entities.remove(removeBuffer.get(0));
            removeBuffer.remove(0);
        }
    }

    private void updateEntities(float delta) {
		for(Entity entity : entities) {
            if(entity.isActive())
                entity.update(delta);
        }
    }

    public World getWorld() {
        return this.world;
    }

    public RpgGame getGame() {
        return this.world.getGame();
    }

    public Player getPlayer() {
        return this.player;
    }
    
    public GameData getGameData() {
        return this.getGame().getGameData();
    }
}
