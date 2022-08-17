package main.game.ui;

import java.util.HashMap;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import io.sly.helix.utils.math.Vector2D;
import main.GameData;
import main.constants.UIConstants;
import main.game.RpgGame;
import main.game.ui.components.Bar;

public class UI {

	public final HashMap<String, UIComponent> components;
	public final RpgGame game;
	
	public final GameData gameData;
	// 188 84 223
	public static final float RED = 188f;
	public static final float GREEN = 84;
	public static final float BLUE = 223;
	
	public UI(RpgGame game) {
		this.components = new HashMap<>();
		this.game = game;
		this.gameData = game.getGameData();
		
		Vector2D healthbarPos = new Vector2D(UIConstants.HEALTH_BAR_X, UIConstants.HEALTH_BAR_Y);
		this.addComponent("heatlh_bar", new HealthBar(gameData, this, healthbarPos));
	}
	
	public void addComponent(String name, UIComponent component) {
		this.components.put(name, component);
	}
	
	public UIComponent getComponent(String name) {
		return this.components.get(name);
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
