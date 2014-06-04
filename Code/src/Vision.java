/**
 * Write a description of class Vision here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Vision {
    private static VisualObject[][] example = new VisualObject[][] {
        { new VisualObject(Shape.CIRCLE,Color.RED), null, null, null, null, null,
        	new VisualObject(Shape.CIRCLE,Color.RED), null, null },

        { null, null, new VisualObject(Shape.CIRCLE,Color.RED), null, null,
        	null, null, new VisualObject(Shape.SQUARE,Color.YELLOW), null},

        { null, new VisualObject(Shape.CIRCLE,Color.BLACK),	null, null,	
        	new VisualObject(Shape.CIRCLE,Color.BLACK), null, null, null, null},

        { null, null, null, null, new VisualObject(Shape.CIRCLE,Color.BLACK), null,
        	new VisualObject(Shape.SQUARE,Color.LIGHT_BLUE), null, null},

        { new VisualObject(Shape.SQUARE,Color.YELLOW), null, null,
            new VisualObject(Shape.HEXAGON,Color.RED),
            new VisualObject(Shape.CIRCLE,Color.BLACK), null, null, null, null},

        { null, new VisualObject(Shape.SQUARE,Color.LIGHT_BLUE), null, null, 
        	null, null, null, null, null},

        { new VisualObject(Shape.CIRCLE,Color.LIGHT_BLUE),
            new VisualObject(Shape.CIRCLE,Color.LIGHT_BLUE), null, null, null,
            new VisualObject(Shape.CIRCLE,Color.LIGHT_BLUE),
            new VisualObject(Shape.CIRCLE,Color.RED), null, null},

        { new VisualObject(Shape.CIRCLE,Color.LIGHT_BLUE),
            new VisualObject(Shape.CIRCLE,Color.LIGHT_BLUE), null,
            new VisualObject(Shape.SQUARE,Color.YELLOW), null,
            new VisualObject(Shape.CIRCLE,Color.RED),
            new VisualObject(Shape.CIRCLE,Color.RED),
            new VisualObject(Shape.HEXAGON,Color.RED), null},
            
        { null, null, null, null, null, null, new VisualObject(Shape.HEXAGON,Color.RED), null, null},
        
    };
    
    public static VisualObject[][] getInput() {
        return example;
    }
}
