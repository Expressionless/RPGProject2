package main.constants;

import com.badlogic.gdx.graphics.Color;

public class ApplicationConstants {
	
	// Title of the game
	public static String TITLE = "RPG Game";

	// Frame Dimensions
	public static int FRAME_WIDTH = 1280;
	public static int FRAME_HEIGHT = 720;

	// Viewport dimensions
	public static int CAMERA_WIDTH = 320;
	public static int CAMERA_HEIGHT = 180;

	// > 1
	public static float RATIO_X = FRAME_WIDTH / CAMERA_WIDTH;
	public static float RATIO_Y = FRAME_HEIGHT / CAMERA_HEIGHT;

	public static boolean SAFE_RES = (RATIO_X == RATIO_Y);

	// FPS Constants
	public static final int IDLE_FPS = 45;
	public static final int TARGET_FPS = 120;

	// Background clear Color
	public static Color CLEAR_COLOR = new Color(0, 0.25f, 0, 1);

}
