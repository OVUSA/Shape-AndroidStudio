package edu.luc.etl.cs313.android.shapes.android;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.hardware.camera2.params.BlackLevelPattern;
import android.icu.text.RelativeDateTimeFormatter;

import java.util.List;

import edu.luc.etl.cs313.android.shapes.model.*;

/**
 * A Visitor for drawing a shape to an Android canvas.
 */
public class Draw implements Visitor<Void> {
 


	private final Canvas canvas;
	private final Paint paint;

	public Draw(final Canvas canvas, final Paint paint) {
		this.canvas = canvas;
		this.paint = paint;
		paint.setStyle(Style.STROKE);
	}

	@Override
	public Void onCircle(final Circle c) {
		canvas.drawCircle(0, 0, c.getRadius(), paint);
		return null;
	}

	@Override
	public Void onStrokeColor(final StrokeColor c) {
		int color = paint.getColor();
		paint.setColor(c.getColor());
		c.getShape().accept(this);
		paint.setColor(color);
		return null;
	}

	@Override
	public Void onFill(final Fill f) {
		Style style = paint.getStyle();
		paint.setStyle(Style.FILL_AND_STROKE);
		f.getShape().accept(this);
		paint.setStyle(style);
		return null;
	}

	@Override
	public Void onGroup(final Group g) {
		for (Shape a: g.getShapes()){
			a.accept(this);
		}
		return null;
	}

	@Override
	public Void onLocation(final Location l) {
		canvas.translate(l.getX(),l.getY());
		l.getShape().accept(this);
		canvas.translate(-l.getX(),-l.getY());
		return null;
	}

	@Override
	public Void onRectangle(final Rectangle r) {
		canvas.drawRect(0,0, r.getWidth(),r.getHeight(), paint);
		return null;
	}

	public Void onOutline(Outline o) {
		int color = paint.getColor();
		Style style = paint.getStyle();
		paint.setColor(color);
		paint.setStyle(Style.STROKE);
		o.getShape().accept(this);
		paint.setColor(color);
		paint.setStyle(style);
		return null;
	}

	@Override
	public Void onPolygon(final Polygon s) {

		final float[] pts = new float[(4*s.getPoints().size())];
		int i = 0;
		for (Point p: s.getPoints()) {
			pts[i] = p.getX();
			pts[i+1] = p.getY();
			i=i+4;
		}
		for (int x=4; x<(4*(s.getPoints().size())); x+=4)
		{
			pts[x-2] = pts[x];
			pts[x-1] = pts[x+1];
		}
		pts[4*(s.getPoints().size())-2]=pts[0];
		pts[4*(s.getPoints().size())-1]=pts[1];

		canvas.drawLines(pts, paint);
		return null;
	}
}
