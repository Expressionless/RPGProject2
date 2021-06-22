package main.game.ui.components;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

import helix.gfx.Sprite;
import helix.gfx.SpriteSheet;
import helix.utils.math.NumberUtils;
import helix.utils.math.Point;
import main.GameData;
import main.constants.ApplicationConstants;
import main.constants.UIConstants;
import main.game.annotations.QueueAsset;
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
	
	public Bar(GameData gameData, UI ui, Point pos, int barWidth) {
		super(ui, pos);

		this.gameData = gameData;
		this.barWidth = barWidth;
	}

	@Override
	public void step(float delta) {
	}

	@Override
	public void render(SpriteBatch batch) {
		// 188 84 223
		final float red = 188f;
		final float green = 84;
		final float blue = 223;
		this.renderBar(batch, new Color((float)(red/255f), (float)(green/255f), (float)(blue/255f), 1.0f));
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
		
		Point topLeft = new Point(x, y);
		
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
