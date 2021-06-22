package main.constants;

import helix.utils.math.Vector2;

public class UIConstants {

	public static final int UI_DEPTH = -1;
	
	public static Vector2 BAR_SCALE = new Vector2(0.65f, 0.65f);

	public static final int RAW_BAR_SPRITE_WIDTH = 8;
	public static final int RAW_BAR_SPRITE_HEIGHT = 12;
	
	public static final int ADJUSTED_BAR_SPRITE_WIDTH = (int)(RAW_BAR_SPRITE_WIDTH * BAR_SCALE.getX());
	public static final int ADJUSTED_BAR_SPRITE_HEIGHT = (int)(RAW_BAR_SPRITE_HEIGHT * BAR_SCALE.getY());
	
	// 12 Sprites long
	public static final int HEALTH_BAR_WIDTH = 12;
	
	//ApplicationConstants.CAMERA_WIDTH / 2 - BAR_SPRITE_WIDTH * HEALTH_BAR_WIDTH / 2
	public static final int HEALTH_BAR_X = 0;//ApplicationConstants.CAMERA_WIDTH / 2 - BAR_SPRITE_WIDTH * HEALTH_BAR_WIDTH / 2;
	// -(160)
	public static final int HEALTH_BAR_Y = -ApplicationConstants.CAMERA_HEIGHT + ADJUSTED_BAR_SPRITE_HEIGHT;

	public static final float BAR_PERCENT_CHANGE = 0.015f;
}
