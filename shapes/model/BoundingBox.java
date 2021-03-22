package edu.luc.etl.cs313.android.shapes.model;
import static java.lang.Math.abs;
/**
 * A shape visitor for calculating the bounding box, that is, the smallest
 * rectangle containing the shape. The resulting bounding box is returned as a
 * rectangle at a specific location.
 */
public class BoundingBox implements Visitor<Location> {
// TODO entirely your job (except onCircle)
	@Override
	public Location onCircle(final Circle c) {
		final int radius = c.getRadius();
		return new Location(-radius, -radius, new Rectangle(2 * radius, 2 * radius));
	}
	@Override
	public Location onFill(final Fill f) {
		return f.getShape().accept(this);
	}
	@Override
	public Location onGroup(final Group g) {
		int i = 0;
		Count h = new Count();
		int[] xVal = new int[2*(h.onGroup(g).intValue())];
		int[] yVal = new int[2*(h.onGroup(g).intValue())];
		for (Shape s : g.getShapes()) {
			xVal[i] = s.accept(this).getX() ;
			xVal[i +1] = s.accept(this).getX() + ((Rectangle) s.accept(this).getShape()).getWidth();
			yVal[i] = s.accept(this).getY();
			yVal[i +1] = s.accept(this).getY() + ((Rectangle) s.accept(this).getShape()).getHeight();
			i = i +2;
		}

		int xMin = xVal[0], xMax = xVal[0];
		int yMin = yVal[0], yMax = yVal[0];
		for (int k = 1; k < i; k++) {
			if (xVal[k] < xMin) {
				xMin = xVal[k];
			}
			if (xVal[k] > xMax) {
				xMax = xVal[k];
			}
			if (yVal[k] < yMin) {
				yMin = yVal[k];
			}
			if (yVal[k] > yMax) {
				yMax = yVal[k];
			}

		}
		return new Location(xMin, yMin, new Rectangle(xMax-xMin, yMax-yMin));
	}

	@Override
	public Location onLocation(final Location l) {
		Location c = l.getShape().accept(this);
		return new Location(l.getX() + c.getX() , l.getY() + c.getY(), c.getShape());
	}
	@Override
	public Location onRectangle(final Rectangle r) {
		return new Location(0,0, new Rectangle(r.getWidth(),r.getHeight()));
	}
	@Override
	public Location onStrokeColor(final StrokeColor c) {
		return c.getShape().accept(this);
	}

	@Override
	public Location onOutline(final Outline o){
		return o.getShape().accept(this);
	}

	@Override
	public Location onPolygon(final Polygon s) {
		return onGroup(s);
	}
}
