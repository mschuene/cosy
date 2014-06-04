
/**
 * Write a description of class Object here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class VisualObject {   
    /** Attribute */
    private Shape shape;
    private Color color;
    
    /**
     * Konstruktor eines Objectes.
     */
    public VisualObject(Shape shape, Color color) {
        this.shape = shape;
        this.color = color;
    }
    
    /**
     * Shape-Getter
     */
    public Shape getShape() {
        return shape;
    }
    
    /**
     * Color-Getter
     */
    public Color getColor() {
        return color;
    }
    
    public String toString() {
    	return "|"+this.color.toString()+" "+this.shape.toString()+"|";
    	
    }
}
