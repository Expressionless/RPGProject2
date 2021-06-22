package main.game.entities;

/**
 * Simple Stat handler to handle stats where T is the stat type
 * @author bmeachem
 *
 */
public abstract class AttribHandler<T> {

	public void setStat(String stat, T val) {
		try {			
			this.getClass().getDeclaredField(stat).set(this, val);
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public T getStat(String stat) {
		try {
			return (T) this.getClass().getDeclaredField(stat).get(this);
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		}

		return null;
	}
}
