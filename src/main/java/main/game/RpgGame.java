package main.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import helix.game.BaseGame;
import main.GameData;
import main.constants.ApplicationConstants;
import main.game.screens.GameScreen;
import main.game.screens.LoadScreen;

public class RpgGame extends BaseGame {

	private Viewport viewport;

	private FPSLogger fps;

	public RpgGame() {
		super(ApplicationConstants.TITLE, ApplicationConstants.FRAME_WIDTH, ApplicationConstants.FRAME_HEIGHT);

		config.setIdleFPS(ApplicationConstants.IDLE_FPS);
		config.setForegroundFPS(ApplicationConstants.TARGET_FPS);
		config.useVsync(true);
		config.setResizable(false);
		fps = new FPSLogger();
		this.setData(new GameData(this));
		
		System.out.println(ApplicationConstants.FRAME_WIDTH + "x" + ApplicationConstants.FRAME_HEIGHT);
	}

	@Override
	protected void addScreens() {
		this.getGameData().screens.add(new LoadScreen(this));
		this.getGameData().screens.add(new GameScreen(this));
	}
	
	@Override
	protected void start() {

		this.getCamera().setToOrtho(false, frameWidth, frameHeight);
		viewport = new ExtendViewport(ApplicationConstants.CAMERA_WIDTH, ApplicationConstants.CAMERA_HEIGHT, this.getCamera());
		viewport.apply();

		this.getGameData().setViewport(viewport);
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
	}

	@Override
	public void render() {
		ScreenUtils.clear(ApplicationConstants.CLEAR_COLOR);
		this.getScreen().render(Gdx.graphics.getDeltaTime());
		//fps.log();
	}

	// Getters and Setters
	
	public GameData getGameData() {
		return (GameData) this.getData();
	}
	
	public RpgGame getRpgGame() {
		return this;
	}
	
	public void setScreen(int num) {
		this.setScreen(this.getGameData().screens.get(num));
	}

}
