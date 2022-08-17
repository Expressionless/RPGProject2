package main.game.ui.components;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

import io.sly.helix.annotations.QueueAsset;
import io.sly.helix.gfx.Sprite;
import io.sly.helix.gfx.SpriteSheet;
import io.sly.helix.utils.math.NumberUtils;
import io.sly.helix.utils.math.Vector2D;
import main.GameData;
import main.constants.ApplicationConstants;
import main.constants.UIConstants;
import main.game.ui.UI;
import main.game.ui.UIComponent;

public abstract class Bar extends UIComponent {
	@QueueAsset(ref="res/sprites/UI/bar/bar_display.png", type=Texture.class)
	public static String BAR_DISP_SPRITE_REF;
	
	@QueueAsset(ref="res/sprites/UI/bar/bar.png", type=Texture.class)
	public static String BAR_SPRITE_REF;
	
	public static SpriteSheet BAR_SPRITE;
	public static Sprite left_disp, center_disp, right_disp,
						bar;
	
	public final GameData gameData;

	private int barWidth;
	private Color barColor;

	private float targetVal = 0f, currentVal = 0f;
	
	/**
	 * Returns a percentage of value and maxvalue. Cannot exceed 1
	 * @return
	 */
	protected abstract float updateValue();
	
	public Bar(GameData gameData, UI ui, Vector2D pos, int barWidth, Color barColor) {
		super(ui, pos);

		this.gameData = gameData;
		this.barWidth = barWidth;
		this.barColor = barColor;
	}

	@Override
	public void step(float delta) {
	}

	@Override
	public void render(SpriteBatch batch) {
		this.renderBar(batch, barColor);
	}
	
	private float getSmoothedValue() {
		this.targetVal = updateValue();
		if(this.currentVal != this.targetVal)
			this.currentVal = NumberUtils.lerp(currentVal, targetVal, UIConstants.BAR_PERCENT_CHANGE);
		return NumberUtils.clamp(this.currentVal, 0, 1).floatValue();
	}
	
	private void renderBar(SpriteBatch batch, Color col) {
		Vector3 camPos = this.data.getCamera().position;
		
		float x = this.getPos().getX() + camPos.x - ApplicationConstants.CAMERA_WIDTH / 2;
		float y = this.getPos().getY() + camPos.y + ApplicationConstants.CAMERA_HEIGHT / 2 - center_disp.getHeight();
		
		Vector2D topLeft = new Vector2D(x, y);
		
		float value = this.getSmoothedValue() * UIConstants.HEALTH_BAR_WIDTH * UIConstants.ADJUSTED_BAR_SPRITE_WIDTH;
		
		bar.setScale(value, UIConstants.BAR_SCALE.getY());
		bar.draw(batch, x, y + 2, barColor);
		
		//left_bar.draw(batch, topLeft.getX(), topLeft.getY(), col);
		int i;
		for(i = 0; i < barWidth - 1; i++)
			center_disp.draw(batch, topLeft.getX() + center_disp.getWidth() * (i), topLeft.getY(), col);
		right_disp.draw(batch, topLeft.getX() + center_disp.getWidth() * (i), topLeft.getY(), col);
	}

	// Getters and Setters
	public Color getBarColor() {
		return barColor;
	}

	public void setBarColor(Color barColor) {
		this.barColor = barColor;
	}
}
