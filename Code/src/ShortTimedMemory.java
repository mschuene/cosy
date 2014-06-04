
public class ShortTimedMemory {
	
	private static Color color;
	private static Shape shape;
	private static int amount;
	
	private ShortTimedMemory() {
	}
	
	/**
	 * 
	 * @param p_color
	 * @param p_shape
	 * @param p_amount
	 */
	public static void memorize(Color p_color, Shape p_shape, int p_amount) {
		color = p_color;
		shape = p_shape;
		amount = p_amount;
	}
	/**
	 * 
	 * @param p_color
	 * @param p_shape
	 * @return Returns -1 if cannot remember. Returns the amount
	 * 			of specified Objects on success.
	 */
	public static int remember(Color p_color, Shape p_shape) {
		if(p_color.equals(color) && p_shape.equals(shape)) {
			return amount;
		} else {
			return -1;
		}
	}
 }
