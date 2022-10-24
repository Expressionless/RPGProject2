package main.game.entities.doodads;

import com.badlogic.gdx.graphics.Texture;

import io.sly.helix.annotations.QueueAsset;
import io.sly.helix.utils.math.Vector2D;
import main.constants.Constants;
import main.game.EntityManager;

import main.game.entities.Doodad;

public class Tree extends Doodad {
	@QueueAsset(type=Texture.class, ref="res/sprites/doodads/tree.png")
	public static String TREE_REF;;
	
	private float growtimer;
	
	public Tree(EntityManager em, Vector2D pos) {
		super(em, pos);
		this.addSprite(TREE_REF);
		this.setSprite(TREE_REF);
		this.growtimer = Constants.TREE_GROWTH_CYCLE;
		this.getSprite().setScale(1, 0.5f);
		this.getAlarm(0).setAlarm((int)growtimer, () -> {});
		System.out.println("New Tree");
	}

	@Override
	protected void step(float delta) {
		float growth = 1.0f - (float)(this.getAlarm(0).getTimer()) / growtimer * 0.5f;
		this.getSprite().setScale(1, growth);
	}

}
