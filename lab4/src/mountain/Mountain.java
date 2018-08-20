package mountain;

import java.util.LinkedList;

import fractal.Fractal;
import fractal.TurtleGraphics;

/**
 * The {@code Mountain} class describes an extension of an fractal in the shap
 * of a mountain. The mountain starts as a triangle described with three points,
 * every fractal made will create four new triangles inside the current
 * triangle(s).
 * <p>
 * Between two points there is a point in the middle where the new point for the
 * new triangle will be created. To create a realistic montain this mid-point
 * will be offset a bit. The offset decreases as the fractal increases.
 * 
 * @author Eneas Hållsten
 *
 */
public class Mountain extends Fractal {
	private Point a;
	private Point b;
	private Point c;
	private double dev; // offset
	private LinkedList<Side> list;

	/**
	 * Counstructs the fractal with order 0, three points, a list of sides and
	 * offset dev.
	 * 
	 * @param a
	 * @param b
	 * @param c
	 * @param dev
	 */
	public Mountain(Point a, Point b, Point c, double dev) {
		super();
		this.a = a;
		this.b = b;
		this.c = c;
		this.dev = dev;
		list = new LinkedList<Side>();
	}

	/**
	 * @return title
	 */
	@Override
	public String getTitle() {
		return "Fractal Mountain";
	}

	/**
	 * Draws the mountain with help from the private recursive method fractalLine.
	 * 
	 * @param turtle
	 *            the TurtleGraphics
	 */
	@Override
	public void draw(TurtleGraphics turtle) {
		turtle.moveTo(a.getX(), a.getY());
		fractalLine(turtle, order, a, b, c, dev);
	}

	/**
	 * Private recursive method that draws the lines between the points.
	 * 
	 * @param turtle
	 * @param order
	 * @param a
	 * @param b
	 * @param c
	 * @param dev
	 */
	private void fractalLine(TurtleGraphics turtle, int order, Point a, Point b, Point c, double dev) {
		if (order == 0) {
			turtle.moveTo(c.getX(), c.getY());
			turtle.forwardTo(a.getX(), a.getY());
			turtle.forwardTo(b.getX(), b.getY());
			turtle.forwardTo(c.getX(), c.getY());

		} else {
			Point d = getMid(a, b, dev); // calcultes the mid-point between every side
			Point e = getMid(b, c, dev);
			Point f = getMid(c, a, dev);

			fractalLine(turtle, order - 1, a, d, f, dev / 2);
			fractalLine(turtle, order - 1, d, b, e, dev / 2);
			fractalLine(turtle, order - 1, c, e, f, dev / 2);
			fractalLine(turtle, order - 1, d, e, f, dev / 2);
		}
	}

	/**
	 * private method that calculates the mid-point between two points with the
	 * offset dev. Adds this point to a list that keeps track which mid-points that
	 * already have been calculated. If already in the list, return the old point
	 * and do not calculate a new mid-point.
	 * 
	 * @param a
	 * @param b
	 * @param dev
	 * @return the new mid-point, old mid-point if side already calculated
	 */
	private Point getMid(Point a, Point b, double dev) {
		if (!list.contains(new Side(a, b))) {
			Point p = new Point(a.getX() + (b.getX() - a.getX()) / 2,
					a.getY() + (b.getY() - a.getY()) / 2 + (int) RandomUtilities.randFunc(dev));
			list.add(new Side(a, b, p));
			return p;
		}
		Side s = new Side(a, b);
		Point p = list.get(list.indexOf(s)).getM();
		list.remove(s);

		return p;
	}
}