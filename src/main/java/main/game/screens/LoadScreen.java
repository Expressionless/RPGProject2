package main.game.screens;

import org.jboss.logging.Logger;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import io.sly.helix.gfx.Screen;
import io.sly.helix.gfx.Sprite;
import io.sly.helix.gfx.SpriteSheet;
import main.GameData;
import main.constants.ApplicationConstants;
import main.constants.AssetConstants;
import main.constants.SerializationConstants;
import main.game.RpgGame;
import main.game.item.Item;
import main.game.item.ItemInfo;

public class LoadScreen extends Screen {
	private static final Logger log = Logger.getLogger(GameScreen.class);

	private Sprite loadScreenSprite;
	
	public LoadScreen(RpgGame game) {
		super(game, 0);
		
		this.queueLoadAssets();
		this.getData().getManager().finishLoading();

		loadScreenSprite = this.getData().createSprite(ApplicationConstants.LOADSCREEN_PATH);

		// Init Item Data
		Item.ITEM_SHEET = new SpriteSheet(this.getData(), AssetConstants.ITEMS_DIRECTORY, 8, 8);
		this.parseItems();
	}
	
	private void queueLoadAssets() {
		this.getData().getManager().load(AssetConstants.ITEMS_DIRECTORY, Texture.class);
		this.getData().getManager().load(ApplicationConstants.LOADSCREEN_PATH, Texture.class);
	}

	private void parseItems() {
		this.getData().beginReading("/data/item");
		
		int itemsToParse = this.getData().getReader().getBytes().size() / SerializationConstants.ITEM_SIZE;
		for(int i = 0; i < itemsToParse; i++) {
			ItemInfo item = new ItemInfo();
			item.parse(this.getData().getReader(), i);
			System.out.println(item.toString());
			GameData.ITEM_TYPES.add(item);
		}
		
		this.getData().stopReading();

		System.out.println("Loaded: " + GameData.ITEM_TYPES.size() + " items");
		// this.getData().setCurrentScreen(1);
	}

	@Override
	protected void step(float delta) {

		boolean finishedLoading = this.getData().loadNextAsset();
		
		if(finishedLoading) {
			((RpgGame)this.getGame()).postLoad();
			this.getData().goToNextScreen();
		}
	}

	@Override
	protected void draw(float delta) {
		SpriteBatch batch = new SpriteBatch();
		batch.begin();
		loadScreenSprite.draw(batch, 0, 0);
		batch.end();
	}
	@Override
	protected void create() {
		// TODO Auto-generated method stub
		
	}

}
