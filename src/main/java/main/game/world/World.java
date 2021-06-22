package main.game.world;

import helix.game.Serializable;
import helix.utils.io.BinaryReader;
import helix.utils.io.BinaryWriter;
import main.constants.WorldConstants;
import main.game.RpgGame;

public final class World implements Serializable {

	private Chunk[][] chunks;
	
	private RpgGame game;
	
	public World(int width, int height) {
		chunks = new Chunk[height][width];
		
		initChunks();
	}
	
	private void initChunks() {
		int x, y;
		int xPos = WorldConstants.CHUNK_WIDTH, yPos = WorldConstants.CHUNK_HEIGHT;
		
		for(y = 0; y < chunks.length; y++) {
			for(x = 0; x < chunks[y].length; x++) {
				chunks[y][x] = new Chunk(game, xPos, yPos);
			}
		}
	}
	
	// Getters and Setters
	public Chunk getChunk(int x, int y) {
		return chunks[y][x];
	}
	
	public RpgGame getGame() {
		return this.game;
	}
	
	public void setGame(RpgGame game) {
		this.game = game;
	}

	@Override
	public boolean write(BinaryWriter writer, int pos) {
		return false;
	}

	@Override
	public boolean parse(BinaryReader reader, int pos) {
		return false;
	}
}
