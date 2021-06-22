package main.constants;

public class SerializationConstants {

	// ITEM INFO CONSTANTS
	// Size in bits (1 byte)
	public static final int INT_SIZE = 4;
	public static final int NO_ITEM = -1;
	public static final int MAX_ITEM_NAME_LEN = 10;
	public static final int MAX_ITEM_TYPE_LEN = 10;

	// Item Data positions
	public static final int ID_POS = 0;
	public static final int TYPE_POS = ID_POS + 1;
	public static final int NAME_POS = TYPE_POS + MAX_ITEM_TYPE_LEN;
	public static final int STACK_POS = NAME_POS + MAX_ITEM_NAME_LEN;
	public static final int FLAG_POS = STACK_POS + 1;
	public static final int ITEM_SIZE = FLAG_POS + 1;

}
