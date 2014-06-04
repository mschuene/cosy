import java.util.Random;

public class IntGenerator {
		int to;
		int from;
		Random generator;
		public IntGenerator(int from,int to) {
			this.from = from;
			this.to = to;
			this.generator = new Random();
		}
		
		public int generate() {
			return from + generator.nextInt(to - from);
		}
}
	