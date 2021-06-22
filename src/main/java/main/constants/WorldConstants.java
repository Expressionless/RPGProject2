package main.constants;

public class WorldConstants {
	// World constants
	public static final float TILE_WIDTH  = 8;
	public static final float TILE_HEIGHT = 8;

	public static final int CHUNK_WIDTH  = 10;
	public static final int CHUNK_HEIGHT = 10;
	
	public static final float CHUNK_WIDTH_PX = CHUNK_WIDTH * TILE_WIDTH;
	public static final float CHUNK_HEIGHT_PX = CHUNK_HEIGHT * TILE_HEIGHT;

	// Entity limits
	public static final int MAX_ENTITIES_PER_TILE = 4;
	public static final int MAX_ENTITIES_PER_CHUNK = MAX_ENTITIES_PER_TILE * CHUNK_WIDTH * CHUNK_HEIGHT;
}