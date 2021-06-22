package main.constants;

import main.game.annotations.QueueAsset;

public class AssetConstants {
	// Items resource path
	@QueueAsset(ref="res/sprites/items.png")
	public static String ITEMS_DIRECTORY;
	
	// Basic Collision (px)
	public static final float ITEM_SUCK_DISTANCE = 12;
	// Animation Constants
	public static final double ITEM_BREATHE_LENGTH = 50;

	public static final float ITEM_SPEED = 15f;
	

}
