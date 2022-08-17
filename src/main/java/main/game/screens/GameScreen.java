package main.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import io.sly.helix.game.BaseGame;
import io.sly.helix.gfx.Screen;
import io.sly.helix.utils.math.Vector2D;
import main.GameData;
import main.game.RpgGame;
import main.game.entities.doodads.Tree;
import main.game.entities.mobs.enemies.mage.Mage;
import main.game.input.GameInput;
import main.game.ui.UI;
import main.game.world.World;

public final class GameScreen extends Screen {

	private World world;
	private UI ui;
	
	private SpriteBatch batch;

	public GameScreen(BaseGame game) {
		super(game);
	}

	@Override
	protected void create() {
		this.batch = new SpriteBatch();
		this.world = new World(this.getRpgGame(), 32, 32);
		this.ui = new UI(this.getRpgGame());
		this.getGameData().setUI(this.ui);
		this.startWorld();
	}
	
	private void startWorld() {
		world.spawnPlayer(new Vector2D(30, 30));
		
		world.itemSpawner.spawnItem(50, 20, "grass", 5);
		world.spawnEntity(Tree.class, new Vector2D(100, 80));

		world.spawnMob(Mage.class, new Vector2D(140, 40));

		initInputs();
	}
	
	private void initInputs() {

		GameData.INPUT_ADAPTERS.put("GAME", new GameInput(this.getGameData()));
	}
	
	@Override
	protected void step(float delta) {
		Gdx.input.setInputProcessor(GameData.INPUT_ADAPTERS.get("GAME"));
		world.step(delta);
	}

	@Override
	protected void draw(float delta) {
		this.getData().update(delta);
		this.getData().getCamera().update();

		this.batch.begin();
		this.batch.setProjectionMatrix(this.getGameData().getCamera().combined);
		this.getGameData().render(batch);
		this.batch.end();
	}

	// Getters and Setters
	public RpgGame getRpgGame() {
		return (RpgGame) getGame();
	}

	public GameData getGameData() {
		return (GameData) getGame().getData();
	}
}
