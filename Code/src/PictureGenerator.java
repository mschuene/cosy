import java.util.Random;
public class PictureGenerator {
	
	
	public VisualObject[][] generatePicture() {
		return generatePicture(new IntGenerator(3,4),new IntGenerator(3,4),
				               new IntGenerator(5,10),new ShapeGenerator(),
				               new ColorGenerator());
	}
	

	public VisualObject[][] generatePicture(IntGenerator xgen, IntGenerator ygen,
			                                IntGenerator gen_num_obj, ShapeGenerator shape_gen, ColorGenerator color_gen) {
		int x = xgen.generate();
		int y = ygen.generate();
		int num_obj = Math.min(x*y,gen_num_obj.generate());
		VisualObject[][] ret = new VisualObject[x][y];
		IntGenerator xcor_gen = new IntGenerator(0,x);
		IntGenerator ycor_gen = new IntGenerator(0,y);
		int xcor = 0;
		int ycor = 0;
		for(int i = 0; i < num_obj; i++) {
			do{
		       xcor = xcor_gen.generate();
		       ycor = ycor_gen.generate();
			} while (ret[xcor][ycor] != null);
			ret[xcor][ycor] = new VisualObject(shape_gen.generate(), color_gen.generate());
		}
		return ret;
	}
	
	public static void printPicture(VisualObject[][] toPrint){
	  for(VisualObject[] row: toPrint) {
		  for(VisualObject cell: row) {
			System.out.print(cell);
			System.out.print(" ,");
		  }
		  System.out.println("");
	  }
	}
	
	
	public static void main(String[] args) {
		PictureGenerator pg = new PictureGenerator();
	   int generated = (new IntGenerator(5,6)).generate();
	   Shape s = (new ShapeGenerator()).generate();
	   Color c = (new ColorGenerator()).generate();
	   System.out.println(generated);
	   System.out.println(s);
	   System.out.println(c);
	   PictureGenerator.printPicture(pg.generatePicture());
	}
}


