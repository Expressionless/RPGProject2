package main.game.ui;

import helix.utils.math.Point;
import main.GameData;
import main.game.Entity;

/**
 * UIComponent class.
 * All UIComponents are updated by their {@link UI}
 * Contains a {@link Point} position
 * @author bmeachem
 *
 */
public abstract class UIComponent extends Entity {

	/**
	 * UI Managing the UIComponent
	 */
	public final UI ui;
	
	/**
	 * GameData object
	 * 
	 * @see {@link GameData}
	 */
	public final GameData data;
	
	/**
	 * Create a new UIComponent
	 * @param ui
	 * @param pos
	 */
	public UIComponent(UI ui, Point pos) {
		super(ui.game, pos);
		this.ui = ui;
		
		this.data = ui.gameData;
	}
}
