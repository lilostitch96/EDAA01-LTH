package tree;

import fractal.Fractal;
import fractal.TurtleGraphics;
import mountain.RandomUtilities;

public class TreeFractal extends Fractal {
	private int rootLength;
	private int dev;

	public TreeFractal(int rootLength, int dev) {
		this.rootLength = rootLength;
		this.dev = dev;
	}

	@Override
	public String getTitle() {
		return "Tree";
	}

	@Override
	public void draw(TurtleGraphics turtle) {
		turtle.moveTo(turtle.getWidth() / 2, turtle.getHeight() - turtle.getHeight() / 10);
		turtle.forward(rootLength);
		fractalLine(turtle, order, rootLength, 90, dev);
	}

	private void fractalLine(TurtleGraphics turtle, int order, double length, int alpha, int dev) {
		double offset = RandomUtilities.randFunc(dev);
		if (order == 0) {
			turtle.penDown();
			turtle.setDirection(alpha);
			turtle.forward(length);
		} else {
			fractalLine(turtle, order - 1, length * 0.67, alpha + 45 + (int) offset, dev / 2);
			fractalLine(turtle, order - 1, length * 0.67, alpha - 45 - (int) offset, dev / 2);
		}
	}

}
