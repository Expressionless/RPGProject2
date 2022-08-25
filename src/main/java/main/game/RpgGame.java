package main.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import io.sly.helix.game.BaseGame;
import io.sly.helix.gfx.SpriteSheet;
import main.GameData;
import main.constants.ApplicationConstants;
import main.constants.AssetConstants;
import main.constants.UIConstants;
import main.game.inventory.Slot;
import main.game.inventory.util.InventoryCursor;
import main.game.item.Item;
import main.game.screens.GameScreen;
import main.game.screens.LoadScreen;
import main.game.ui.components.Bar;

public class RpgGame extends BaseGame {

	private Viewport viewport;

	public RpgGame() {
		super(ApplicationConstants.TITLE, ApplicationConstants.FRAME_WIDTH, ApplicationConstants.FRAME_HEIGHT);

		config.setIdleFPS(ApplicationConstants.IDLE_FPS);
		config.setForegroundFPS(ApplicationConstants.TARGET_FPS);
		config.useVsync(true);
		config.setResizable(false);
		
		System.out.println(ApplicationConstants.FRAME_WIDTH + "x" + ApplicationConstants.FRAME_HEIGHT);
	}

	@Override
	public void init() {
		// Init Bar Sprite
		Bar.BAR_SPRITE = new SpriteSheet(this.getData(), Bar.BAR_DISP_SPRITE_REF, 8, 12);
		Bar.bar = this.getData().createSprite(Bar.BAR_SPRITE_REF);
		
		// Init inventory data
		Slot.SPRITE = this.getData().createSprite(Slot.SPRITE_REF);
		Slot.inventoryFont.getData().setScale(0.25f);
		
		// Init Item Data
		Item.ITEM_SHEET = new SpriteSheet(this.getData(), AssetConstants.ITEMS_DIRECTORY, 8, 8);
		
		
		Bar.left_disp = Bar.BAR_SPRITE.getSubSprite(0, 0);
		Bar.center_disp = Bar.BAR_SPRITE.getSubSprite(1, 0);
		Bar.right_disp = Bar.BAR_SPRITE.getSubSprite(2, 0);
				
		Bar.left_disp.setScale(UIConstants.BAR_SCALE);
		Bar.center_disp.setScale(UIConstants.BAR_SCALE);
		Bar.right_disp.setScale(UIConstants.BAR_SCALE);
		
		
		getGameData().setCursor(new InventoryCursor(this));
		addScreens();
	}

	protected void addScreens() {
		this.getData().addScreen(new LoadScreen(this));
		this.getData().addScreen(new GameScreen(this));
	}
	
	@Override
	protected void start() {

		((OrthographicCamera)this.getCurrentCamera()).setToOrtho(false, frameWidth, frameHeight);
		viewport = new ExtendViewport(ApplicationConstants.CAMERA_WIDTH, ApplicationConstants.CAMERA_HEIGHT, this.getCurrentCamera());
		viewport.apply();

		this.getData().setViewport(viewport);
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
	

}
