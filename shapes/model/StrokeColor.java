package edu.luc.etl.cs313.android.shapes.model;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * A decorator for specifying the stroke (foreground) color for drawing the
 * shape.
 */
	// DONE
public class StrokeColor implements Shape {
	public final Shape shape;
	public final int color;


	public StrokeColor(final int color, final Shape shape) {
		this.shape = shape;
		this.color = color;
	}

	public int getColor(){
		return color;
	}

	public Shape getShape() {
		return shape;
	}

	@Override
	public <Result> Result accept(Visitor<Result> v) {
		return v.onStrokeColor(this);
	}
}
