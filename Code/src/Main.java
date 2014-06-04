import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


/**
 * Write a description of class Main here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Main {
	
	private static boolean on = true;
    
    /**
     * Main-Method
     */
    public static void main(String[] args) throws InterruptedException, IOException {
        ImageViewer viewer = new ImageViewer();
        viewer.setLocationRelativeTo(null);
        Thinkpad.setViewer(viewer);
        
        while(on) {
        	BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        	String line = reader.readLine();
        	Shape[] shapes = findShapes(line.split(" "));
            Color[] colors = findColors(line.split(" "));
	        if(shapes.length == 2 && colors.length == 2) {
	        	int re1 = ShortTimedMemory.remember(colors[0], shapes[0]);
	        	int re2 = ShortTimedMemory.remember(colors[1], shapes[1]);
	        	
	        	if(re1 > -1) {
	        		int i = Thinkpad.focusView(Thinkpad.peripheralView(Vision.getInput(), colors[1]), shapes[1]);
	        		ShortTimedMemory.memorize(colors[1], shapes[1], i);
	        		
	        		System.out.println("I know of " + re1 + " " + colors[0].toString().toLowerCase()
	                        + " " + shapes[0].toString().toLowerCase() + "s and counted " + i + " "
	                        + colors[1].toString().toLowerCase() + " " + shapes[1].toString().toLowerCase() + "s"
	                        + " altogther " + (re1 + i) + " Objects.");
	        		
	        	} else if (re2 > -1) {
	        		int i = Thinkpad.focusView(Thinkpad.peripheralView(Vision.getInput(), colors[0]), shapes[0]);
	        		ShortTimedMemory.memorize(colors[0], shapes[0], i);
	        		
	        		System.out.println("I know of " + re2 + " " + colors[1].toString().toLowerCase()
	                        + " " + shapes[1].toString().toLowerCase() + "s and counted " + i + " "
	                        + colors[0].toString().toLowerCase() + " " + shapes[0].toString().toLowerCase() + "s"
	                        + " altogther " + (re2 + i) + " Objects.");
	        		
	        	} else {
	        		System.out.println("Number unknown.");
	        	}
	        	
	        } else if (shapes.length == 1 && colors.length == 1) {
	        	int i = Thinkpad.focusView(Thinkpad.peripheralView(Vision.getInput(), colors[0]), shapes[0]);
	        	ShortTimedMemory.memorize(colors[0], shapes[0], i);
	        	
	        	System.out.println("I counted " + i + " " + colors[0].toString().toLowerCase()
	                    + " " + shapes[0].toString().toLowerCase() + "s.");
	        } else {
	            System.out.println("I didn't get that.\nShapes are : Circle, Hexagon, Square\n"
	                + "Colors are: White, Yellow, Red, Light_Blue, Dark_Blue, Light_Green"
	                + "Dark_Green, Purple, Pink, Braun, Black");
	        }
        }
    }
    
    /**
     * 
     */
    public static Shape[] findShapes(String[] args) {
        ArrayList<Shape> shapes = new ArrayList<Shape>(2);
        for(String next : args) {
            try {
            	if(next.charAt(next.length()-1) == 's') {
            		shapes.add(Shape.valueOf(next.substring(0, next.length()-1).toUpperCase()));
            	} else {
            		shapes.add(Shape.valueOf(next.toUpperCase()));
            	}
                
            } catch(IllegalArgumentException e) {
            }
        }
        return shapes.toArray(new Shape[shapes.size()]);
    }
    
    /**
     * 
     */
    public static Color[] findColors(String[] args) {
    	ArrayList<Color> colors = new ArrayList<Color>(2);
        for(String next : args) {
            try {
            	if(next.charAt(next.length()-1) == 's') {
            		colors.add(Color.valueOf(next.substring(0, next.length()-1).toUpperCase()));
            	} else {
            		colors.add(Color.valueOf(next.toUpperCase()));
            	}
            } catch(IllegalArgumentException e) {
            }
        }
        return colors.toArray(new Color[colors.size()]);
    }
}
