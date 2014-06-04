import java.util.Random;
public class ShapeGenerator {

	Shape[] allowedShapes;
	public ShapeGenerator() {
		this.allowedShapes = new Shape[] {Shape.CIRCLE,Shape.HEXAGON,Shape.SQUARE};
	}
	
	public ShapeGenerator(Shape[] allowedShapes) {
		this.allowedShapes = allowedShapes;
	}
	
	public Shape generate() {
		int count = allowedShapes.length;
		return allowedShapes[(new Random()).nextInt(count)];
	}
}

