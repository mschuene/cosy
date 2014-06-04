import java.util.Random;
public class ColorGenerator {

	Color[] allowedColors;
	public ColorGenerator() {
		this.allowedColors = new Color[] {Color.BLACK,Color.BRAUN,Color.DARK_BLUE,Color.DARK_GREEN,Color.LIGHT_BLUE,
				                          Color.LIGHT_GREEN,Color.ORANGE,Color.PINK,Color.PURPLE,Color.RED,Color.WHITE,
				                          Color.YELLOW};
	}
	
	public ColorGenerator(Color[] allowedColors) {
		this.allowedColors = allowedColors;
	}
	
	public Color generate() {
		int count = allowedColors.length;
		return allowedColors[(new Random()).nextInt(count)];
	}
}
