package main.game.entities.mobs.neutral;

import static main.constants.PlayerConstants.DOWN;
import static main.constants.PlayerConstants.LEFT;
import static main.constants.PlayerConstants.RIGHT;
import static main.constants.PlayerConstants.UP;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import helix.utils.io.BinaryReader;
import helix.utils.io.BinaryWriter;
import helix.utils.math.Angle;
import helix.utils.math.Point;
import main.constants.ApplicationConstants;
import main.constants.InventoryConstants;
import main.constants.PlayerConstants;
import main.game.RpgGame;
import main.game.annotations.QueueAsset;
import main.game.entities.Mob;
import main.game.inventory.Inventory;
import main.game.inventory.Slot;
import main.game.inventory.subtypes.ArmourInventory;
import main.game.inventory.subtypes.GenericInventory;
import main.game.inventory.subtypes.HotbarInventory;

public class Player extends Mob {
	@QueueAsset(ref = "res/sprites/mob/player/right.png", type = Texture.class)
	public static String PLAYER_RIGHT;

	@QueueAsset(ref = "res/sprites/mob/player/down.png", type = Texture.class)
	public static String PLAYER_DOWN;

	@QueueAsset(ref = "res/sprites/mob/player/up.png", type = Texture.class)
	public static String PLAYER_UP;

	// Inventories
	private Inventory hotbar;
	private Inventory equipped;
	private Inventory armour;

	private int anim_duration = 750;
	private int movement = 0x00;

	public Player(RpgGame game, Point pos) {
		super(game, pos);
		float x = 40 - ApplicationConstants.CAMERA_WIDTH / 4;
		float y = 30 - ApplicationConstants.CAMERA_HEIGHT * .6f
				+ Slot.SPRITE.getHeight() * (PlayerConstants.P_INV_HEIGHT + 1);
		GenericInventory newInv = new GenericInventory(game, new Point(x, y), PlayerConstants.P_INV_WIDTH,
				PlayerConstants.P_INV_HEIGHT);
		this.setInventory(newInv);

		this.addSprite(PLAYER_RIGHT, 4, anim_duration);
		this.addSprite(PLAYER_DOWN, 4, anim_duration);
		this.addSprite(PLAYER_UP, 4, anim_duration);

		this.hotbar = new HotbarInventory(game,
				new Point(40 - ApplicationConstants.CAMERA_WIDTH / 4, 30 - ApplicationConstants.CAMERA_HEIGHT * .6f));
		this.hotbar.setVisible(true);

		Point armourPos = this.getInventory().getPos().copy();
		armourPos.setX(armourPos.getX() - Slot.SPRITE.getWidth() - InventoryConstants.INVENTORY_MARGIN);
		armourPos.setY(armourPos.getY() - Slot.SPRITE.getHeight());
		this.armour = new ArmourInventory(game, armourPos);

		Point equipPos = this.getHotbar().getPos().copy();
		equipPos.setX(equipPos.getX() - Slot.SPRITE.getWidth() * 2.5f - InventoryConstants.INVENTORY_MARGIN);
		equipPos.setY(equipPos.getY());

		this.equipped = new GenericInventory(game, equipPos, 2, 1);
		this.equipped.clearAllowedTypes();
		this.equipped.addAllowedTypes("WEAPON", "TOOL");
		this.equipped.setVisible(true);

		this.setStat("speed", PlayerConstants.PLAYER_SPEED);
		this.setStat("maxHealth", 150);

		this.updateCollider();
		game.getGameData().setPlayer(this);
	}

	@Override
	protected void preStep(float delta) {
		super.preStep(delta);
		this.hotbar.update(delta);
		this.equipped.update(delta);
		this.armour.update(delta);
	}

	@Override
	public void step(float delta) {
		// Update collider
		// Manage input
		this.handleMovement(delta);
	}

	@Override
	public void draw(SpriteBatch batch) {

		this.getInventory().render(batch);
		this.hotbar.render(batch);
		this.armour.render(batch);
		this.equipped.render(batch);
	}

	private void handleMovement(float delta) {

		// Update Direction
		int xVal = this.getMovement(RIGHT) - this.getMovement(LEFT);
		int yVal = this.getMovement(UP) - this.getMovement(DOWN);

		this.getDirection().setX(xVal);
		this.getDirection().setY(yVal);

		if (this.getDirection().length() != 0)
			this.move(this.getStat("speed") * delta);
	}

	protected void updateSprite() {
		double angle = this.getDirection().getAngle();

		boolean up = angle < Angle.TOP_LEFT.angle && angle > Angle.TOP_RIGHT.angle;
		boolean left = angle <= Angle.BOTTOM_LEFT.angle && angle >= Angle.TOP_LEFT.angle;
		boolean right = (angle >= 0 && angle <= Angle.TOP_RIGHT.angle)
				|| (angle < 0 && angle >= Angle.BOTTOM_RIGHT.angle);
		boolean down = angle < Angle.BOTTOM_RIGHT.angle && angle > Angle.BOTTOM_LEFT.angle;

		if (up) {
			setSprite(PLAYER_UP);
			getSprite().flip(false);
		} else if (right) {
			setSprite(PLAYER_RIGHT);
			getSprite().flip(false);
		} else if (down) {
			setSprite(PLAYER_DOWN);
			getSprite().flip(false);
		} else if (left) {
			setSprite(PLAYER_RIGHT);
			getSprite().flip(true);
		}
		this.getSprite().start();
	}

	private void updateCollider() {
		this.getCollider().setWidth(6);
		this.getCollider().setHeight(13);
		this.getCollider().setXOffset(5);
		this.getCollider().setYOffset(2);
	}

	// Getters and Setters
	public HotbarInventory getHotbar() {
		return (HotbarInventory) this.hotbar;
	}

	public ArmourInventory getArmour() {
		return (ArmourInventory) this.armour;
	}

	public Inventory getHands() {
		return this.equipped;
	}

	public int getMovement(int bit) {

		return ((movement & bit) > 0) ? 1 : 0;
	}

	public void setMovement(int bit, boolean val) {
		if (val)
			movement |= bit;
		else {
			bit = 0xF - bit;
			movement &= bit;
		}
	}

	// TODO: Implement Serialization of player object
	/*
	 * Save the position
	 */
	@Override
	public boolean write(BinaryWriter writer, int pos) {
		return false;
	}

	@Override
	public boolean parse(BinaryReader reader, int pos) {
		return false;
	}
}
