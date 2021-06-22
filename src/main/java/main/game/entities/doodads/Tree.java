package main.game.entities.doodads;

import com.badlogic.gdx.graphics.Texture;

import helix.utils.math.Point;
import main.constants.Constants;
import main.game.RpgGame;
import main.game.annotations.QueueAsset;
import main.game.entities.Doodad;

public class Tree extends Doodad {
	@QueueAsset(type=Texture.class, ref="res/sprites/doodads/tree.png")
	public static String TREE_REF;;
	
	private float growtimer;
	
	public Tree(RpgGame game, Point pos) {
		super(game, pos);
		this.addSprite(TREE_REF);
		this.setSprite(TREE_REF);
		this.growtimer = Constants.TREE_GROWTH_CYCLE;
		this.getSprite().setScale(1, 0.5f);
		this.getAlarm(0).setAlarm((int)growtimer, () -> {});
	}

	@Override
	public void step(float delta) {
		this.getSprite().setScale(1, 1.0f - (float)(this.getAlarm(0).getTimer()) / growtimer * 0.5f);
	}

}
