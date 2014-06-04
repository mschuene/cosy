import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Write a description of class Brain here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Thinkpad {
	
	private static int peripheral_range = 1;
	private static VisualObject[][] v_objects;
	private static ImageViewer viewer;
	
	private Thinkpad() {
	}
	
	public static void setViewer(ImageViewer p_viewer) {
		viewer = p_viewer;
	}
    
	/**
	 * 
	 * @param input
	 * @param color
	 * @return
	 * @throws InterruptedException
	 */
    public static ArrayList<Point> peripheralView(VisualObject[][] input, Color color) throws InterruptedException {
    	v_objects = input;
    	// long started = System.currentTimeMillis();
    	Point[] focus_points = getFocusPoints(input);
    	return new ArrayList<Point>(haveALook(input, focus_points, color));
    }
    
    /**
     * 
     * @param input
     * @param shape
     * @return
     */
    public static int focusView(ArrayList<Point> input, Shape shape) throws InterruptedException {
    	int counter = 0;
    	for(int i = 0; i < input.size();) {
    		Thread.sleep(400);
    		Point point = input.get(i);
    		VisualObject current = v_objects[point.x][point.y];
    		if(current != null && current.getShape().equals(shape)) {
	    		ArrayList<Point> square = square(input, point, shape);
	    		if(square != null) {
	    			counter+= square.size();
	    			i = i - square.size() > 0 ? i - square.size() : 0;
	    			input.removeAll(square);
	    		} else {
		    		ArrayList<Point> cross = cross(input, point, shape);
		    		if(cross != null && square == null) {
		    	    	counter+= cross.size();
		    	    	i = i - cross.size() > 0 ? i - cross.size() : 0;
		    	    	input.removeAll(cross);
		    	    } else {
		    	    	ArrayList<Point> line = line(input, point, shape);
		        		if(line != null && cross == null && square == null) {
		            	    counter+= line.size();
		            	    i = i - line.size() > 0 ? i - line.size() : 0;
		            	    input.removeAll(line);
		             	} else {
		             		counter++;
		             		viewer.getContentPane().getGraphics().drawRect(point.x*60, point.y*60, 60, 60);
		             		i = i -1 > 0 ? i -1 : 0;
		             		input.remove(point);
		             	}
		    	    }
	    		}
	    	} else {
	    		input.remove(point);
	    	}
    	}
    	return counter;
    }
    
    private static ArrayList<Point> square(List<Point> input, Point point, Shape shape) {
    	ArrayList<Point> output = new ArrayList<Point>(4);
    	for(Point point1 : input) {
    		VisualObject current = v_objects[point1.x][point1.y];
	    		if(current != null && current.getShape().equals(shape)) {
	    		if(point1.x < point.x +2 && point1.x > point.x -2 &&
	    				point1.y < point.y +2 && point1.y > point.y -2 && output.size() < 4) {
	    			output.add(point1);
	    		}
    		}
    	}
    	ArrayList<Point> output1 = new ArrayList<Point>(9);
    	if(output.size() == 4) {
    		for(Point point1 : input) {
    			VisualObject current = v_objects[point1.x][point1.y];
        		if(current != null && current.getShape().equals(shape)) {
	        		if(point1.x < point.x +3 && point1.x > point.x -3 &&
	        				point1.y < point.y +3 && point1.y > point.y -3) {
	        			output1.add(point1);
	        		}
        		}
        	}
    	} else {
    		return null;
    	}
    	ArrayList<Point> output2 = new ArrayList<Point>(25);
    	if (output1.size() == 9) {
    		for(Point point1 : input) {
    			VisualObject current = v_objects[point1.x][point1.y];
        		if(current != null && current.getShape().equals(shape)) {
	        		if(point1.x < point.x +4 && point1.x > point.x -4 &&
	        				point1.y < point.y +4 && point1.y > point.y -4) {
	        			output2.add(point1);
	        		}
        		}
        	}
    	} else {
    		return output;
    	} if(output2.size() == 25) {
    		return output2;
    	} else {
    		return output1;
    	}
    }
    /**
     * 
     * @param input
     * @param point
     * @return
     */
    private static ArrayList<Point> cross(List<Point> input, Point point, Shape shape) {
    	ArrayList<Point> output = new ArrayList<Point>(5);
    	output.add(point);
    	for(Point point1 : input) {
    		VisualObject current = v_objects[point.x][point.y];
    		if(current != null && current.getShape().equals(shape)) {
	    		if(point1.x == point.x +1 && point1.y == point.y ||
	    				point1.x == point.x -1 && point1.y == point.y ||
	    				point1.x == point.x && point1.y + 1 == point.y ||
	    				point1.x == point.x && point1.y - 1== point.y) {
	    			output.add(point1);
	    		}
    		}
    	} if(output.size() == 5) {
    		return output;
    	} else {
    		return null;
    	}
    }
    /**
     * 
     * @param input
     * @param point
     * @return
     */
    private static ArrayList<Point> line(List<Point> input, Point point, Shape shape) {
    	ArrayList<Point> output1 = new ArrayList<Point>(5);
    	ArrayList<Point> output2 = new ArrayList<Point>(5);
    	for(Point point1 : input) {
    		VisualObject current = v_objects[point.x][point.y];
    		if(current != null && current.getShape().equals(shape)) {
	    		if(point1.x < point.x +4 && point1.x > point.x -4 && point1.y == point.y ) {
	    			output1.add(point1);
	    		}
    		}
    	}
    	for(Point point1 : input) {
    		VisualObject current = v_objects[point.x][point.y];
    		if(current != null && current.getShape().equals(shape)) {
	    		if(point1.y < point.y +4 && point1.y > point.y -4 && point1.x == point.x ) {
	    			output2.add(point1);
	    		}
    		}
    	}
    	if(output1.size() > 1 && output1.size() > output2.size()) {
    		Point point2 = output1.get(0);
    		int x = 60, y = 60;
    		for(int i = 1; i < output1.size(); i++) {
    			x+= 60;
    		}
    		viewer.getContentPane().getGraphics().drawRect(point2.x*60, point2.y*60, x, y);
    		return output1;
    	} else if(output2.size() > 1 && output2.size() >= output2.size()) {
    		Point point2 = output1.get(0);
    		int x = 60, y = 60;
    		for(int i = 1; i < output2.size(); i++) {
    			y+= 60;
    		}
    		viewer.getContentPane().getGraphics().drawRect(point2.x*60, point2.y*60, x, y);
    		return output2;
    	} else {
    		return null;
    	}
	}
    
    /**
     * 
     * @param input
     * @param points
     * @param color
     * @return
     */
    private static HashSet<Point> haveALook(VisualObject[][] input, Point[] points, Color color) throws InterruptedException {
    	HashSet<Point> output = new HashSet<Point>();
    	for(Point point : points) {
    		Thread.sleep(100);
    		int x_min = point.x - peripheral_range, x_max = point.x + peripheral_range;
    		int y_min = point.y - peripheral_range, y_max = point.y + peripheral_range;
    		viewer.getContentPane().getGraphics().drawRect(x_min*60, y_min*60, (x_max-x_min)*90, (y_max-y_min)*90);
    		for(int x = x_min; x <= x_max && x >= 0 && x < input.length; x++) {
    			for(int y = y_min; y <= y_max && y >= 0 && y < input[0].length; y++) {
    				
    				if(input[x][y] != null && input[x][y].getColor().equals(color)) {
    	    			output.add(new Point(x,y));
    	    		}
    			}
    		}
    	}
    	return output;
    }
    
    /**
     * 
     * @param input
     * @return
     */
    private static Point[] getFocusPoints(VisualObject[][] input)  {
    	int input_width = input.length, input_height = input[0].length;
    	int input_box = input.length * input[0].length;
    	Point middle = new Point(input_width / 2, input_height / 2);
    	
    	// Checks if one focus_point is enough. 1, 9, 25, 49, 81 ... (1 + 2*n)^2 
    	if(input_box <= (Math.pow((1 + 2 * peripheral_range), 2))) {
    		return new Point[] { middle };
    	}
    	else {
    		
    		// Calculates how many focus_points are needed. 1, 9, 25, 49, 81 ...
        	int range_multiplier = 0;
        	while(input_box  > (Math.pow((1 + 2 * peripheral_range), 2))
        			* (Math.pow((1 + 2 * range_multiplier), 2))) {
        		range_multiplier++;
        	}
        	int focus_number = (int)(Math.pow((1 + 2 * range_multiplier), 2));
        	
        	// Sets up a new Point[], adds middle Point and sets necessary variables
        	Point[] focus_points = new Point[focus_number];
        	focus_points[0] = middle;
        	Point new_point, old_point = middle;
        	int sidestep = 1, side = 1 + 2 * sidestep;
        	
        	// Locates the focus_points that the entire Picture is covert
        	for(int j = 1, k = 1; k < focus_number; j++) {
        		switch(j / 2 % 5) {
        		
        			case 0:
	       				new_point = new Point(old_point.x + peripheral_range * 2 + 1, old_point.y - peripheral_range * 2 - 1);
	       				trimToBounds(new_point, input_width -1, input_height -1);
    					focus_points[k++] = new_point;
	   	        		old_point = new_point;
	   	        		if(k > 2 && k < focus_number) {
		   	        		new_point = new Point(old_point.x + peripheral_range * 2 + 1, old_point.y);
	        				trimToBounds(new_point, input_width -1, input_height -1);
	        				focus_points[k++] = new_point;
	    	        		old_point = new_point;
	    	        		side = 1 + 2 * ++sidestep;
	    	        		j++;
	   	        		}
        				break;
        				
        			case 1:
        				for(int i = 0; i < side -1; i++) {
        					new_point = new Point(old_point.x, old_point.y + peripheral_range * 2 + 1);
        					trimToBounds(new_point, input_width -1, input_height -1);
        					focus_points[k++] = new_point;
        	        		old_point = new_point;
        				}
        				j++;
        				break;
        				
        			case 2:
        				for(int i = 0; i < side -1; i++) {
	        				new_point = new Point(old_point.x - peripheral_range * 2 - 1, old_point.y);
	        				trimToBounds(new_point, input_width -1, input_height -1);
	        				focus_points[k++] = new_point;
	    	        		old_point = new_point;
        				}
        				j++;
        				break;
        			
        			case 3:
        				for(int i = 0; i < side -1; i++) {
	        				new_point = new Point(old_point.x, old_point.y - peripheral_range * 2 - 1);
	        				trimToBounds(new_point, input_width -1, input_height -1);
	        				focus_points[k++] = new_point;
	    	        		old_point = new_point;
        				}
        				j++;
        				break;
        				
        			case 4:
        				for(int i = 0; i < side - 2 && k < focus_number; i++) {
	        				new_point = new Point(old_point.x + peripheral_range * 2 + 1, old_point.y);
	        				trimToBounds(new_point, input_width -1, input_height -1);
	        				focus_points[k++] = new_point;
	    	        		old_point = new_point;
        				}
        				j++;
        				break;
        		}
        	}
        	return focus_points;
    	}
    }
    
    private static void trimToBounds(Point point, int width, int height) {
    	if(point.x > width) {
			point.setLocation(width, point.y);
		} else if(point.x < 0) {
			point.setLocation(0, height);
		} if(point.y > height) {
			point.setLocation(point.x, height);
		} else if (point.y < 0) {
			point.setLocation(point.x, 0);
		}
    }
    
    /**
     * Counts
     * @deprecated 1.0
     */
    public static int countObjects(VisualObject[][] input, Shape shape, Color color) throws InterruptedException {
        long started = System.currentTimeMillis();
        int i = 0;
        for (VisualObject[] array : input) {
        	for (VisualObject object : array) {
        		if(object != null) {
        			//Thread.sleep(100);
        			if(TimeManager.isTimeLeft(started) && object.getColor().equals(color)
        					&& object.getShape().equals(shape)) {
        				i++;
        			}
        		}
            }
        }
        return i;
    }
}
