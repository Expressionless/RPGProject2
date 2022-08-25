package main.game.screens;

import io.sly.helix.gfx.Screen;
import main.GameData;
import main.constants.SerializationConstants;
import main.game.RpgGame;
import main.game.item.ItemInfo;

public class LoadScreen extends Screen {
	
	public LoadScreen(RpgGame game) {
		super(game);
	}
	
	@Override
	public void show() {
		// this.parseItems();
		
		this.getData().setCurrentScreen(this);
	}

	@Override
	protected void step(float delta) {
		
	}

	@Override
	protected void draw(float delta) {
		
	}
	@Override
	protected void create() {
		// TODO Auto-generated method stub
		
	}

}
