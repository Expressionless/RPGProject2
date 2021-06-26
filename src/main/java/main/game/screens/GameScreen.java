package main.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import helix.gfx.Screen;
import helix.utils.math.Point;
import main.GameData;
import main.game.RpgGame;
import main.game.entities.doodads.Tree;
import main.game.entities.mobs.enemies.mage.Mage;
import main.game.entities.mobs.neutral.Player;
import main.game.input.GameInput;
import main.game.item.ItemSpawner;
import main.game.ui.UI;
import main.game.world.World;

public final class GameScreen extends Screen {

	private World world;
	private UI ui;

	private Player player;
	private SpriteBatch batch;

	public GameScreen(RpgGame game) {
		super(game);
	}

	@Override
	protected void create() {
		
		this.world = new World(32, 32);
		world.setGame(this.getRpgGame());
		
		this.ui = new UI(this.getRpgGame());
		this.getGameData().setUI(this.ui);
		

		player = new Player(getRpgGame(), new Point(30, 30));
		GameData.INPUT_ADAPTERS.put("GAME", new GameInput(this.getGameData()));
		this.batch = new SpriteBatch();
		ItemSpawner is = new ItemSpawner(this.getRpgGame());
		is.spawnItem(50, 20, "grass", 5);
		new Tree(getRpgGame(), new Point(100, 80));

		new Mage(getRpgGame(), new Point(140, 40));
	}
	
	@Override
	protected void step(float delta) {
		this.handleInput();
		this.updateCamera();
	}

	@Override
	protected void draw(float delta) {
		this.getGameData().update(delta);
		this.getGameData().getCamera().update();

		this.batch.begin();
		this.batch.setProjectionMatrix(this.getGameData().getCamera().combined);
		this.getGameData().render(batch);
		this.batch.end();
	}

	private void updateCamera() {
		// Update Camera
		getData().getCamera().position.x = player.getPos().getX();
		getData().getCamera().position.y = player.getPos().getY();
	}

	private void handleInput() {
		Gdx.input.setInputProcessor(GameData.INPUT_ADAPTERS.get("GAME"));
	}

	// Getters and Setters
	public RpgGame getRpgGame() {
		return (RpgGame) getGame();
	}

	public GameData getGameData() {
		return (GameData) getGame().getData();
	}
}
