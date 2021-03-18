package edu.luc.etl.cs313.android.shapes.model;

/**
 * A point, implemented as a location without a shape.
 */
public class Point extends Location {

	// DONE your job
	// HINT: use a circle with radius 0 as the shape!
	private static final Shape point = new Circle(0);

	public Point(final int x, final int y) {
		super(-1, -1, null);
		assert x >= 0;
		assert y >= 0;
	}

}
