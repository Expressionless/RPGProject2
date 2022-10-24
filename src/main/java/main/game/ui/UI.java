package main.game.ui;

import java.util.HashMap;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import io.sly.helix.utils.math.Vector2D;
import main.GameData;
import main.constants.UIConstants;
import main.game.EntityManager;
import main.game.RpgGame;
import main.game.ui.components.Bar;

public class UI {

	public final HashMap<Class<? extends UIComponent>, UIComponent> components;
	public final RpgGame game;
	
	public final GameData gameData;
	// 188 84 223
	public static final float RED = 188f;
	public static final float GREEN = 84;
	public static final float BLUE = 223;
	
	private EntityManager entityManager;

	public UI(EntityManager entityManager) {
		this.components = new HashMap<>();
		this.entityManager = entityManager;
		this.gameData = entityManager.getGameData();
		this.game = entityManager.getGame();
		
		Vector2D healthbarPos = new Vector2D(UIConstants.HEALTH_BAR_X, UIConstants.HEALTH_BAR_Y);
		this.addComponent(HealthBar.class, new HealthBar(gameData, this, healthbarPos));
	}
	
	public void addComponent(Class<? extends UIComponent> clazz, UIComponent component) {
		this.components.put(clazz, component);
	}

	public EntityManager getEntityManager() {
		return this.entityManager;
	}
	
	public UIComponent getComponent(Class<? extends UIComponent> clazz) {
		return this.components.get(clazz);
	}
	
	private class HealthBar extends Bar {

		public HealthBar(GameData gameData, UI ui, Vector2D pos) {
			super(gameData, ui, pos, UIConstants.HEALTH_BAR_WIDTH, new Color(RED, GREEN, BLUE, 1.0f));
			this.setBarColor(new Color(1.0f, 0.0f, 0.0f, 1.0f));
		}
		
		@Override
		public void draw(SpriteBatch batch) {
			// Red healthbar code here
			super.render(batch);
		}

		@Override
		protected float updateValue() {
			float value = gameData.getPlayer().getStat("health") / gameData.getPlayer().getStat("maxHealth");
			return value;
		}	
	}
}
