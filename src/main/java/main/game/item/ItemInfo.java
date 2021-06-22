package main.game.item;

import java.util.logging.Logger;

import helix.game.Serializable;
import helix.gfx.Sprite;
import helix.utils.io.BinaryReader;
import helix.utils.io.BinaryWriter;
import main.GameData;
import main.constants.Constants;
import main.constants.InventoryConstants;
import main.constants.SerializationConstants;

public final class ItemInfo implements Serializable {
	private static final Logger log = Logger.getLogger(ItemInfo.class.getCanonicalName());
	
	public String name;
	public ItemType type;
	public final Flags itemFlags;

	public int maxStack;
	public int ID;

	public Sprite sprite;

	/**
	 * For Serialization purposes
	 * @param id
	 * @param name
	 * @param maxStack
	 * @param flags
	 */
	public ItemInfo(int id, String type, String name, int maxStack, boolean... flags) {
		this.ID = id;
		this.type = ItemType.valueOf(type.toUpperCase());
		this.name = name;
		this.maxStack = maxStack;
		this.itemFlags = new Flags(flags);
	}
	
	public ItemInfo(int id, String name, int maxStack, int flags) {
		this.ID = id;
		this.name = name;
		this.maxStack = maxStack;
		this.itemFlags = new Flags(flags);
		
		attachSprite();
	}

	private ItemInfo(int id, String name, int flags) {
		this(id, name, InventoryConstants.MAX_STACK, flags);
	}

	private ItemInfo(int id, String name) {
		this(id, name, 0);
	}
	
	public ItemInfo() {
		itemFlags = new Flags();
	}
	
	private void attachSprite() {
		int index[] = Item.IDtoImageIndex(ID);
		this.sprite = Item.ITEM_SHEET.getSubSprite(index[0], index[1]);
		this.sprite.setName(name);
	}
	
	public static boolean isAllowedType(String type) {
		final ItemType[] allowedTypes = ItemType.values();
		for(ItemType t : allowedTypes)  {
			if(type == t.toString().toLowerCase())
				return true;
		}
		
		return false;
	}

	// Getters and Setters
	
	public Sprite getSprite() {
		return this.sprite.copy();
	}

	public boolean getFlag(String flag) {
		return this.itemFlags.getFlag(flag);
	}

	public void setFlag(String flag, boolean val) {
		this.itemFlags.setFlag(flag, val);
	}
	
	public ItemType getType() {
		return this.type;
	}

	@Override
	public String toString() {
		return "ItemType [ID=" + ID 
							+ ", name=" + name
							+ ", flags=[" + itemFlags.toString() + "]" 
							+ ", spr=" + ((sprite != null) ? sprite.getName() : name)
							+ "]";
	}

	// Helper class
	@SuppressWarnings("unused")
	private class Flags {

		private boolean stackable = true;
		private int maxStack = 50;

		public Flags() {

		}

		public Flags(boolean... flag) {
			stackable = flag[0];
		}
		
		public Flags(int... flags) {
			stackable = (flags[0] & 0x01) != 0;
		}

		public void setFlag(String flag, boolean val) {
			try {
				Flags.class.getDeclaredField(flag).set(this, val);
			} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
				e.printStackTrace();
			}
		}

		public boolean getFlag(String flag) {
			try {
				return Flags.class.getDeclaredField(flag).getBoolean(this);
			} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
				e.printStackTrace();
			}

			return false;
		}
		
		public void setFlags(int... flags) {
			stackable = (flags[0] & 0x01) != 0;
		}

		@Override
		public String toString() {
			return "Flags [stackable=" + stackable + "]";
		}
		
		public boolean[] getFlags() {
			return new boolean[] {stackable, false, false, false};
		}
	}

	
	// Static Fetch code
	/**
	 * Get the ID of an Item based on it's name (not case sensitive)
	 * 
	 * @param name - name of the item to find
	 * @return id of the item or -1 if no item has the specified name
	 */
	public static int idOf(final String name) {
		for (int i = 0; i < GameData.ITEM_TYPES.size(); i++) {
			if (GameData.ITEM_TYPES.get(i).name.toLowerCase().equals(name.toLowerCase()))
				return GameData.ITEM_TYPES.get(i).ID;
		}
		System.err.println("Could not get ID: " + name);
		return -1;
	}

	public static String nameOf(final int id) {
		try {
			return ItemInfo.get(id).name;
		} catch (NullPointerException e) {
			return "UNDEFINED";
		}
	}

	public static ItemInfo get(final int ID) {
		return GameData.ITEM_TYPES.get(ID);
	}

	// Serialization
	
	public boolean write(BinaryWriter writer) {
		return this.write(writer, ID);
	}
	
	@Override
	public boolean write(BinaryWriter writer, int position) {
		if(writer == null)
			return false;
		if(name.length() > SerializationConstants.MAX_ITEM_NAME_LEN)
			return false;
		writer.writeInts(ID);
		writer.write(type.name(), SerializationConstants.MAX_ITEM_TYPE_LEN);
		writer.write(name, SerializationConstants.MAX_ITEM_NAME_LEN);
		writer.writeInts(maxStack);
		writer.writeBools(itemFlags.getFlags());
		return true;
	}

	@Override
	public boolean parse(BinaryReader reader, int position) {
		if(reader == null)
			return false;
		position *= SerializationConstants.ITEM_SIZE;

		log.fine("Parsing new item at: " + position);
		// Read in the ID
		this.ID = reader.getInt(position + SerializationConstants.ID_POS);
		log.fine("ID: "  + this.ID);
		
		// Read in the type
		this.type = ItemType.valueOf(reader.getString(position + SerializationConstants.TYPE_POS, SerializationConstants.MAX_ITEM_TYPE_LEN));
		log.fine("TYPE: "  + this.type);
		// Read in the name
		this.name = reader.getString(position + SerializationConstants.NAME_POS, SerializationConstants.MAX_ITEM_NAME_LEN);
		log.fine("Name: " + this.name);
		// Read in maxStack
		this.maxStack = reader.getInt(position + SerializationConstants.STACK_POS);
		log.fine("Stack: " + this.maxStack);
		// Read in the flags
		int flags = reader.getInt(position + SerializationConstants.FLAG_POS);
		itemFlags.setFlags(flags);
		log.fine("Flags: " + flags);
		if(com.badlogic.gdx.Gdx.files != null)
			this.attachSprite();
		return true;
	}

}
