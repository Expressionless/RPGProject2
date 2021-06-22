package main.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import helix.gfx.Screen;
import helix.utils.math.Point;
import main.GameData;
import main.constants.PlayerConstants;
import main.game.RpgGame;
import main.game.entities.doodads.Tree;
import main.game.entities.mobs.enemies.mage.Mage;
import main.game.entities.mobs.neutral.Player;
import main.game.inventory.Inventory;
import main.game.inventory.Slot;
import main.game.inventory.subtypes.HotbarInventory;
import main.game.inventory.util.InventoryCursor;
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
	public void show() {
		this.world = new World(32, 32);
		world.setGame(this.getRpgGame());
		
		this.ui = new UI(this.getRpgGame());
		this.getGameData().setUI(this.ui);
		

		player = new Player(getRpgGame(), new Point(30, 30));
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
		Gdx.input.setInputProcessor(new InputAdapter() {
			private boolean handleInventory(InventoryCursor cursor, Inventory inventory) {
				for (Slot[] slots : inventory.getSlots()) {
					for (Slot slot : slots) {
						if (slot.isCursorOver()) {
							if (cursor.hasNothing() && !slot.isEmpty()) {
								cursor.take(slot);
								
								if(cursor.getQuickShift()) {
									if(slot.getInventory().id == player.getInventory().id) {
										Slot otherSlot = player.getHotbar().getFirstFree();
										cursor.place(otherSlot);
									}
								}
							} else if (!cursor.hasNothing()) {
								if (slot.isEmpty())
									cursor.place(slot);
								else {
									cursor.swap(slot);
								}
							}

							// Interaction is done
							return true;
						}
					}
				}
				return false;
			}

			public boolean scrolled(float amountX, float amountY) {
				HotbarInventory inv = player.getHotbar();
				inv.getSelector().setCurrentSlot((int)amountY, true);
				return true;
			}
			
			@Override
			public boolean keyDown(int keycode) {
				HotbarInventory inv = player.getHotbar();
				int pos;
				if(keycode >= PlayerConstants.KEY_0 && keycode <= (PlayerConstants.KEY_0 + player.getHotbar().getWidth())) {
					if(keycode != Keys.NUM_0) {
						pos = keycode - Keys.NUM_1;
					} else {
						pos = Keys.NUM_9 - PlayerConstants.KEY_0;
					}
					inv.getSelector().setCurrentSlot(pos, false);
				}
				switch (keycode) {
				case PlayerConstants.KEY_INV:
					if (player.getInventory().isVisible()) {
						player.getInventory().setVisible(false);
					} else {
						player.getInventory().setVisible(true);
					}
					break;
				case PlayerConstants.KEY_DOWN:
					player.setMovement(PlayerConstants.DOWN, true);
					break;
				case PlayerConstants.KEY_RIGHT:
					player.setMovement(PlayerConstants.RIGHT, true);
					break;
				case PlayerConstants.KEY_LEFT:
					player.setMovement(PlayerConstants.LEFT, true);
					break;
				case PlayerConstants.KEY_UP:
					player.setMovement(PlayerConstants.UP, true);
					break;
				
				case PlayerConstants.KEY_CHARACTER:
					if(player.getArmour().isVisible())
						player.getArmour().setVisible(false);
					else
						player.getArmour().setVisible(true);
					break;
				}
				return true;
			}

			@Override
			public boolean keyUp(int keycode) {
				switch (keycode) {
				case PlayerConstants.KEY_DOWN:
					player.setMovement(PlayerConstants.DOWN, false);
					break;
				case PlayerConstants.KEY_RIGHT:
					player.setMovement(PlayerConstants.RIGHT, false);
					break;
				case PlayerConstants.KEY_LEFT:
					player.setMovement(PlayerConstants.LEFT, false);
					break;
				case PlayerConstants.KEY_UP:
					player.setMovement(PlayerConstants.UP, false);
					break;
				}
				return true;
			}

			@Override
			public boolean mouseMoved(int screenX, int screenY) {
				Point p = getGameData().toGameCoords(screenX, screenY);
				getGameData().getCursor().getPos().setX(p.getX());// + screenX / Constants.RATIO_X);
				getGameData().getCursor().getPos().setY(p.getY());// - screenY / Constants.RATIO_Y);
				return true;
			}

			@Override
			public boolean touchDown(int screenX, int screenY, int pointer, int button) {
				InventoryCursor cursor = getGameData().getCursor();
				// TODO: Set inventory bounds and do a bounds check on that
				if (player.getInventory().isVisible()) {
					if(handleInventory(cursor, player.getInventory()))
						return true;

					if(player.getHotbar().isVisible()) {
						if(handleInventory(cursor, player.getHotbar()))
							return true;
					}
					
					if(player.getArmour().isVisible()) {
						if(handleInventory(cursor, player.getArmour()))
							return true;
					}

					if(player.getHands().isVisible()) {
						if(handleInventory(cursor, player.getHands()))
							return true;
					}
				}
				
				// if not over any slots place item on ground
				boolean onGround = !player.getInventory().getBounds().contains(cursor.getPos());
				System.out.println(player.getInventory().getBounds().toString());
				System.out.println("Mouse: " + cursor.getPos());
				if(onGround || !player.getInventory().isVisible())
					if (cursor.getItem() != null) {
						ItemSpawner is = new ItemSpawner(getRpgGame());
						is.spawnItem(cursor.getPos().copy(), cursor.getItem().ID, cursor.getAmount());
	
						cursor.setItem(null);
						return true;
					}

				// Interaction is done
				return true;
			}
		});
	}

	// Getters and Setters
	public RpgGame getRpgGame() {
		return (RpgGame) getGame();
	}

	public GameData getGameData() {
		return (GameData) getGame().getData();
	}
}
