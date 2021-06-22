package main.constants;

import com.badlogic.gdx.Input.Keys;

public class PlayerConstants {
	// Player Constants
	public static final int DOWN  = 0x01;
	public static final int LEFT  = 0x02;
	public static final int RIGHT = 0x04;
	public static final int UP    = 0x08;
	
	// Movement and Input
	public static final char KEY_RIGHT = Keys.D;
	public static final char KEY_LEFT = Keys.A;
	public static final char KEY_DOWN = Keys.S;
	public static final char KEY_UP = Keys.W;

	public static final char KEY_INV = Keys.P;
	public static final char KEY_CHARACTER = Keys.C;
	
	public static final char KEY_SHIFT = Keys.SHIFT_LEFT;
	public static final char KEY_SHIFT_R = Keys.SHIFT_RIGHT;
	public static final char KEY_0 = Keys.NUM_0;
	public static final char KEY_9 = Keys.NUM_9;

	public static final float PLAYER_SPEED = 60f;
	
	// Inventory Size Constants
	public static final int P_INV_WIDTH = 8;
	public static final int P_INV_HEIGHT = 6;

	// Pickup Range (px)
	public static final float PICKUP_DISTANCE = 8;
	

}
