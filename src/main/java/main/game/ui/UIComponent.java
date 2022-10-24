package main.game.ui;

import io.sly.helix.utils.math.Vector2D;
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
	 * Create a new UIComponent
	 * @param ui
	 * @param pos
	 */
	public UIComponent(UI ui, Vector2D pos) {
		super(ui.getEntityManager(), pos);
		this.ui = ui;
	}
}
